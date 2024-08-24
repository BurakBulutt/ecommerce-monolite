package com.examplesoft.ecommercemonolite.domain.category.repo;

import com.examplesoft.ecommercemonolite.domain.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, String> {
    Optional<Category> findBySlug(String slug);
    List<Category> findAllByParentId(String parentId);
}
