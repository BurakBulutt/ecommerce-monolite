package com.examplesoft.ecommercemonolite.domain.product.api;

import java.math.BigDecimal;

public record ProductRequest(
        String code,
        String name,
        String description,
        BigDecimal originalPrice,
        Integer quantity,
        String slug,
        String image,
        String mainCategoryId
) {
}
