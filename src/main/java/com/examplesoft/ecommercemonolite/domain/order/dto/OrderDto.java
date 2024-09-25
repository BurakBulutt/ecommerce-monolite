package com.examplesoft.ecommercemonolite.domain.order.dto;

import com.examplesoft.ecommercemonolite.domain.order.entity.OrderStatus;
import com.examplesoft.ecommercemonolite.domain.payment.dto.PaymentDto;
import com.examplesoft.ecommercemonolite.domain.user.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private String id;
    private UserDto user;
    private BigDecimal amount;
    private Date shipmentDate;
    private String billingAddress;
    private String deliveryAddress;
    private OrderStatus status;
    private PaymentDto payment;
    private List<OrderItemDto> orderItems;
    private String code;
}
