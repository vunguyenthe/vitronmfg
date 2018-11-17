package io.vitronmfg.application.service.mapper;

import io.vitronmfg.application.domain.*;
import io.vitronmfg.application.service.dto.CareerDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Career and its DTO CareerDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CareerMapper extends EntityMapper<CareerDTO, Career> {



    default Career fromId(Long id) {
        if (id == null) {
            return null;
        }
        Career career = new Career();
        career.setId(id);
        return career;
    }
}
