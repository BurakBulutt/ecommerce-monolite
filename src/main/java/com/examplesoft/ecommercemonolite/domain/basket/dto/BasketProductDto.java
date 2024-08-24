package com.examplesoft.ecommercemonolite.domain.basket.dto;

import com.examplesoft.ecommercemonolite.domain.product.dto.ProductDto;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class BasketProductDto {
    private String id;
    private String basketId;
    private ProductDto product;
    private Integer quantity;
    private BigDecimal totalAmount;
}
