package com.examplesoft.ecommercemonolite.domain.useraddress.entity;

import com.examplesoft.ecommercemonolite.util.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = UserAddress.TABLE)
@Data
@EqualsAndHashCode(callSuper = true)
public class UserAddress extends BaseEntity {
    public static final String TABLE = "user_address";

    @Column(nullable = false)
    private String title;
    @Column(nullable = false,columnDefinition = "text")
    private String address;
    @Column(nullable = false)
    private String userId;
}
