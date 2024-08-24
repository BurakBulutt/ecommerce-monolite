package com.examplesoft.ecommercemonolite.domain.order.repo;

import com.examplesoft.ecommercemonolite.domain.order.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem,String> {
    List<OrderItem> findAllByOrderId(String orderId);
}
