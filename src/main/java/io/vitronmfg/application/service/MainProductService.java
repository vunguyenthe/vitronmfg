package io.vitronmfg.application.service;

import io.vitronmfg.application.service.dto.MainProductDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing MainProduct.
 */
public interface MainProductService {

    /**
     * Save a mainProduct.
     *
     * @param mainProductDTO the entity to save
     * @return the persisted entity
     */
    MainProductDTO save(MainProductDTO mainProductDTO);

    /**
     * Get all the mainProducts.
     *
     * @return the list of entities
     */
    List<MainProductDTO> findAll();


    /**
     * Get the "id" mainProduct.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<MainProductDTO> findOne(Long id);

    /**
     * Delete the "id" mainProduct.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
