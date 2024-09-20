package com.examplesoft.ecommercemonolite.domain.favoriteproduct.api;

import com.examplesoft.ecommercemonolite.domain.favoriteproduct.dto.FavoriteProductDto;
import com.examplesoft.ecommercemonolite.util.PageUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FavoriteProductMapper {

    public static UserFavoriteProductResponse toResponse(FavoriteProductDto favoriteProductDto){
        return UserFavoriteProductResponse.builder()
                .id(favoriteProductDto.getId())
                .product(favoriteProductDto.getProduct())
                .build();
    }

    public static Page<UserFavoriteProductResponse> toPageResponse(Page<FavoriteProductDto> favoriteProductDtos){
        return PageUtil.toPage(favoriteProductDtos,FavoriteProductMapper::toResponse);
    }
}
