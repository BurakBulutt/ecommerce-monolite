package com.examplesoft.ecommercemonolite.domain.order.entity;

import com.examplesoft.ecommercemonolite.entitylistener.service.EntityChangesListener;
import com.examplesoft.ecommercemonolite.util.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@EntityListeners(EntityChangesListener.class)
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = Order.TABLE)
public class Order extends BaseEntity {
    public static final String TABLE = "orders";

    @Column(nullable = false)
    private String userId;
    @Column(nullable = false)
    private BigDecimal amount;
    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date shipmentDate;
    @Column(nullable = false)
    private String billingAddress;
    @Column(nullable = false)
    private String deliveryAddress;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;
    @Column(nullable = false, unique = true)
    private String paymentId;
    @Column(nullable = false, unique = true)
    private String code;
}
