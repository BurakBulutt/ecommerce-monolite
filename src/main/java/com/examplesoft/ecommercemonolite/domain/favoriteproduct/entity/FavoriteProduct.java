package com.examplesoft.ecommercemonolite.domain.favoriteproduct.entity;

import com.examplesoft.ecommercemonolite.entitylistener.service.EntityChangesListener;
import com.examplesoft.ecommercemonolite.util.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = FavoriteProduct.TABLE,uniqueConstraints = {
        @UniqueConstraint(columnNames = {"product_id","user_id"},name = "favorite_product_uniq_key")
})
@EntityListeners(EntityChangesListener.class)
@Data
@EqualsAndHashCode(callSuper = true)
public class FavoriteProduct extends BaseEntity {
    public static final String TABLE = "favorite_product";

    @Column(nullable = false)
    private String productId;
    @Column(nullable = false)
    private String userId;
}
