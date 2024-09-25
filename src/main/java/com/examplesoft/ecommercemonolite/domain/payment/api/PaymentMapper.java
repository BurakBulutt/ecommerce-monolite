package com.examplesoft.ecommercemonolite.domain.payment.api;

import com.examplesoft.ecommercemonolite.domain.payment.dto.PaymentDto;
import com.examplesoft.ecommercemonolite.util.PageUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PaymentMapper {


    public static PaymentDto toDto(PaymentRequest request){
        return PaymentDto.builder()
                .billingAddress(request.billingAddress())
                .deliveryAddress(request.deliveryAddress())
                .build();
    }

    public static PaymentResponse toResponse(PaymentDto dto){
        return PaymentResponse.builder()
                .id(dto.getId())
                .billingAddress(dto.getBillingAddress())
                .deliveryAddress(dto.getDeliveryAddress())
                .price(dto.getPrice())
                .user(dto.getUser())
                .build();
    }

    public static Page<PaymentResponse> toPageResponse(Page<PaymentDto> dtos){
        return PageUtil.toPage(dtos,PaymentMapper::toResponse);
    }
}
