package com.examplesoft.ecommercemonolite.domain.category.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryDto {
    private String id;
    private String name;
    private String description;
    private String slug;
    private String parentId;
}
