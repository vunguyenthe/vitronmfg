package io.vitronmfg.application.service.impl;

import io.vitronmfg.application.service.LogoService;
import io.vitronmfg.application.domain.Logo;
import io.vitronmfg.application.repository.LogoRepository;
import io.vitronmfg.application.service.dto.LogoDTO;
import io.vitronmfg.application.service.mapper.LogoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * Service Implementation for managing Logo.
 */
@Service
@Transactional
public class LogoServiceImpl implements LogoService {

    private final Logger log = LoggerFactory.getLogger(LogoServiceImpl.class);

    private final LogoRepository logoRepository;

    private final LogoMapper logoMapper;

    public LogoServiceImpl(LogoRepository logoRepository, LogoMapper logoMapper) {
        this.logoRepository = logoRepository;
        this.logoMapper = logoMapper;
    }

    /**
     * Save a logo.
     *
     * @param logoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public LogoDTO save(LogoDTO logoDTO) {
        log.debug("Request to save Logo : {}", logoDTO);
        Logo logo = logoMapper.toEntity(logoDTO);
        logo = logoRepository.save(logo);
        return logoMapper.toDto(logo);
    }

    /**
     * Get all the logos.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<LogoDTO> findAll() {
        log.debug("Request to get all Logos");
        return logoRepository.findAll().stream()
            .map(logoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one logo by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<LogoDTO> findOne(Long id) {
        log.debug("Request to get Logo : {}", id);
        return logoRepository.findById(id)
            .map(logoMapper::toDto);
    }

    /**
     * Delete the logo by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Logo : {}", id);
        logoRepository.deleteById(id);
    }
}
