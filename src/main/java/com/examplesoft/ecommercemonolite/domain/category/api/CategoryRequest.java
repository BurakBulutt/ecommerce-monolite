package com.examplesoft.ecommercemonolite.domain.category.api;

public record CategoryRequest(
        String name,
        String description,
        String slug,
        String parentId
) {
}
