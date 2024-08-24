package com.examplesoft.ecommercemonolite.domain.basket.api;

public record UpdateQuantityRequest(
        String basketProductId,
        Integer quantity
) {
}
