package com.examplesoft.ecommercemonolite.domain.campaign.dto;

import java.util.Set;

public record CampaignNotifyEvent(
        Set<String> productIds
) {
}
