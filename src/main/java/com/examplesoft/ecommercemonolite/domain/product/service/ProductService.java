package com.examplesoft.ecommercemonolite.domain.product.service;

import com.examplesoft.ecommercemonolite.domain.product.dto.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

public interface ProductService {
    Page<ProductDto> getAll(Pageable pageable);

    List<ProductDto> getAllByIdIn(Set<String> ids);

    Page<ProductDto> filter(String categorySlug, Pageable pageable);
    Page<ProductDto> searchFilter(String keyword, Pageable pageable);
    ProductDto getById(String id);
    ProductDto getBySlug(String slug);
    ProductDto save(ProductDto productDto);
    ProductDto update(String id, ProductDto productDto);
    void delete(String id);
}
