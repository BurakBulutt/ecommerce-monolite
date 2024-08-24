package com.examplesoft.ecommercemonolite.domain.basket.api;

public record AddProductRequest(
        String productId,
        Integer quantity
) {
}
