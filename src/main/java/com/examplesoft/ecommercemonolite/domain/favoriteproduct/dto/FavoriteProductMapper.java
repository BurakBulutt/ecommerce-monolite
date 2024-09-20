package com.examplesoft.ecommercemonolite.domain.favoriteproduct.dto;

import com.examplesoft.ecommercemonolite.domain.favoriteproduct.entity.FavoriteProduct;
import com.examplesoft.ecommercemonolite.domain.product.dto.ProductDto;
import com.examplesoft.ecommercemonolite.domain.user.dto.UserDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FavoriteProductMapper {

    public static FavoriteProductDto toDto(FavoriteProduct entity, UserDto user, ProductDto product) {
        return FavoriteProductDto.builder()
                .id(entity.getId())
                .product(product)
                .user(user)
                .build();
    }

    public static FavoriteProduct toEntity(FavoriteProduct entity,FavoriteProductDto dto) {
        entity.setProductId(dto.getProduct().getId());
        entity.setUserId(dto.getUser().getId());
        return entity;
    }
}
