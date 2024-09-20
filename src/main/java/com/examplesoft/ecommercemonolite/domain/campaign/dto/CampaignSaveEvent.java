package com.examplesoft.ecommercemonolite.domain.campaign.dto;

import java.util.Set;

public record CampaignSaveEvent(
        Set<String> productIds
) {
}
