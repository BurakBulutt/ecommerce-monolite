package com.examplesoft.ecommercemonolite.domain.userpermission.entity;

import com.examplesoft.ecommercemonolite.entitylistener.service.EntityChangesListener;
import com.examplesoft.ecommercemonolite.util.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@EntityListeners(EntityChangesListener.class)
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = UserPermission.TABLE,uniqueConstraints = @UniqueConstraint(columnNames = {"user_id","permission_id"}))
@ToString
public class UserPermission extends BaseEntity {
    public static final String TABLE = "user_permission";

    private static final String COL_USER_ID = "user_id";
    private static final String COL_PERMISSION_ID = "permission_id";

    @Column(name = COL_USER_ID, nullable = false)
    private String userId;
    @Column(name = COL_PERMISSION_ID, nullable = false)
    private String permissionId;
}
