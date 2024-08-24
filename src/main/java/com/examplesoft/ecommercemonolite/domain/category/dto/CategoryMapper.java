package com.examplesoft.ecommercemonolite.domain.category.dto;

import com.examplesoft.ecommercemonolite.domain.category.entity.Category;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CategoryMapper {

    public static CategoryDto toDto(Category entity) {
        return CategoryDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .slug(entity.getSlug())
                .parentId(entity.getParentId())
                .build();
    }

    public static Category toEntity(Category entity,CategoryDto dto) {
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setSlug(dto.getSlug());
        entity.setParentId(dto.getParentId());

        return entity;
    }
}
