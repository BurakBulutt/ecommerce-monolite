package com.examplesoft.ecommercemonolite.domain.category.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CategoryTreeDto {
    private String id;
    private String name;
    private String description;
    private String slug;
    private String parentId;
    private List<CategoryTreeDto> childrensList;
}
