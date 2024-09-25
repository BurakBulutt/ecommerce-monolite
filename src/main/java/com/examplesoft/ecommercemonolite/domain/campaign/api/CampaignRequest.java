package com.examplesoft.ecommercemonolite.domain.campaign.api;

import com.examplesoft.ecommercemonolite.domain.campaign.entity.CampaignPriceEffect;
import com.examplesoft.ecommercemonolite.domain.campaign.entity.CampaignScope;

import java.util.Date;
import java.util.Set;

public record CampaignRequest(
        String name,
        String description,
        CampaignScope campaignScope,
        CampaignPriceEffect priceEffect,
        Date expirationDate,
        Set<String> targets,
        Double discount,
        Integer priority,
        Boolean isActive
) {
}
