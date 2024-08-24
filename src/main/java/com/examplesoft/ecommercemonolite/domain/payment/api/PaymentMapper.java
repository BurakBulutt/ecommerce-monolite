package com.examplesoft.ecommercemonolite.domain.payment.api;

import com.examplesoft.ecommercemonolite.domain.payment.dto.PaymentDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PaymentMapper {


    public static PaymentDto toDto(PaymentRequest request){
        return PaymentDto.builder()
                .billingAddress(request.billingAddress())
                .deliveryAddress(request.deliveryAddress())
                .build();
    }
}
