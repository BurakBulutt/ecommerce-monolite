package com.examplesoft.ecommercemonolite.domain.order.service;

import com.examplesoft.ecommercemonolite.domain.order.dto.OrderDto;
import com.examplesoft.ecommercemonolite.domain.order.entity.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {
    Page<OrderDto> getAll(Pageable pageable);
    OrderDto getOrderByCode(String code);
    Page<OrderDto> filter(Pageable pageable);
    Page<OrderDto> getUserOrders(Pageable pageable);
    OrderDto updateOrderStatus(String id, OrderStatus status);
    void deleteOrder(String id);
}
