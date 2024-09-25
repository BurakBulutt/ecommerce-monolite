package com.examplesoft.ecommercemonolite.domain.order.dto;

import com.examplesoft.ecommercemonolite.domain.order.entity.Order;
import com.examplesoft.ecommercemonolite.domain.order.entity.OrderItem;
import com.examplesoft.ecommercemonolite.domain.payment.dto.PaymentDto;
import com.examplesoft.ecommercemonolite.domain.product.dto.ProductDto;
import com.examplesoft.ecommercemonolite.domain.user.dto.UserDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderMapper {

    public static OrderDto toDto(Order entity, UserDto user, PaymentDto payment,List<OrderItemDto> orderItems) {
        return OrderDto.builder()
                .id(entity.getId())
                .user(user)
                .amount(entity.getAmount())
                .shipmentDate(entity.getShipmentDate())
                .status(entity.getStatus())
                .payment(payment)
                .billingAddress(entity.getBillingAddress())
                .deliveryAddress(entity.getDeliveryAddress())
                .orderItems(orderItems)
                .code(entity.getCode())
                .build();
    }

    public static OrderItemDto toItemDto(OrderItem entity, ProductDto productDto) {
        return OrderItemDto.builder()
                .id(entity.getId())
                .quantity(entity.getQuantity())
                .product(productDto)
                .totalAmount(entity.getTotalAmount())
                .orderId(entity.getOrderId())
                .build();
    }

    public static Order toEntity(Order entity,OrderDto dto) {
        entity.setUserId(dto.getUser().getId());
        entity.setShipmentDate(dto.getShipmentDate());
        entity.setStatus(dto.getStatus());
        entity.setPaymentId(dto.getPayment().getId());
        entity.setBillingAddress(dto.getBillingAddress());
        entity.setDeliveryAddress(dto.getDeliveryAddress());
        entity.setAmount(dto.getAmount());
        entity.setCode(dto.getCode());

        return entity;
    }
}
