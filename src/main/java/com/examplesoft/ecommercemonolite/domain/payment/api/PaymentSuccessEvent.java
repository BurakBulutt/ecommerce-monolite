package com.examplesoft.ecommercemonolite.domain.payment.api;

import com.examplesoft.ecommercemonolite.domain.payment.dto.PaymentDto;

public record PaymentSuccessEvent(
        PaymentDto payment
) {
}
