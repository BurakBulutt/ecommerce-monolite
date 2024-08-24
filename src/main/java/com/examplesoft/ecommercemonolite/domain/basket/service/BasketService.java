package com.examplesoft.ecommercemonolite.domain.basket.service;

import com.examplesoft.ecommercemonolite.domain.basket.api.AddProductRequest;
import com.examplesoft.ecommercemonolite.domain.basket.dto.BasketDto;

public interface BasketService {
    BasketDto getBasket();
    BasketDto addProductToBasket(AddProductRequest request);
    BasketDto changeQuantity(String basketProductId, Integer quantity);
    void clearBasket();
}
