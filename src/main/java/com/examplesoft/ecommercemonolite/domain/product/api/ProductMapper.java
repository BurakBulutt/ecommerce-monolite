package com.examplesoft.ecommercemonolite.domain.product.api;

import com.examplesoft.ecommercemonolite.domain.category.dto.CategoryDto;
import com.examplesoft.ecommercemonolite.domain.product.dto.ProductDto;
import com.examplesoft.ecommercemonolite.util.PageUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductMapper {

    public static ProductDto toDto(ProductRequest request) {
        return ProductDto.builder()
                .name(request.name())
                .description(request.description())
                .price(request.price())
                .code(request.code())
                .slug(request.slug())
                .image(request.image())
                .quantity(request.quantity())
                .mainCategory(CategoryDto.builder().id(request.mainCategoryId()).build())
                .build();
    }

    public static ProductResponse toResponse(ProductDto dto) {
        return ProductResponse.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .code(dto.getCode())
                .slug(dto.getSlug())
                .image(dto.getImage())
                .quantity(dto.getQuantity())
                .mainCategory(dto.getMainCategory())
                .build();
    }

    public static Page<ProductResponse> toPageResponse(Page<ProductDto> dtos) {
        return PageUtil.toPage(dtos,ProductMapper::toResponse);
    }
}
