package io.vitronmfg.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.vitronmfg.application.service.LogoService;
import io.vitronmfg.application.web.rest.errors.BadRequestAlertException;
import io.vitronmfg.application.web.rest.util.HeaderUtil;
import io.vitronmfg.application.service.dto.LogoDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Logo.
 */
@RestController
@RequestMapping("/api")
public class LogoResource {

    private final Logger log = LoggerFactory.getLogger(LogoResource.class);

    private static final String ENTITY_NAME = "logo";

    private final LogoService logoService;

    public LogoResource(LogoService logoService) {
        this.logoService = logoService;
    }

    /**
     * POST  /logos : Create a new logo.
     *
     * @param logoDTO the logoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new logoDTO, or with status 400 (Bad Request) if the logo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/logos")
    @Timed
    public ResponseEntity<LogoDTO> createLogo(@RequestBody LogoDTO logoDTO) throws URISyntaxException {
        log.debug("REST request to save Logo : {}", logoDTO);
        if (logoDTO.getId() != null) {
            throw new BadRequestAlertException("A new logo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LogoDTO result = logoService.save(logoDTO);
        return ResponseEntity.created(new URI("/api/logos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /logos : Updates an existing logo.
     *
     * @param logoDTO the logoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated logoDTO,
     * or with status 400 (Bad Request) if the logoDTO is not valid,
     * or with status 500 (Internal Server Error) if the logoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/logos")
    @Timed
    public ResponseEntity<LogoDTO> updateLogo(@RequestBody LogoDTO logoDTO) throws URISyntaxException {
        log.debug("REST request to update Logo : {}", logoDTO);
        if (logoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LogoDTO result = logoService.save(logoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, logoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /logos : get all the logos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of logos in body
     */
    @GetMapping("/logos")
    @Timed
    public List<LogoDTO> getAllLogos() {
        log.debug("REST request to get all Logos");
        return logoService.findAll();
    }

    /**
     * GET  /logos/:id : get the "id" logo.
     *
     * @param id the id of the logoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the logoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/logos/{id}")
    @Timed
    public ResponseEntity<LogoDTO> getLogo(@PathVariable Long id) {
        log.debug("REST request to get Logo : {}", id);
        Optional<LogoDTO> logoDTO = logoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(logoDTO);
    }

    /**
     * DELETE  /logos/:id : delete the "id" logo.
     *
     * @param id the id of the logoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/logos/{id}")
    @Timed
    public ResponseEntity<Void> deleteLogo(@PathVariable Long id) {
        log.debug("REST request to delete Logo : {}", id);
        logoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
