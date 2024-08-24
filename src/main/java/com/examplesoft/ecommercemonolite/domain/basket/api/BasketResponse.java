package com.examplesoft.ecommercemonolite.domain.basket.api;

import com.examplesoft.ecommercemonolite.domain.basket.dto.BasketProductDto;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class BasketResponse {
    private BigDecimal totalAmount;
    private List<BasketProductDto> basketProducts;
}
