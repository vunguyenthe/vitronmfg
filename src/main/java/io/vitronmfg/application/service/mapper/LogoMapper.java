package io.vitronmfg.application.service.mapper;

import io.vitronmfg.application.domain.*;
import io.vitronmfg.application.service.dto.LogoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Logo and its DTO LogoDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LogoMapper extends EntityMapper<LogoDTO, Logo> {



    default Logo fromId(Long id) {
        if (id == null) {
            return null;
        }
        Logo logo = new Logo();
        logo.setId(id);
        return logo;
    }
}
