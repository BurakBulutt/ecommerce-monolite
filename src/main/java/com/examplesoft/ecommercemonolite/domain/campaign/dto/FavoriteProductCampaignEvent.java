package com.examplesoft.ecommercemonolite.domain.campaign.dto;

public record FavoriteProductCampaignEvent(
        String url,
        String to,
        String name,
        String description,
        String image
) {
}
