package com.examplesoft.ecommercemonolite.domain.category.entity;

import com.examplesoft.ecommercemonolite.entitylistener.service.EntityChangesListener;
import com.examplesoft.ecommercemonolite.util.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@EntityListeners(EntityChangesListener.class)
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = Category.TABLE,indexes = {@Index(name = "idx_category_slug",columnList = "slug")})
public class Category extends BaseEntity {
    public static final String TABLE = "category";

    private String name;
    private String description;
    @Column(unique = true,nullable = false)
    private String slug;
    private String parentId;
}
