package com.examplesoft.ecommercemonolite.domain.product.dto;

import com.examplesoft.ecommercemonolite.domain.category.dto.CategoryDto;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class ProductDto {
    private String id;
    private String code;
    private String name;
    private String description;
    private BigDecimal originalPrice;
    private BigDecimal priceAfterDiscount;
    private Integer quantity;
    private String slug;
    private String image;
    private CategoryDto mainCategory;
    private List<ProductDiscount> productDiscounts;

}
