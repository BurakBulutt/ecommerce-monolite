package com.examplesoft.ecommercemonolite.domain.campaign.api;

import com.examplesoft.ecommercemonolite.domain.campaign.dto.CampaignDto;
import com.examplesoft.ecommercemonolite.util.PageUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CampaignMapper {

    public static CampaignDto toDto(CampaignRequest request){
        return CampaignDto.builder()
                .name(request.name())
                .description(request.description())
                .priceEffect(request.priceEffect())
                .campaignScope(request.campaignScope())
                .discount(request.discount())
                .expirationDate(request.expirationDate())
                .targets(request.targets())
                .priority(request.priority())
                .isActive(request.isActive())
                .build();
    }

    public static CampaignResponse toResponse(CampaignDto dto){
        return CampaignResponse.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .priceEffect(dto.getPriceEffect())
                .campaignScope(dto.getCampaignScope())
                .discount(dto.getDiscount())
                .expirationDate(dto.getExpirationDate())
                .targets(dto.getTargets())
                .priority(dto.getPriority())
                .isActive(dto.getIsActive())
                .build();
    }

    public static Page<CampaignResponse> toPageResponse(Page<CampaignDto> dtoPage){
        return PageUtil.toPage(dtoPage,CampaignMapper::toResponse);
    }
}
