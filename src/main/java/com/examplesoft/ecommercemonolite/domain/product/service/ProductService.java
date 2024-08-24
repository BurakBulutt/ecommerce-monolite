package com.examplesoft.ecommercemonolite.domain.product.service;

import com.examplesoft.ecommercemonolite.domain.product.dto.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    Page<ProductDto> getAll(Pageable pageable);
    Page<ProductDto> filter(String categorySlug, Pageable pageable);
    ProductDto getById(String id);
    ProductDto getBySlug(String slug);
    ProductDto save(ProductDto productDto);
    ProductDto update(String id, ProductDto productDto);
    void delete(String id);
}
