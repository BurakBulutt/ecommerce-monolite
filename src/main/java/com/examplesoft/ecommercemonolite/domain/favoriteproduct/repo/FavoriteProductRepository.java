package com.examplesoft.ecommercemonolite.domain.favoriteproduct.repo;

import com.examplesoft.ecommercemonolite.domain.favoriteproduct.entity.FavoriteProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface FavoriteProductRepository extends JpaRepository<FavoriteProduct,String> {
    Page<FavoriteProduct> findAllByUserId(String userId, Pageable pageable);
    List<FavoriteProduct> findAllByUserId(String userId);
    Optional<FavoriteProduct> findByUserIdAndProductId(String userId, String productId);
}
