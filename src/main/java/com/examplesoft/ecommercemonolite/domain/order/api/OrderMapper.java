package com.examplesoft.ecommercemonolite.domain.order.api;

import com.examplesoft.ecommercemonolite.domain.order.dto.OrderDto;
import com.examplesoft.ecommercemonolite.util.PageUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderMapper {

    public static OrderResponse toResponse(OrderDto dto) {
        return OrderResponse.builder()
                .id(dto.getId())
                .code(dto.getCode())
                .orderItems(dto.getOrderItems())
                .billingAddress(dto.getBillingAddress())
                .deliveryAddress(dto.getDeliveryAddress())
                .amount(dto.getAmount())
                .payment(dto.getPayment())
                .shipmentDate(dto.getShipmentDate())
                .status(dto.getStatus())
                .user(dto.getUser())
                .build();
    }

    public static Page<OrderResponse> toPageResponse(Page<OrderDto> dtoPage) {
        return PageUtil.toPage(dtoPage, OrderMapper::toResponse);
    }
}
