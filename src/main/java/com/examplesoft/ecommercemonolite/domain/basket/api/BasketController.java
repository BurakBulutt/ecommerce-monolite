package com.examplesoft.ecommercemonolite.domain.basket.api;

import com.examplesoft.ecommercemonolite.domain.basket.service.BasketService;
import com.examplesoft.ecommercemonolite.util.BaseController;
import com.examplesoft.ecommercemonolite.util.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("baskets")
@RequiredArgsConstructor
public class BasketController extends BaseController {
    private final BasketService service;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('basket:read','admin')")
    public Response<BasketResponse> getBasket() {
        return response(BasketMapper.toBasketResponse(service.getBasket()));
    }

    @PostMapping("add-product")
    @PreAuthorize("hasAnyAuthority('basket:write','admin')")
    public Response<BasketResponse> addProductToBasket(@RequestBody AddProductRequest request) {
        return response(BasketMapper.toBasketResponse(service.addProductToBasket(request)));
    }

    @PostMapping("update-quantity")
    @PreAuthorize("hasAnyAuthority('basket:write','admin')")
    public Response<BasketResponse> updateQuantity(@RequestBody UpdateQuantityRequest request) {
        return response(BasketMapper.toBasketResponse(service.changeQuantity(request.basketProductId(),request.quantity())));
    }

    @DeleteMapping("delete-basketProduct/{id}")
    @PreAuthorize("hasAnyAuthority('basket:write','admin')")
    public Response<BasketResponse> deleteBasketProduct(@PathVariable String id) {
        return response(BasketMapper.toBasketResponse(service.deleteBasketProduct(id)));
    }

    @DeleteMapping
    @PreAuthorize("hasAnyAuthority('basket:write','admin')")
    public Response<Void> clearBasket() {
        service.clearBasket();
        return success();
    }

}
