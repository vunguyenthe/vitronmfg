package io.vitronmfg.application.web.rest;

import io.vitronmfg.application.VitronmfgApp;

import io.vitronmfg.application.domain.MainProduct;
import io.vitronmfg.application.repository.MainProductRepository;
import io.vitronmfg.application.service.MainProductService;
import io.vitronmfg.application.service.dto.MainProductDTO;
import io.vitronmfg.application.service.mapper.MainProductMapper;
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
 * Test class for the MainProductResource REST controller.
 *
 * @see MainProductResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VitronmfgApp.class)
public class MainProductResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final byte[] DEFAULT_PHOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PHOTO = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_PHOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PHOTO_CONTENT_TYPE = "image/png";

    @Autowired
    private MainProductRepository mainProductRepository;


    @Autowired
    private MainProductMapper mainProductMapper;
    

    @Autowired
    private MainProductService mainProductService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMainProductMockMvc;

    private MainProduct mainProduct;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MainProductResource mainProductResource = new MainProductResource(mainProductService);
        this.restMainProductMockMvc = MockMvcBuilders.standaloneSetup(mainProductResource)
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
    public static MainProduct createEntity(EntityManager em) {
        MainProduct mainProduct = new MainProduct()
            .title(DEFAULT_TITLE)
            .description(DEFAULT_DESCRIPTION)
            .photo(DEFAULT_PHOTO)
            .photoContentType(DEFAULT_PHOTO_CONTENT_TYPE);
        return mainProduct;
    }

    @Before
    public void initTest() {
        mainProduct = createEntity(em);
    }

    @Test
    @Transactional
    public void createMainProduct() throws Exception {
        int databaseSizeBeforeCreate = mainProductRepository.findAll().size();

        // Create the MainProduct
        MainProductDTO mainProductDTO = mainProductMapper.toDto(mainProduct);
        restMainProductMockMvc.perform(post("/api/main-products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mainProductDTO)))
            .andExpect(status().isCreated());

        // Validate the MainProduct in the database
        List<MainProduct> mainProductList = mainProductRepository.findAll();
        assertThat(mainProductList).hasSize(databaseSizeBeforeCreate + 1);
        MainProduct testMainProduct = mainProductList.get(mainProductList.size() - 1);
        assertThat(testMainProduct.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testMainProduct.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testMainProduct.getPhoto()).isEqualTo(DEFAULT_PHOTO);
        assertThat(testMainProduct.getPhotoContentType()).isEqualTo(DEFAULT_PHOTO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createMainProductWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mainProductRepository.findAll().size();

        // Create the MainProduct with an existing ID
        mainProduct.setId(1L);
        MainProductDTO mainProductDTO = mainProductMapper.toDto(mainProduct);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMainProductMockMvc.perform(post("/api/main-products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mainProductDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MainProduct in the database
        List<MainProduct> mainProductList = mainProductRepository.findAll();
        assertThat(mainProductList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = mainProductRepository.findAll().size();
        // set the field null
        mainProduct.setTitle(null);

        // Create the MainProduct, which fails.
        MainProductDTO mainProductDTO = mainProductMapper.toDto(mainProduct);

        restMainProductMockMvc.perform(post("/api/main-products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mainProductDTO)))
            .andExpect(status().isBadRequest());

        List<MainProduct> mainProductList = mainProductRepository.findAll();
        assertThat(mainProductList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMainProducts() throws Exception {
        // Initialize the database
        mainProductRepository.saveAndFlush(mainProduct);

        // Get all the mainProductList
        restMainProductMockMvc.perform(get("/api/main-products?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mainProduct.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].photoContentType").value(hasItem(DEFAULT_PHOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(Base64Utils.encodeToString(DEFAULT_PHOTO))));
    }
    

    @Test
    @Transactional
    public void getMainProduct() throws Exception {
        // Initialize the database
        mainProductRepository.saveAndFlush(mainProduct);

        // Get the mainProduct
        restMainProductMockMvc.perform(get("/api/main-products/{id}", mainProduct.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mainProduct.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.photoContentType").value(DEFAULT_PHOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.photo").value(Base64Utils.encodeToString(DEFAULT_PHOTO)));
    }
    @Test
    @Transactional
    public void getNonExistingMainProduct() throws Exception {
        // Get the mainProduct
        restMainProductMockMvc.perform(get("/api/main-products/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMainProduct() throws Exception {
        // Initialize the database
        mainProductRepository.saveAndFlush(mainProduct);

        int databaseSizeBeforeUpdate = mainProductRepository.findAll().size();

        // Update the mainProduct
        MainProduct updatedMainProduct = mainProductRepository.findById(mainProduct.getId()).get();
        // Disconnect from session so that the updates on updatedMainProduct are not directly saved in db
        em.detach(updatedMainProduct);
        updatedMainProduct
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE);
        MainProductDTO mainProductDTO = mainProductMapper.toDto(updatedMainProduct);

        restMainProductMockMvc.perform(put("/api/main-products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mainProductDTO)))
            .andExpect(status().isOk());

        // Validate the MainProduct in the database
        List<MainProduct> mainProductList = mainProductRepository.findAll();
        assertThat(mainProductList).hasSize(databaseSizeBeforeUpdate);
        MainProduct testMainProduct = mainProductList.get(mainProductList.size() - 1);
        assertThat(testMainProduct.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testMainProduct.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMainProduct.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testMainProduct.getPhotoContentType()).isEqualTo(UPDATED_PHOTO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingMainProduct() throws Exception {
        int databaseSizeBeforeUpdate = mainProductRepository.findAll().size();

        // Create the MainProduct
        MainProductDTO mainProductDTO = mainProductMapper.toDto(mainProduct);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException 
        restMainProductMockMvc.perform(put("/api/main-products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mainProductDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MainProduct in the database
        List<MainProduct> mainProductList = mainProductRepository.findAll();
        assertThat(mainProductList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMainProduct() throws Exception {
        // Initialize the database
        mainProductRepository.saveAndFlush(mainProduct);

        int databaseSizeBeforeDelete = mainProductRepository.findAll().size();

        // Get the mainProduct
        restMainProductMockMvc.perform(delete("/api/main-products/{id}", mainProduct.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MainProduct> mainProductList = mainProductRepository.findAll();
        assertThat(mainProductList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MainProduct.class);
        MainProduct mainProduct1 = new MainProduct();
        mainProduct1.setId(1L);
        MainProduct mainProduct2 = new MainProduct();
        mainProduct2.setId(mainProduct1.getId());
        assertThat(mainProduct1).isEqualTo(mainProduct2);
        mainProduct2.setId(2L);
        assertThat(mainProduct1).isNotEqualTo(mainProduct2);
        mainProduct1.setId(null);
        assertThat(mainProduct1).isNotEqualTo(mainProduct2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MainProductDTO.class);
        MainProductDTO mainProductDTO1 = new MainProductDTO();
        mainProductDTO1.setId(1L);
        MainProductDTO mainProductDTO2 = new MainProductDTO();
        assertThat(mainProductDTO1).isNotEqualTo(mainProductDTO2);
        mainProductDTO2.setId(mainProductDTO1.getId());
        assertThat(mainProductDTO1).isEqualTo(mainProductDTO2);
        mainProductDTO2.setId(2L);
        assertThat(mainProductDTO1).isNotEqualTo(mainProductDTO2);
        mainProductDTO1.setId(null);
        assertThat(mainProductDTO1).isNotEqualTo(mainProductDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(mainProductMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(mainProductMapper.fromId(null)).isNull();
    }
}
