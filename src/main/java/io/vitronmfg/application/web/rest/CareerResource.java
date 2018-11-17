package io.vitronmfg.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.vitronmfg.application.service.CareerService;
import io.vitronmfg.application.web.rest.errors.BadRequestAlertException;
import io.vitronmfg.application.web.rest.util.HeaderUtil;
import io.vitronmfg.application.web.rest.util.PaginationUtil;
import io.vitronmfg.application.service.dto.CareerDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Career.
 */
@RestController
@RequestMapping("/api")
public class CareerResource {

    private final Logger log = LoggerFactory.getLogger(CareerResource.class);

    private static final String ENTITY_NAME = "career";

    private final CareerService careerService;

    public CareerResource(CareerService careerService) {
        this.careerService = careerService;
    }

    /**
     * POST  /careers : Create a new career.
     *
     * @param careerDTO the careerDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new careerDTO, or with status 400 (Bad Request) if the career has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/careers")
    @Timed
    public ResponseEntity<CareerDTO> createCareer(@Valid @RequestBody CareerDTO careerDTO) throws URISyntaxException {
        log.debug("REST request to save Career : {}", careerDTO);
        if (careerDTO.getId() != null) {
            throw new BadRequestAlertException("A new career cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CareerDTO result = careerService.save(careerDTO);
        return ResponseEntity.created(new URI("/api/careers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /careers : Updates an existing career.
     *
     * @param careerDTO the careerDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated careerDTO,
     * or with status 400 (Bad Request) if the careerDTO is not valid,
     * or with status 500 (Internal Server Error) if the careerDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/careers")
    @Timed
    public ResponseEntity<CareerDTO> updateCareer(@Valid @RequestBody CareerDTO careerDTO) throws URISyntaxException {
        log.debug("REST request to update Career : {}", careerDTO);
        if (careerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CareerDTO result = careerService.save(careerDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, careerDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /careers : get all the careers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of careers in body
     */
    @GetMapping("/careers")
    @Timed
    public ResponseEntity<List<CareerDTO>> getAllCareers(Pageable pageable) {
        log.debug("REST request to get a page of Careers");
        Page<CareerDTO> page = careerService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/careers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /careers/:id : get the "id" career.
     *
     * @param id the id of the careerDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the careerDTO, or with status 404 (Not Found)
     */
    @GetMapping("/careers/{id}")
    @Timed
    public ResponseEntity<CareerDTO> getCareer(@PathVariable Long id) {
        log.debug("REST request to get Career : {}", id);
        Optional<CareerDTO> careerDTO = careerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(careerDTO);
    }

    /**
     * DELETE  /careers/:id : delete the "id" career.
     *
     * @param id the id of the careerDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/careers/{id}")
    @Timed
    public ResponseEntity<Void> deleteCareer(@PathVariable Long id) {
        log.debug("REST request to delete Career : {}", id);
        careerService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
