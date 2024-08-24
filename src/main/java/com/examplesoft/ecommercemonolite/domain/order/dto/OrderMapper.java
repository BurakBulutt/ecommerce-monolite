package com.examplesoft.ecommercemonolite.domain.order.dto;

import com.examplesoft.ecommercemonolite.domain.order.entity.Order;
import com.examplesoft.ecommercemonolite.domain.order.entity.OrderItem;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderMapper {

    public static OrderDto toDto(Order entity, List<OrderItemDto> orderItems) {
        return OrderDto.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .amount(entity.getAmount())
                .shipmentDate(entity.getShipmentDate())
                .status(entity.getStatus())
                .paymentId(entity.getPaymentId())
                .orderAddress(entity.getOrderAddress())
                .orderItems(orderItems)
                .code(entity.getCode())
                .build();
    }

    public static OrderItemDto toItemDto(OrderItem entity) {
        return OrderItemDto.builder()
                .id(entity.getId())
                .quantity(entity.getQuantity())
                .productId(entity.getProductId())
                .totalAmount(entity.getTotalAmount())
                .build();
    }

    public static Order toEntity(Order entity,OrderDto dto) {
        entity.setUserId(dto.getUserId());
        entity.setShipmentDate(dto.getShipmentDate());
        entity.setStatus(dto.getStatus());
        entity.setPaymentId(dto.getPaymentId());
        entity.setOrderAddress(dto.getOrderAddress());
        entity.setAmount(dto.getAmount());
        entity.setCode(dto.getCode());

        return entity;
    }
}
