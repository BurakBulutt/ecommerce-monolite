package com.examplesoft.ecommercemonolite.domain.payment.dto;

import com.examplesoft.ecommercemonolite.domain.user.dto.UserDto;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class PaymentDto {
    private String id;
    private String deliveryAddress;
    private String billingAddress;
    private BigDecimal price;
    private UserDto user;
}
