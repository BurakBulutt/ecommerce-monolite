package com.examplesoft.ecommercemonolite.domain.product.dto;

import com.examplesoft.ecommercemonolite.domain.campaign.entity.CampaignPriceEffect;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDiscount {
    private String name;
    private CampaignPriceEffect priceEffect;
    private Double discount;
    private BigDecimal discountPrice;
    private BigDecimal priceWithoutDiscount;
    private BigDecimal priceWithDiscount;
}
