package com.examplesoft.ecommercemonolite.domain.basket.repo;

import com.examplesoft.ecommercemonolite.domain.basket.entity.Basket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BasketRepository extends JpaRepository<Basket,String> {
    Optional<Basket> findByUserId(String userId);
}
