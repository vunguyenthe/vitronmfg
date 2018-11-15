package io.vitronmfg.application.service.mapper;

import io.vitronmfg.application.domain.*;
import io.vitronmfg.application.service.dto.NewsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity News and its DTO NewsDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface NewsMapper extends EntityMapper<NewsDTO, News> {



    default News fromId(Long id) {
        if (id == null) {
            return null;
        }
        News news = new News();
        news.setId(id);
        return news;
    }
}
