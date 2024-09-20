package com.examplesoft.ecommercemonolite.domain.favoriteproduct.dto;

import com.examplesoft.ecommercemonolite.domain.product.dto.ProductDto;
import com.examplesoft.ecommercemonolite.domain.user.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteProductDto {
    private String id;
    private ProductDto product;
    private UserDto user;
}
