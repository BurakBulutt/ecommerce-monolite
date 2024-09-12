package com.examplesoft.ecommercemonolite.domain.product.repo;

import com.examplesoft.ecommercemonolite.domain.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, String> {
    Optional<Product> findBySlug(String slug);

    List<Product> findAllByMainCategoryId(String mainCategoryId);

    @Query(value = """
            SELECT p FROM Product p
            LEFT JOIN Category c on c.id = p.mainCategoryId
            """,
    countQuery = """
            SELECT count(p) FROM Product p
            LEFT JOIN Category c on c.id = p.mainCategoryId
            """)
    Page<Product> productFilter(@Param("categoryId") String categoryId,
                                Pageable pageable);

    List<Product> findAllByNameContaining(String name);


}
