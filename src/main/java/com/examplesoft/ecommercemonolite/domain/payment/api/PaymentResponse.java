package com.examplesoft.ecommercemonolite.domain.payment.api;

import com.examplesoft.ecommercemonolite.domain.user.dto.UserDto;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class PaymentResponse {
    private String id;
    private String deliveryAddress;
    private String billingAddress;
    private BigDecimal price;
    private UserDto user;
}
