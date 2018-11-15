package io.vitronmfg.application.service.mapper;

import io.vitronmfg.application.domain.*;
import io.vitronmfg.application.service.dto.MainProductDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity MainProduct and its DTO MainProductDTO.
 */
@Mapper(componentModel = "spring", uses = {CategoryMapper.class})
public interface MainProductMapper extends EntityMapper<MainProductDTO, MainProduct> {

    @Mapping(source = "category.id", target = "categoryId")
    MainProductDTO toDto(MainProduct mainProduct);

    @Mapping(source = "categoryId", target = "category")
    MainProduct toEntity(MainProductDTO mainProductDTO);

    default MainProduct fromId(Long id) {
        if (id == null) {
            return null;
        }
        MainProduct mainProduct = new MainProduct();
        mainProduct.setId(id);
        return mainProduct;
    }
}
