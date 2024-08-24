package com.examplesoft.ecommercemonolite.domain.category.api;

import com.examplesoft.ecommercemonolite.domain.category.dto.CategoryDto;
import com.examplesoft.ecommercemonolite.domain.category.dto.CategoryTreeDto;
import com.examplesoft.ecommercemonolite.domain.category.entity.Category;
import com.examplesoft.ecommercemonolite.util.PageUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CategoryMapper {


    public static CategoryDto toDto(CategoryRequest request) {
        return CategoryDto.builder()
                .name(request.name())
                .description(request.description())
                .slug(request.slug())
                .parentId(request.parentId())
                .build();
    }

    public static CategoryResponse toResponse(CategoryDto dto) {
        return CategoryResponse.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .slug(dto.getSlug())
                .parentId(dto.getParentId())
                .build();
    }

    public static CategoryTreeResponse toTreeResponse(CategoryTreeDto dto) {
        return CategoryTreeResponse.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .slug(dto.getSlug())
                .parentId(dto.getParentId())
                .childrensList(dto.getChildrensList())
                .build();
    }

    public static Page<CategoryResponse> toPageResponse(Page<CategoryDto> categories) {
        return PageUtil.toPage(categories,CategoryMapper::toResponse);
    }

    public static List<CategoryTreeResponse> toDataTreeResponse(List<CategoryTreeDto> categories) {
        return categories.stream().map(CategoryMapper::toTreeResponse).toList();
    }
}
