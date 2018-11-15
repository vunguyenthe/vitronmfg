package io.vitronmfg.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.vitronmfg.application.service.MainProductService;
import io.vitronmfg.application.web.rest.errors.BadRequestAlertException;
import io.vitronmfg.application.web.rest.util.HeaderUtil;
import io.vitronmfg.application.service.dto.MainProductDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing MainProduct.
 */
@RestController
@RequestMapping("/api")
public class MainProductResource {

    private final Logger log = LoggerFactory.getLogger(MainProductResource.class);

    private static final String ENTITY_NAME = "mainProduct";

    private final MainProductService mainProductService;

    public MainProductResource(MainProductService mainProductService) {
        this.mainProductService = mainProductService;
    }

    /**
     * POST  /main-products : Create a new mainProduct.
     *
     * @param mainProductDTO the mainProductDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mainProductDTO, or with status 400 (Bad Request) if the mainProduct has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/main-products")
    @Timed
    public ResponseEntity<MainProductDTO> createMainProduct(@Valid @RequestBody MainProductDTO mainProductDTO) throws URISyntaxException {
        log.debug("REST request to save MainProduct : {}", mainProductDTO);
        if (mainProductDTO.getId() != null) {
            throw new BadRequestAlertException("A new mainProduct cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MainProductDTO result = mainProductService.save(mainProductDTO);
        return ResponseEntity.created(new URI("/api/main-products/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /main-products : Updates an existing mainProduct.
     *
     * @param mainProductDTO the mainProductDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mainProductDTO,
     * or with status 400 (Bad Request) if the mainProductDTO is not valid,
     * or with status 500 (Internal Server Error) if the mainProductDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/main-products")
    @Timed
    public ResponseEntity<MainProductDTO> updateMainProduct(@Valid @RequestBody MainProductDTO mainProductDTO) throws URISyntaxException {
        log.debug("REST request to update MainProduct : {}", mainProductDTO);
        if (mainProductDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MainProductDTO result = mainProductService.save(mainProductDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mainProductDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /main-products : get all the mainProducts.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of mainProducts in body
     */
    @GetMapping("/main-products")
    @Timed
    public List<MainProductDTO> getAllMainProducts() {
        log.debug("REST request to get all MainProducts");
        return mainProductService.findAll();
    }

    /**
     * GET  /main-products/:id : get the "id" mainProduct.
     *
     * @param id the id of the mainProductDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mainProductDTO, or with status 404 (Not Found)
     */
    @GetMapping("/main-products/{id}")
    @Timed
    public ResponseEntity<MainProductDTO> getMainProduct(@PathVariable Long id) {
        log.debug("REST request to get MainProduct : {}", id);
        Optional<MainProductDTO> mainProductDTO = mainProductService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mainProductDTO);
    }

    /**
     * DELETE  /main-products/:id : delete the "id" mainProduct.
     *
     * @param id the id of the mainProductDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/main-products/{id}")
    @Timed
    public ResponseEntity<Void> deleteMainProduct(@PathVariable Long id) {
        log.debug("REST request to delete MainProduct : {}", id);
        mainProductService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
