package com.examplesoft.ecommercemonolite.domain.product.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(name = "unique_product_category",columnNames = {"productId","categoryId"})})
@Data
@EqualsAndHashCode(of = "id")
public class ProductCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String productId;
    private String categoryId;
}
