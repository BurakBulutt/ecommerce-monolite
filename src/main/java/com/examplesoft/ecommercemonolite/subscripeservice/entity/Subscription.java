package com.examplesoft.ecommercemonolite.subscripeservice.entity;

import com.examplesoft.ecommercemonolite.entitylistener.service.EntityChangesListener;
import com.examplesoft.ecommercemonolite.util.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@EntityListeners(EntityChangesListener.class)
@Table(name = Subscription.TABLE)
public class Subscription extends BaseEntity {
    public static final String TABLE = "subscription";

    private String endpoint;
    @ElementCollection(fetch = FetchType.EAGER)
    private Map<String, String> keys;
    private String userId;
}

