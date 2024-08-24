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
                .orderAddress(dto.getOrderAddress())
                .amount(dto.getAmount())
                .paymentId(dto.getPaymentId())
                .shipmentDate(dto.getShipmentDate())
                .status(dto.getStatus())
                .userId(dto.getUserId())
                .build();
    }

    public static Page<OrderResponse> toPageResponse(Page<OrderDto> dtoPage) {
        return PageUtil.toPage(dtoPage, OrderMapper::toResponse);
    }
}
