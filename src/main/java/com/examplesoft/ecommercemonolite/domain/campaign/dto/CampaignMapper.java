package com.examplesoft.ecommercemonolite.domain.campaign.dto;

import com.examplesoft.ecommercemonolite.domain.campaign.entity.Campaign;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CampaignMapper {

    public static CampaignDto toDto(Campaign entity) {
        return CampaignDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .campaignScope(entity.getCampaignScope())
                .priceEffect(entity.getPriceEffect())
                .discount(entity.getDiscount())
                .targets(entity.getTargets())
                .priority(entity.getPriority())
                .isActive(entity.getIsActive())
                .expirationDate(entity.getExpirationDate())
                .build();
    }

    public static Campaign toEntity(Campaign entity,CampaignDto dto) {
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setCampaignScope(dto.getCampaignScope());
        entity.setPriceEffect(dto.getPriceEffect());
        entity.setExpirationDate(dto.getExpirationDate());
        entity.setDiscount(dto.getDiscount());
        entity.setTargets(dto.getTargets());
        entity.setPriority(dto.getPriority());
        entity.setIsActive(dto.getIsActive());

        return entity;
    }
}
