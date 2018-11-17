package io.vitronmfg.application.web.rest;

import io.vitronmfg.application.VitronmfgApp;

import io.vitronmfg.application.domain.Career;
import io.vitronmfg.application.repository.CareerRepository;
import io.vitronmfg.application.service.CareerService;
import io.vitronmfg.application.service.dto.CareerDTO;
import io.vitronmfg.application.service.mapper.CareerMapper;
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
 * Test class for the CareerResource REST controller.
 *
 * @see CareerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VitronmfgApp.class)
public class CareerResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final byte[] DEFAULT_NEW_ICON_PATH = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_NEW_ICON_PATH = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_NEW_ICON_PATH_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_NEW_ICON_PATH_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_DETAILS = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_CONTACT = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_CONTACT = "BBBBBBBBBB";

    private static final String DEFAULT_MOBILE = "AAAAAAAAAA";
    private static final String UPDATED_MOBILE = "BBBBBBBBBB";

    @Autowired
    private CareerRepository careerRepository;


    @Autowired
    private CareerMapper careerMapper;
    

    @Autowired
    private CareerService careerService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCareerMockMvc;

    private Career career;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CareerResource careerResource = new CareerResource(careerService);
        this.restCareerMockMvc = MockMvcBuilders.standaloneSetup(careerResource)
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
    public static Career createEntity(EntityManager em) {
        Career career = new Career()
            .title(DEFAULT_TITLE)
            .newIconPath(DEFAULT_NEW_ICON_PATH)
            .newIconPathContentType(DEFAULT_NEW_ICON_PATH_CONTENT_TYPE)
            .description(DEFAULT_DESCRIPTION)
            .details(DEFAULT_DETAILS)
            .emailContact(DEFAULT_EMAIL_CONTACT)
            .mobile(DEFAULT_MOBILE);
        return career;
    }

    @Before
    public void initTest() {
        career = createEntity(em);
    }

    @Test
    @Transactional
    public void createCareer() throws Exception {
        int databaseSizeBeforeCreate = careerRepository.findAll().size();

        // Create the Career
        CareerDTO careerDTO = careerMapper.toDto(career);
        restCareerMockMvc.perform(post("/api/careers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(careerDTO)))
            .andExpect(status().isCreated());

        // Validate the Career in the database
        List<Career> careerList = careerRepository.findAll();
        assertThat(careerList).hasSize(databaseSizeBeforeCreate + 1);
        Career testCareer = careerList.get(careerList.size() - 1);
        assertThat(testCareer.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testCareer.getNewIconPath()).isEqualTo(DEFAULT_NEW_ICON_PATH);
        assertThat(testCareer.getNewIconPathContentType()).isEqualTo(DEFAULT_NEW_ICON_PATH_CONTENT_TYPE);
        assertThat(testCareer.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCareer.getDetails()).isEqualTo(DEFAULT_DETAILS);
        assertThat(testCareer.getEmailContact()).isEqualTo(DEFAULT_EMAIL_CONTACT);
        assertThat(testCareer.getMobile()).isEqualTo(DEFAULT_MOBILE);
    }

    @Test
    @Transactional
    public void createCareerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = careerRepository.findAll().size();

        // Create the Career with an existing ID
        career.setId(1L);
        CareerDTO careerDTO = careerMapper.toDto(career);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCareerMockMvc.perform(post("/api/careers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(careerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Career in the database
        List<Career> careerList = careerRepository.findAll();
        assertThat(careerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = careerRepository.findAll().size();
        // set the field null
        career.setTitle(null);

        // Create the Career, which fails.
        CareerDTO careerDTO = careerMapper.toDto(career);

        restCareerMockMvc.perform(post("/api/careers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(careerDTO)))
            .andExpect(status().isBadRequest());

        List<Career> careerList = careerRepository.findAll();
        assertThat(careerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCareers() throws Exception {
        // Initialize the database
        careerRepository.saveAndFlush(career);

        // Get all the careerList
        restCareerMockMvc.perform(get("/api/careers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(career.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].newIconPathContentType").value(hasItem(DEFAULT_NEW_ICON_PATH_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].newIconPath").value(hasItem(Base64Utils.encodeToString(DEFAULT_NEW_ICON_PATH))))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].details").value(hasItem(DEFAULT_DETAILS.toString())))
            .andExpect(jsonPath("$.[*].emailContact").value(hasItem(DEFAULT_EMAIL_CONTACT.toString())))
            .andExpect(jsonPath("$.[*].mobile").value(hasItem(DEFAULT_MOBILE.toString())));
    }
    

    @Test
    @Transactional
    public void getCareer() throws Exception {
        // Initialize the database
        careerRepository.saveAndFlush(career);

        // Get the career
        restCareerMockMvc.perform(get("/api/careers/{id}", career.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(career.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.newIconPathContentType").value(DEFAULT_NEW_ICON_PATH_CONTENT_TYPE))
            .andExpect(jsonPath("$.newIconPath").value(Base64Utils.encodeToString(DEFAULT_NEW_ICON_PATH)))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.details").value(DEFAULT_DETAILS.toString()))
            .andExpect(jsonPath("$.emailContact").value(DEFAULT_EMAIL_CONTACT.toString()))
            .andExpect(jsonPath("$.mobile").value(DEFAULT_MOBILE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingCareer() throws Exception {
        // Get the career
        restCareerMockMvc.perform(get("/api/careers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCareer() throws Exception {
        // Initialize the database
        careerRepository.saveAndFlush(career);

        int databaseSizeBeforeUpdate = careerRepository.findAll().size();

        // Update the career
        Career updatedCareer = careerRepository.findById(career.getId()).get();
        // Disconnect from session so that the updates on updatedCareer are not directly saved in db
        em.detach(updatedCareer);
        updatedCareer
            .title(UPDATED_TITLE)
            .newIconPath(UPDATED_NEW_ICON_PATH)
            .newIconPathContentType(UPDATED_NEW_ICON_PATH_CONTENT_TYPE)
            .description(UPDATED_DESCRIPTION)
            .details(UPDATED_DETAILS)
            .emailContact(UPDATED_EMAIL_CONTACT)
            .mobile(UPDATED_MOBILE);
        CareerDTO careerDTO = careerMapper.toDto(updatedCareer);

        restCareerMockMvc.perform(put("/api/careers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(careerDTO)))
            .andExpect(status().isOk());

        // Validate the Career in the database
        List<Career> careerList = careerRepository.findAll();
        assertThat(careerList).hasSize(databaseSizeBeforeUpdate);
        Career testCareer = careerList.get(careerList.size() - 1);
        assertThat(testCareer.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testCareer.getNewIconPath()).isEqualTo(UPDATED_NEW_ICON_PATH);
        assertThat(testCareer.getNewIconPathContentType()).isEqualTo(UPDATED_NEW_ICON_PATH_CONTENT_TYPE);
        assertThat(testCareer.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCareer.getDetails()).isEqualTo(UPDATED_DETAILS);
        assertThat(testCareer.getEmailContact()).isEqualTo(UPDATED_EMAIL_CONTACT);
        assertThat(testCareer.getMobile()).isEqualTo(UPDATED_MOBILE);
    }

    @Test
    @Transactional
    public void updateNonExistingCareer() throws Exception {
        int databaseSizeBeforeUpdate = careerRepository.findAll().size();

        // Create the Career
        CareerDTO careerDTO = careerMapper.toDto(career);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException 
        restCareerMockMvc.perform(put("/api/careers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(careerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Career in the database
        List<Career> careerList = careerRepository.findAll();
        assertThat(careerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCareer() throws Exception {
        // Initialize the database
        careerRepository.saveAndFlush(career);

        int databaseSizeBeforeDelete = careerRepository.findAll().size();

        // Get the career
        restCareerMockMvc.perform(delete("/api/careers/{id}", career.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Career> careerList = careerRepository.findAll();
        assertThat(careerList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Career.class);
        Career career1 = new Career();
        career1.setId(1L);
        Career career2 = new Career();
        career2.setId(career1.getId());
        assertThat(career1).isEqualTo(career2);
        career2.setId(2L);
        assertThat(career1).isNotEqualTo(career2);
        career1.setId(null);
        assertThat(career1).isNotEqualTo(career2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CareerDTO.class);
        CareerDTO careerDTO1 = new CareerDTO();
        careerDTO1.setId(1L);
        CareerDTO careerDTO2 = new CareerDTO();
        assertThat(careerDTO1).isNotEqualTo(careerDTO2);
        careerDTO2.setId(careerDTO1.getId());
        assertThat(careerDTO1).isEqualTo(careerDTO2);
        careerDTO2.setId(2L);
        assertThat(careerDTO1).isNotEqualTo(careerDTO2);
        careerDTO1.setId(null);
        assertThat(careerDTO1).isNotEqualTo(careerDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(careerMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(careerMapper.fromId(null)).isNull();
    }
}
