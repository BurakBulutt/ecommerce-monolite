package com.examplesoft.ecommercemonolite.domain.basket.api;

import com.examplesoft.ecommercemonolite.domain.basket.dto.BasketDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BasketMapper {

    public static BasketResponse toBasketResponse(BasketDto dto) {
        return BasketResponse.builder()
                .basketProducts(dto.getBasketProducts())
                .totalAmount(dto.getTotalAmount())
                .build();
    }
}
