package com.examplesoft.ecommercemonolite.domain.favoriteproduct.api;

import com.examplesoft.ecommercemonolite.domain.favoriteproduct.service.FavoriteProductService;
import com.examplesoft.ecommercemonolite.util.BaseController;
import com.examplesoft.ecommercemonolite.util.PageResponse;
import com.examplesoft.ecommercemonolite.util.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("favorite-products")
@RequiredArgsConstructor
public class FavoriteProductController extends BaseController {
    private final FavoriteProductService service;

    @GetMapping("get-user-favorites")
    public Response<PageResponse<UserFavoriteProductResponse>> getUserFavorites(Pageable pageable) {
        return response(FavoriteProductMapper.toPageResponse(service.getUserFavoriteProducts(pageable)));
    }

    @GetMapping("check-favorite/{id}")
    public Response<UserFavoriteProductResponse> checkFavorite(@PathVariable String id){
        return response(FavoriteProductMapper.toResponse(service.isFavoriteProduct(id)));
    }

    @PostMapping
    public Response<UserFavoriteProductResponse> save(@RequestBody FavoriteProductRequest request) {
        return response(FavoriteProductMapper.toResponse(service.save(request.productId())));
    }

    @DeleteMapping("{id}")
    public Response<Void> delete(@PathVariable String id) {
        service.delete(id);
        return success();
    }
}
