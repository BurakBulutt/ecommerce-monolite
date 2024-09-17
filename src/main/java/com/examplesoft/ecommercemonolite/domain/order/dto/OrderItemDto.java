package com.examplesoft.ecommercemonolite.domain.order.dto;

import com.examplesoft.ecommercemonolite.domain.product.dto.ProductDto;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class OrderItemDto {
    private String id;
    private String orderId;
    private ProductDto product;
    private Integer quantity;
    private BigDecimal totalAmount;
}
