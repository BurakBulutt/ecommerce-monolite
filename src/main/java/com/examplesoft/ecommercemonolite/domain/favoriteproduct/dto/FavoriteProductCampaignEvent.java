package com.examplesoft.ecommercemonolite.domain.favoriteproduct.dto;

public record FavoriteProductCampaignEvent(
        String url,
        String to,
        String name,
        String description,
        String image
) {
}
