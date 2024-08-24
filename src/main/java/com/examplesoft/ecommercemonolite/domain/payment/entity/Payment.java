package com.examplesoft.ecommercemonolite.domain.payment.entity;

import com.examplesoft.ecommercemonolite.entitylistener.service.EntityChangesListener;
import com.examplesoft.ecommercemonolite.util.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Entity
@EntityListeners(EntityChangesListener.class)
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = Payment.TABLE)
public class Payment extends BaseEntity {
    public static final String TABLE = "payment";

    private BigDecimal price;
    private String deliveryAddress;
    private String billingAddress;
    @Column(nullable = false,unique = true)
    private String userId;

}
