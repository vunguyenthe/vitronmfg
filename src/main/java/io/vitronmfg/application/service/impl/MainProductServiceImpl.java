package io.vitronmfg.application.service.impl;

import io.vitronmfg.application.service.MainProductService;
import io.vitronmfg.application.domain.MainProduct;
import io.vitronmfg.application.repository.MainProductRepository;
import io.vitronmfg.application.service.dto.MainProductDTO;
import io.vitronmfg.application.service.mapper.MainProductMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * Service Implementation for managing MainProduct.
 */
@Service
@Transactional
public class MainProductServiceImpl implements MainProductService {

    private final Logger log = LoggerFactory.getLogger(MainProductServiceImpl.class);

    private final MainProductRepository mainProductRepository;

    private final MainProductMapper mainProductMapper;

    public MainProductServiceImpl(MainProductRepository mainProductRepository, MainProductMapper mainProductMapper) {
        this.mainProductRepository = mainProductRepository;
        this.mainProductMapper = mainProductMapper;
    }

    /**
     * Save a mainProduct.
     *
     * @param mainProductDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MainProductDTO save(MainProductDTO mainProductDTO) {
        log.debug("Request to save MainProduct : {}", mainProductDTO);
        MainProduct mainProduct = mainProductMapper.toEntity(mainProductDTO);
        mainProduct = mainProductRepository.save(mainProduct);
        return mainProductMapper.toDto(mainProduct);
    }

    /**
     * Get all the mainProducts.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<MainProductDTO> findAll() {
        log.debug("Request to get all MainProducts");
        return mainProductRepository.findAll().stream()
            .map(mainProductMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one mainProduct by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<MainProductDTO> findOne(Long id) {
        log.debug("Request to get MainProduct : {}", id);
        return mainProductRepository.findById(id)
            .map(mainProductMapper::toDto);
    }

    /**
     * Delete the mainProduct by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MainProduct : {}", id);
        mainProductRepository.deleteById(id);
    }
}
