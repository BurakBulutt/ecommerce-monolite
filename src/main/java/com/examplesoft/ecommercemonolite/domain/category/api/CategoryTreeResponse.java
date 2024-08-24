package com.examplesoft.ecommercemonolite.domain.category.api;

import com.examplesoft.ecommercemonolite.domain.category.dto.CategoryTreeDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CategoryTreeResponse {
    private String id;
    private String name;
    private String description;
    private String slug;
    private String parentId;
    private List<CategoryTreeDto> childrensList;
}
