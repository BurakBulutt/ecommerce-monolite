package com.examplesoft.ecommercemonolite.domain.permisson.service;

import com.examplesoft.ecommercemonolite.domain.permisson.dto.PermissionDto;
import com.examplesoft.ecommercemonolite.domain.permisson.entity.PermissionType;
import com.examplesoft.ecommercemonolite.domain.permisson.repo.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DefaultPermissionCreator {
    private final PermissionServiceImpl permissionService;

    private static final String ADMIN = "admin";
    private static final String USER_READ = "user:read";
    private static final String USER_WRITE = "user:write";
    private static final String BASKET_READ = "basket:read";
    private static final String BASKET_WRITE = "basket:write";
    private static final String PRODUCT_READ = "product:read";
    private static final String PRODUCT_WRITE = "product:write";
    private static final String CATEGORY_READ = "category:read";
    private static final String CATEGORY_WRITE = "category:write";
    private static final String ORDER_READ = "order:read";
    private static final String ORDER_WRITE = "order:write";
    private static final String PAYMENT_READ = "payment:read";
    private static final String PAYMENT_WRITE = "payment:write";

    @EventListener(value = ApplicationReadyEvent.class)
    @Order(1)
    public void createPermission(){
        List<PermissionDto> permissionDtoList = new ArrayList<>();
        permissionDtoList.add(PermissionDto.builder()
                        .permissionType(PermissionType.ADMIN)
                        .name(ADMIN)
                        .description("Admin Permission")
                .build());
        permissionDtoList.add(PermissionDto.builder()
                .permissionType(PermissionType.USER)
                .name(USER_READ)
                .description("User Read Yetkisi")
                .build());
        permissionDtoList.add(PermissionDto.builder()
                .permissionType(PermissionType.USER)
                .name(USER_WRITE)
                .description("User Write Yetkisi")
                .build());
        permissionDtoList.add(PermissionDto.builder()
                .permissionType(PermissionType.USER)
                .name(BASKET_READ)
                .description("Basket Read Yetkisi")
                .build());
        permissionDtoList.add(PermissionDto.builder()
                .permissionType(PermissionType.USER)
                .name(BASKET_WRITE)
                .description("Basket Write Yetkisi")
                .build());
        permissionDtoList.add(PermissionDto.builder()
                .permissionType(PermissionType.USER)
                .name(PRODUCT_READ)
                .description("Product Read Yetkisi")
                .build());
        permissionDtoList.add(PermissionDto.builder()
                .permissionType(PermissionType.USER)
                .name(PRODUCT_WRITE)
                .description("Product Write Yetkisi")
                .build());
        permissionDtoList.add(PermissionDto.builder()
                .permissionType(PermissionType.USER)
                .name(CATEGORY_READ)
                .description("Category Read Yetkisi")
                .build());
        permissionDtoList.add(PermissionDto.builder()
                .permissionType(PermissionType.USER)
                .name(CATEGORY_WRITE)
                .description("Category Write Yetkisi")
                .build());
        permissionDtoList.add(PermissionDto.builder()
                .permissionType(PermissionType.USER)
                .name(ORDER_READ)
                .description("Order Read Yetkisi")
                .build());
        permissionDtoList.add(PermissionDto.builder()
                .permissionType(PermissionType.USER)
                .name(ORDER_WRITE)
                .description("Order Write Yetkisi")
                .build());
        permissionDtoList.add(PermissionDto.builder()
                .permissionType(PermissionType.USER)
                .name(PAYMENT_READ)
                .description("Payment Read Yetkisi")
                .build());
        permissionDtoList.add(PermissionDto.builder()
                .permissionType(PermissionType.USER)
                .name(PAYMENT_WRITE)
                .description("Payment Write Yetkisi")
                .build());

        permissionService.saveAll(permissionDtoList);
    }
}

