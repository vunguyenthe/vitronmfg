package io.vitronmfg.application.web.rest;

import io.vitronmfg.application.VitronmfgApp;

import io.vitronmfg.application.domain.Logo;
import io.vitronmfg.application.repository.LogoRepository;
import io.vitronmfg.application.service.LogoService;
import io.vitronmfg.application.service.dto.LogoDTO;
import io.vitronmfg.application.service.mapper.LogoMapper;
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
 * Test class for the LogoResource REST controller.
 *
 * @see LogoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VitronmfgApp.class)
public class LogoResourceIntTest {

    private static final byte[] DEFAULT_LOGO_ID = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_LOGO_ID = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_LOGO_ID_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_LOGO_ID_CONTENT_TYPE = "image/png";

    @Autowired
    private LogoRepository logoRepository;


    @Autowired
    private LogoMapper logoMapper;
    

    @Autowired
    private LogoService logoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restLogoMockMvc;

    private Logo logo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LogoResource logoResource = new LogoResource(logoService);
        this.restLogoMockMvc = MockMvcBuilders.standaloneSetup(logoResource)
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
    public static Logo createEntity(EntityManager em) {
        Logo logo = new Logo()
            .logoId(DEFAULT_LOGO_ID)
            .logoIdContentType(DEFAULT_LOGO_ID_CONTENT_TYPE);
        return logo;
    }

    @Before
    public void initTest() {
        logo = createEntity(em);
    }

    @Test
    @Transactional
    public void createLogo() throws Exception {
        int databaseSizeBeforeCreate = logoRepository.findAll().size();

        // Create the Logo
        LogoDTO logoDTO = logoMapper.toDto(logo);
        restLogoMockMvc.perform(post("/api/logos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(logoDTO)))
            .andExpect(status().isCreated());

        // Validate the Logo in the database
        List<Logo> logoList = logoRepository.findAll();
        assertThat(logoList).hasSize(databaseSizeBeforeCreate + 1);
        Logo testLogo = logoList.get(logoList.size() - 1);
        assertThat(testLogo.getLogoId()).isEqualTo(DEFAULT_LOGO_ID);
        assertThat(testLogo.getLogoIdContentType()).isEqualTo(DEFAULT_LOGO_ID_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createLogoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = logoRepository.findAll().size();

        // Create the Logo with an existing ID
        logo.setId(1L);
        LogoDTO logoDTO = logoMapper.toDto(logo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLogoMockMvc.perform(post("/api/logos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(logoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Logo in the database
        List<Logo> logoList = logoRepository.findAll();
        assertThat(logoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllLogos() throws Exception {
        // Initialize the database
        logoRepository.saveAndFlush(logo);

        // Get all the logoList
        restLogoMockMvc.perform(get("/api/logos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(logo.getId().intValue())))
            .andExpect(jsonPath("$.[*].logoIdContentType").value(hasItem(DEFAULT_LOGO_ID_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].logoId").value(hasItem(Base64Utils.encodeToString(DEFAULT_LOGO_ID))));
    }
    

    @Test
    @Transactional
    public void getLogo() throws Exception {
        // Initialize the database
        logoRepository.saveAndFlush(logo);

        // Get the logo
        restLogoMockMvc.perform(get("/api/logos/{id}", logo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(logo.getId().intValue()))
            .andExpect(jsonPath("$.logoIdContentType").value(DEFAULT_LOGO_ID_CONTENT_TYPE))
            .andExpect(jsonPath("$.logoId").value(Base64Utils.encodeToString(DEFAULT_LOGO_ID)));
    }
    @Test
    @Transactional
    public void getNonExistingLogo() throws Exception {
        // Get the logo
        restLogoMockMvc.perform(get("/api/logos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLogo() throws Exception {
        // Initialize the database
        logoRepository.saveAndFlush(logo);

        int databaseSizeBeforeUpdate = logoRepository.findAll().size();

        // Update the logo
        Logo updatedLogo = logoRepository.findById(logo.getId()).get();
        // Disconnect from session so that the updates on updatedLogo are not directly saved in db
        em.detach(updatedLogo);
        updatedLogo
            .logoId(UPDATED_LOGO_ID)
            .logoIdContentType(UPDATED_LOGO_ID_CONTENT_TYPE);
        LogoDTO logoDTO = logoMapper.toDto(updatedLogo);

        restLogoMockMvc.perform(put("/api/logos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(logoDTO)))
            .andExpect(status().isOk());

        // Validate the Logo in the database
        List<Logo> logoList = logoRepository.findAll();
        assertThat(logoList).hasSize(databaseSizeBeforeUpdate);
        Logo testLogo = logoList.get(logoList.size() - 1);
        assertThat(testLogo.getLogoId()).isEqualTo(UPDATED_LOGO_ID);
        assertThat(testLogo.getLogoIdContentType()).isEqualTo(UPDATED_LOGO_ID_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingLogo() throws Exception {
        int databaseSizeBeforeUpdate = logoRepository.findAll().size();

        // Create the Logo
        LogoDTO logoDTO = logoMapper.toDto(logo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException 
        restLogoMockMvc.perform(put("/api/logos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(logoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Logo in the database
        List<Logo> logoList = logoRepository.findAll();
        assertThat(logoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLogo() throws Exception {
        // Initialize the database
        logoRepository.saveAndFlush(logo);

        int databaseSizeBeforeDelete = logoRepository.findAll().size();

        // Get the logo
        restLogoMockMvc.perform(delete("/api/logos/{id}", logo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Logo> logoList = logoRepository.findAll();
        assertThat(logoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Logo.class);
        Logo logo1 = new Logo();
        logo1.setId(1L);
        Logo logo2 = new Logo();
        logo2.setId(logo1.getId());
        assertThat(logo1).isEqualTo(logo2);
        logo2.setId(2L);
        assertThat(logo1).isNotEqualTo(logo2);
        logo1.setId(null);
        assertThat(logo1).isNotEqualTo(logo2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LogoDTO.class);
        LogoDTO logoDTO1 = new LogoDTO();
        logoDTO1.setId(1L);
        LogoDTO logoDTO2 = new LogoDTO();
        assertThat(logoDTO1).isNotEqualTo(logoDTO2);
        logoDTO2.setId(logoDTO1.getId());
        assertThat(logoDTO1).isEqualTo(logoDTO2);
        logoDTO2.setId(2L);
        assertThat(logoDTO1).isNotEqualTo(logoDTO2);
        logoDTO1.setId(null);
        assertThat(logoDTO1).isNotEqualTo(logoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(logoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(logoMapper.fromId(null)).isNull();
    }
}
