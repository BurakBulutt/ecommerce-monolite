package com.examplesoft.ecommercemonolite.domain.basket.entity;

import com.examplesoft.ecommercemonolite.entitylistener.service.EntityChangesListener;
import com.examplesoft.ecommercemonolite.util.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@EntityListeners(EntityChangesListener.class)
@Table
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Basket extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String userId;
    private BigDecimal totalAmount;
}
