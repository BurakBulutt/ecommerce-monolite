package com.examplesoft.ecommercemonolite.domain.payment.api;

public record PaymentSuccessEvent(
        String paymentId,
        String deliveryAddress,
        String billingAddress
) {
}
