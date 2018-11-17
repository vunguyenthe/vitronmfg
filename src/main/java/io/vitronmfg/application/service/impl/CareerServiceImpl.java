package io.vitronmfg.application.service.impl;

import io.vitronmfg.application.service.CareerService;
import io.vitronmfg.application.domain.Career;
import io.vitronmfg.application.repository.CareerRepository;
import io.vitronmfg.application.service.dto.CareerDTO;
import io.vitronmfg.application.service.mapper.CareerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing Career.
 */
@Service
@Transactional
public class CareerServiceImpl implements CareerService {

    private final Logger log = LoggerFactory.getLogger(CareerServiceImpl.class);

    private final CareerRepository careerRepository;

    private final CareerMapper careerMapper;

    public CareerServiceImpl(CareerRepository careerRepository, CareerMapper careerMapper) {
        this.careerRepository = careerRepository;
        this.careerMapper = careerMapper;
    }

    /**
     * Save a career.
     *
     * @param careerDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CareerDTO save(CareerDTO careerDTO) {
        log.debug("Request to save Career : {}", careerDTO);
        Career career = careerMapper.toEntity(careerDTO);
        career = careerRepository.save(career);
        return careerMapper.toDto(career);
    }

    /**
     * Get all the careers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CareerDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Careers");
        return careerRepository.findAll(pageable)
            .map(careerMapper::toDto);
    }


    /**
     * Get one career by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CareerDTO> findOne(Long id) {
        log.debug("Request to get Career : {}", id);
        return careerRepository.findById(id)
            .map(careerMapper::toDto);
    }

    /**
     * Delete the career by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Career : {}", id);
        careerRepository.deleteById(id);
    }
}
