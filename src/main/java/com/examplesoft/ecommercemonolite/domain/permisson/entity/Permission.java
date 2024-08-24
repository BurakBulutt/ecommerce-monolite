package com.examplesoft.ecommercemonolite.domain.permisson.entity;

import com.examplesoft.ecommercemonolite.entitylistener.service.EntityChangesListener;
import com.examplesoft.ecommercemonolite.util.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@EntityListeners(EntityChangesListener.class)
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = Permission.TABLE,indexes = {@Index(name = "idx_permission_name",columnList = "name")})
public class Permission extends BaseEntity {
    public static final String TABLE = "permission";

    @Column(unique = true)
    private String name;
    private String description;
    @Enumerated(EnumType.STRING)
    private PermissionType permissionType;
}
