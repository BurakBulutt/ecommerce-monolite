package com.examplesoft.ecommercemonolite.domain.order.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class OrderItemDto {
    private String id;
    private String orderId;
    private String productId;
    private Integer quantity;
    private BigDecimal totalAmount;
}
