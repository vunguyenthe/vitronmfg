package io.vitronmfg.application.service;

import io.vitronmfg.application.service.dto.CareerDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Career.
 */
public interface CareerService {

    /**
     * Save a career.
     *
     * @param careerDTO the entity to save
     * @return the persisted entity
     */
    CareerDTO save(CareerDTO careerDTO);

    /**
     * Get all the careers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CareerDTO> findAll(Pageable pageable);


    /**
     * Get the "id" career.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<CareerDTO> findOne(Long id);

    /**
     * Delete the "id" career.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
