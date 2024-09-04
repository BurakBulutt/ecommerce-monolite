package com.examplesoft.ecommercemonolite.domain.user.entity;


import com.examplesoft.ecommercemonolite.entitylistener.service.EntityChangesListener;
import com.examplesoft.ecommercemonolite.util.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EqualsAndHashCode(callSuper = true)
@EntityListeners(EntityChangesListener.class)
@Entity
@Table(name = User.TABLE)
@Data
public class User extends BaseEntity {
    public static final String TABLE = "users";

    private String name;
    private String surname;
    @Column(unique = true, nullable = false,updatable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(unique = true)
    private String email;
    private Boolean isActive;
    private Boolean isVerified;
    @Enumerated(EnumType.STRING)
    private UserType userType;
}
