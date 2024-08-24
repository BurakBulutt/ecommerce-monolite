package com.examplesoft.ecommercemonolite.domain.category.service;

import com.examplesoft.ecommercemonolite.domain.category.dto.CategoryDto;
import com.examplesoft.ecommercemonolite.domain.category.dto.CategoryTreeDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {
    Page<CategoryDto> getAll(Pageable pageable);
    Page<CategoryDto> filter(Pageable pageable);
    CategoryDto getById(String id);
    CategoryDto getBySlug(String slug);
    CategoryDto save(CategoryDto categoryDto);
    CategoryDto update(String id,CategoryDto categoryDto);
    void delete(String id);

    List<CategoryTreeDto> getMainCategoriesTree();
}
