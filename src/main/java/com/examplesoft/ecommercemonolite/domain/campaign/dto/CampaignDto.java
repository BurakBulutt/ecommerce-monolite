package com.examplesoft.ecommercemonolite.domain.campaign.dto;

import com.examplesoft.ecommercemonolite.domain.campaign.entity.CampaignPriceEffect;
import com.examplesoft.ecommercemonolite.domain.campaign.entity.CampaignScope;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CampaignDto {
    private String id;
    private String name;
    private String description;
    private CampaignScope campaignScope;
    private CampaignPriceEffect priceEffect;
    private Date expirationDate;
    private Double discount;
    private Set<String> targets;
    private Integer priority;
}
