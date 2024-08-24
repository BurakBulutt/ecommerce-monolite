package com.examplesoft.ecommercemonolite.domain.basket.repo;

import com.examplesoft.ecommercemonolite.domain.basket.entity.Basket;
import com.examplesoft.ecommercemonolite.domain.basket.entity.BasketProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BasketProductRepository extends JpaRepository<BasketProduct,String> {
    List<BasketProduct> findAllByBasketId(String basketId);
}
