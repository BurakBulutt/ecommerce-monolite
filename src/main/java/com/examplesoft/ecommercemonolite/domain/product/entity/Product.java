package com.examplesoft.ecommercemonolite.domain.product.entity;

import com.examplesoft.ecommercemonolite.entitylistener.service.EntityChangesListener;
import com.examplesoft.ecommercemonolite.util.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Entity
@EntityListeners(EntityChangesListener.class)
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = Product.TABLE,indexes = {@Index(name = "idx_product_slug",columnList = "slug")})
public class Product extends BaseEntity {
    public static final String TABLE = "product";

    @Column(unique = true, nullable = false)
    private String code;
    @Column(nullable = false)
    private String name;
    private String description;
    private BigDecimal price;
    private Integer quantity;
    @Column(unique = true, nullable = false)
    private String slug;
    private String image;
    private String mainCategoryId;
}
