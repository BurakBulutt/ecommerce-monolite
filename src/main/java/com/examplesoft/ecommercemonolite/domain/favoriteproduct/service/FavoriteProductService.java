package com.examplesoft.ecommercemonolite.domain.favoriteproduct.service;

import com.examplesoft.ecommercemonolite.domain.favoriteproduct.dto.FavoriteProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FavoriteProductService {
    Page<FavoriteProductDto> getUserFavoriteProducts(Pageable pageable);
    List<FavoriteProductDto> getUserFavoriteProducts(String userId);
    FavoriteProductDto isFavoriteProduct(String productId);
    FavoriteProductDto save(String productId);
    void delete(String id);
}
