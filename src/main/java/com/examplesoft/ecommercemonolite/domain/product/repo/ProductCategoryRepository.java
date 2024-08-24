package com.examplesoft.ecommercemonolite.domain.product.repo;

import com.examplesoft.ecommercemonolite.domain.product.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, String> {
    List<ProductCategory> findAllByCategoryId(String categoryId);
}
