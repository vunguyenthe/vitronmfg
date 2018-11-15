package io.vitronmfg.application.service.mapper;

import io.vitronmfg.application.domain.*;
import io.vitronmfg.application.service.dto.SlideDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Slide and its DTO SlideDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SlideMapper extends EntityMapper<SlideDTO, Slide> {



    default Slide fromId(Long id) {
        if (id == null) {
            return null;
        }
        Slide slide = new Slide();
        slide.setId(id);
        return slide;
    }
}
