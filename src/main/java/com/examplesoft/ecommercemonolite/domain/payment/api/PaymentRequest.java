package com.examplesoft.ecommercemonolite.domain.payment.api;

public record PaymentRequest(
        String deliveryAddress,
        String billingAddress
) {
}
