package com.examplesoft.ecommercemonolite.domain.category.api;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryResponse {
    private String id;
    private String name;
    private String description;
    private String slug;
    private String parentId;
}
