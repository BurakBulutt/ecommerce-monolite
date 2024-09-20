package com.examplesoft.ecommercemonolite.domain.favoriteproduct.api;

import com.examplesoft.ecommercemonolite.domain.product.dto.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserFavoriteProductResponse {
    private String id;
    private ProductDto product;
}
