package com.examplesoft.ecommercemonolite.domain.order.repo;

import com.examplesoft.ecommercemonolite.domain.order.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, String> {
    Optional<Order> findByCode(String code);
    Page<Order> findAllByUserId(String userId, Pageable pageable);
}
