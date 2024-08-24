package com.examplesoft.ecommercemonolite.domain.basket.dto;


import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class BasketDto {
    private String id;
    private String userId;
    private BigDecimal totalAmount;
    private List<BasketProductDto> basketProducts;
}
