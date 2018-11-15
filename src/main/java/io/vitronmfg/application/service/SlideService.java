package io.vitronmfg.application.service;

import io.vitronmfg.application.service.dto.SlideDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Slide.
 */
public interface SlideService {

    /**
     * Save a slide.
     *
     * @param slideDTO the entity to save
     * @return the persisted entity
     */
    SlideDTO save(SlideDTO slideDTO);

    /**
     * Get all the slides.
     *
     * @return the list of entities
     */
    List<SlideDTO> findAll();


    /**
     * Get the "id" slide.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<SlideDTO> findOne(Long id);

    /**
     * Delete the "id" slide.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
