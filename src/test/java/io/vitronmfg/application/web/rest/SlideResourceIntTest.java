package io.vitronmfg.application.web.rest;

import io.vitronmfg.application.VitronmfgApp;

import io.vitronmfg.application.domain.Slide;
import io.vitronmfg.application.repository.SlideRepository;
import io.vitronmfg.application.service.SlideService;
import io.vitronmfg.application.service.dto.SlideDTO;
import io.vitronmfg.application.service.mapper.SlideMapper;
import io.vitronmfg.application.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.util.List;


import static io.vitronmfg.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SlideResource REST controller.
 *
 * @see SlideResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VitronmfgApp.class)
public class SlideResourceIntTest {

    private static final byte[] DEFAULT_SLIDE_ID = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_SLIDE_ID = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_SLIDE_ID_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_SLIDE_ID_CONTENT_TYPE = "image/png";

    @Autowired
    private SlideRepository slideRepository;


    @Autowired
    private SlideMapper slideMapper;
    

    @Autowired
    private SlideService slideService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSlideMockMvc;

    private Slide slide;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SlideResource slideResource = new SlideResource(slideService);
        this.restSlideMockMvc = MockMvcBuilders.standaloneSetup(slideResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Slide createEntity(EntityManager em) {
        Slide slide = new Slide()
            .slideId(DEFAULT_SLIDE_ID)
            .slideIdContentType(DEFAULT_SLIDE_ID_CONTENT_TYPE);
        return slide;
    }

    @Before
    public void initTest() {
        slide = createEntity(em);
    }

    @Test
    @Transactional
    public void createSlide() throws Exception {
        int databaseSizeBeforeCreate = slideRepository.findAll().size();

        // Create the Slide
        SlideDTO slideDTO = slideMapper.toDto(slide);
        restSlideMockMvc.perform(post("/api/slides")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(slideDTO)))
            .andExpect(status().isCreated());

        // Validate the Slide in the database
        List<Slide> slideList = slideRepository.findAll();
        assertThat(slideList).hasSize(databaseSizeBeforeCreate + 1);
        Slide testSlide = slideList.get(slideList.size() - 1);
        assertThat(testSlide.getSlideId()).isEqualTo(DEFAULT_SLIDE_ID);
        assertThat(testSlide.getSlideIdContentType()).isEqualTo(DEFAULT_SLIDE_ID_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createSlideWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = slideRepository.findAll().size();

        // Create the Slide with an existing ID
        slide.setId(1L);
        SlideDTO slideDTO = slideMapper.toDto(slide);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSlideMockMvc.perform(post("/api/slides")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(slideDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Slide in the database
        List<Slide> slideList = slideRepository.findAll();
        assertThat(slideList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSlides() throws Exception {
        // Initialize the database
        slideRepository.saveAndFlush(slide);

        // Get all the slideList
        restSlideMockMvc.perform(get("/api/slides?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(slide.getId().intValue())))
            .andExpect(jsonPath("$.[*].slideIdContentType").value(hasItem(DEFAULT_SLIDE_ID_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].slideId").value(hasItem(Base64Utils.encodeToString(DEFAULT_SLIDE_ID))));
    }
    

    @Test
    @Transactional
    public void getSlide() throws Exception {
        // Initialize the database
        slideRepository.saveAndFlush(slide);

        // Get the slide
        restSlideMockMvc.perform(get("/api/slides/{id}", slide.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(slide.getId().intValue()))
            .andExpect(jsonPath("$.slideIdContentType").value(DEFAULT_SLIDE_ID_CONTENT_TYPE))
            .andExpect(jsonPath("$.slideId").value(Base64Utils.encodeToString(DEFAULT_SLIDE_ID)));
    }
    @Test
    @Transactional
    public void getNonExistingSlide() throws Exception {
        // Get the slide
        restSlideMockMvc.perform(get("/api/slides/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSlide() throws Exception {
        // Initialize the database
        slideRepository.saveAndFlush(slide);

        int databaseSizeBeforeUpdate = slideRepository.findAll().size();

        // Update the slide
        Slide updatedSlide = slideRepository.findById(slide.getId()).get();
        // Disconnect from session so that the updates on updatedSlide are not directly saved in db
        em.detach(updatedSlide);
        updatedSlide
            .slideId(UPDATED_SLIDE_ID)
            .slideIdContentType(UPDATED_SLIDE_ID_CONTENT_TYPE);
        SlideDTO slideDTO = slideMapper.toDto(updatedSlide);

        restSlideMockMvc.perform(put("/api/slides")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(slideDTO)))
            .andExpect(status().isOk());

        // Validate the Slide in the database
        List<Slide> slideList = slideRepository.findAll();
        assertThat(slideList).hasSize(databaseSizeBeforeUpdate);
        Slide testSlide = slideList.get(slideList.size() - 1);
        assertThat(testSlide.getSlideId()).isEqualTo(UPDATED_SLIDE_ID);
        assertThat(testSlide.getSlideIdContentType()).isEqualTo(UPDATED_SLIDE_ID_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingSlide() throws Exception {
        int databaseSizeBeforeUpdate = slideRepository.findAll().size();

        // Create the Slide
        SlideDTO slideDTO = slideMapper.toDto(slide);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException 
        restSlideMockMvc.perform(put("/api/slides")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(slideDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Slide in the database
        List<Slide> slideList = slideRepository.findAll();
        assertThat(slideList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSlide() throws Exception {
        // Initialize the database
        slideRepository.saveAndFlush(slide);

        int databaseSizeBeforeDelete = slideRepository.findAll().size();

        // Get the slide
        restSlideMockMvc.perform(delete("/api/slides/{id}", slide.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Slide> slideList = slideRepository.findAll();
        assertThat(slideList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Slide.class);
        Slide slide1 = new Slide();
        slide1.setId(1L);
        Slide slide2 = new Slide();
        slide2.setId(slide1.getId());
        assertThat(slide1).isEqualTo(slide2);
        slide2.setId(2L);
        assertThat(slide1).isNotEqualTo(slide2);
        slide1.setId(null);
        assertThat(slide1).isNotEqualTo(slide2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SlideDTO.class);
        SlideDTO slideDTO1 = new SlideDTO();
        slideDTO1.setId(1L);
        SlideDTO slideDTO2 = new SlideDTO();
        assertThat(slideDTO1).isNotEqualTo(slideDTO2);
        slideDTO2.setId(slideDTO1.getId());
        assertThat(slideDTO1).isEqualTo(slideDTO2);
        slideDTO2.setId(2L);
        assertThat(slideDTO1).isNotEqualTo(slideDTO2);
        slideDTO1.setId(null);
        assertThat(slideDTO1).isNotEqualTo(slideDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(slideMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(slideMapper.fromId(null)).isNull();
    }
}
