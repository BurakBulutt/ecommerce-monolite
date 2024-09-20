package com.examplesoft.ecommercemonolite.domain.campaign.entity;

import com.examplesoft.ecommercemonolite.entitylistener.service.EntityChangesListener;
import com.examplesoft.ecommercemonolite.util.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = Campaign.TABLE)
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@EntityListeners(EntityChangesListener.class)
public class Campaign extends BaseEntity {
    public static final String TABLE = "campaign";

    private String name;
    private String description;
    @Enumerated(EnumType.STRING)
    private CampaignPriceEffect priceEffect;
    @Enumerated(EnumType.STRING)
    private CampaignScope campaignScope;
    private Date expirationDate;
    private Double discount;
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> targets;
    private Integer priority;
}
