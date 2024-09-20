package com.examplesoft.ecommercemonolite.domain.product.dto;

import com.examplesoft.ecommercemonolite.domain.campaign.dto.CampaignDto;
import com.examplesoft.ecommercemonolite.domain.campaign.entity.CampaignPriceEffect;
import com.examplesoft.ecommercemonolite.domain.category.dto.CategoryDto;
import com.examplesoft.ecommercemonolite.domain.product.entity.Product;
import com.examplesoft.ecommercemonolite.util.BaseException;
import com.examplesoft.ecommercemonolite.util.MessageUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductMapper {

    public static ProductDto toDto(Product entity, CategoryDto mainCategory, List<CampaignDto> campaigns) {
        List<ProductDiscount> productDiscounts = new ArrayList<>();
        BigDecimal priceAfterDiscount = getDiscountedPrice(entity.getPrice(), campaigns, productDiscounts);

        return ProductDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .originalPrice(entity.getPrice())
                .priceAfterDiscount(priceAfterDiscount)
                .productDiscounts(productDiscounts)
                .image(entity.getImage())
                .code(entity.getCode())
                .slug(entity.getSlug())
                .quantity(entity.getQuantity())
                .mainCategory(mainCategory)
                .build();
    }

    public static Product toEntity(Product entity, ProductDto dto) {
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getOriginalPrice());
        entity.setImage(dto.getImage());
        entity.setCode(dto.getCode());
        entity.setSlug(dto.getSlug());
        entity.setQuantity(dto.getQuantity());
        entity.setMainCategoryId(dto.getMainCategory().getId());

        return entity;
    }

    private static BigDecimal getDiscountedPrice(BigDecimal originalPrice, List<CampaignDto> campaigns, List<ProductDiscount> productDiscounts) {
        BigDecimal price = originalPrice;

        campaigns = campaigns.stream()
                .sorted(Comparator.comparing(CampaignDto::getPriority))
                .toList();

        for (CampaignDto campaign : campaigns) {
            switch (campaign.getPriceEffect()) {
                case PRICE -> {
                    BigDecimal priceWithoutDiscount = price;
                    price = price.subtract(BigDecimal.valueOf(campaign.getDiscount()));
                    productDiscounts.add(ProductDiscount.builder()
                            .name(campaign.getName())
                            .discount(campaign.getDiscount())
                            .discountPrice(BigDecimal.valueOf(campaign.getDiscount()))
                            .priceWithDiscount(price)
                            .priceEffect(campaign.getPriceEffect())
                            .priceWithoutDiscount(priceWithoutDiscount)
                            .build());
                }
                case PERCENTAGE -> {
                    BigDecimal priceWithoutDiscount = price;
                    BigDecimal discountRate = price.multiply(BigDecimal.valueOf(campaign.getDiscount() / 100));
                    price = price.subtract(discountRate);
                    productDiscounts.add(ProductDiscount.builder()
                            .name(campaign.getName())
                            .discount(campaign.getDiscount())
                            .discountPrice(discountRate)
                            .priceWithDiscount(price)
                            .priceEffect(campaign.getPriceEffect())
                            .priceWithoutDiscount(priceWithoutDiscount)
                            .build());
                }
                default -> throw new BaseException(MessageUtil.FAIL);
            }
        }

        return price;
    }
}
