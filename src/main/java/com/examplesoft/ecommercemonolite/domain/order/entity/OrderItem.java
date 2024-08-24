package com.examplesoft.ecommercemonolite.domain.order.entity;

import com.examplesoft.ecommercemonolite.entitylistener.service.EntityChangesListener;
import com.examplesoft.ecommercemonolite.util.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@EntityListeners(EntityChangesListener.class)
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = OrderItem.TABLE)
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem extends BaseEntity {
    public static final String TABLE = "order_item";

    @Column(nullable = false)
    private String orderId;
    @Column(nullable = false)
    private String productId;
    @Column(nullable = false)
    private Integer quantity;
    @Column(nullable = false)
    private BigDecimal totalAmount;
}
