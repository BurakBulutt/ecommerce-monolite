package com.examplesoft.ecommercemonolite.domain.order.api;

import com.examplesoft.ecommercemonolite.domain.order.dto.OrderItemDto;
import com.examplesoft.ecommercemonolite.domain.order.entity.OrderStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@Builder
public class OrderResponse {
    private String id;
    private String userId;
    private BigDecimal amount;
    private Date shipmentDate;
    private String orderAddress;
    private OrderStatus status;
    private String paymentId;
    private List<OrderItemDto> orderItems;
    private String code;
}
