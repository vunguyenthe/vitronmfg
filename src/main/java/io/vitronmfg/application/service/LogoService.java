package io.vitronmfg.application.service;

import io.vitronmfg.application.service.dto.LogoDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Logo.
 */
public interface LogoService {

    /**
     * Save a logo.
     *
     * @param logoDTO the entity to save
     * @return the persisted entity
     */
    LogoDTO save(LogoDTO logoDTO);

    /**
     * Get all the logos.
     *
     * @return the list of entities
     */
    List<LogoDTO> findAll();


    /**
     * Get the "id" logo.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<LogoDTO> findOne(Long id);

    /**
     * Delete the "id" logo.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
