package com.examplesoft.ecommercemonolite.domain.product.dto;

import com.examplesoft.ecommercemonolite.domain.category.dto.CategoryDto;
import com.examplesoft.ecommercemonolite.domain.product.entity.Product;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductMapper {

    public static ProductDto toDto(Product entity, CategoryDto mainCategory) {
        return ProductDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .price(entity.getPrice())
                .image(entity.getImage())
                .code(entity.getCode())
                .slug(entity.getSlug())
                .quantity(entity.getQuantity())
                .mainCategory(mainCategory)
                .build();
    }

    public static Product toEntity(Product entity,ProductDto dto) {
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setImage(dto.getImage());
        entity.setCode(dto.getCode());
        entity.setSlug(dto.getSlug());
        entity.setQuantity(dto.getQuantity());
        entity.setMainCategoryId(dto.getMainCategory().getId());

        return entity;
    }
}
