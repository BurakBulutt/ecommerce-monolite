package com.examplesoft.ecommercemonolite.domain.product.api;

import com.examplesoft.ecommercemonolite.domain.product.service.ProductService;
import com.examplesoft.ecommercemonolite.util.BaseController;
import com.examplesoft.ecommercemonolite.util.PageResponse;
import com.examplesoft.ecommercemonolite.util.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("products")
@RequiredArgsConstructor
public class ProductController extends BaseController {
    private final ProductService service;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('admin','product:read')")
    public Response<PageResponse<ProductResponse>> getAll(Pageable pageable) {
        return response(ProductMapper.toPageResponse(service.getAll(pageable)));
    }

    @GetMapping("filter")
    @PreAuthorize("hasAnyAuthority('admin','product:read')")
    public Response<PageResponse<ProductResponse>> filter(@RequestParam(value = "categorySlug", required = false) String categorySlug,
                                                          Pageable pageable) {
        return response(ProductMapper.toPageResponse(service.filter(categorySlug, pageable)));
    }

    @GetMapping("search-filter")
    public Response<PageResponse<ProductResponse>> searchFilter(@RequestParam(value = "key", required = false) String keyword,
                                                                Pageable pageable) {
        return response(ProductMapper.toPageResponse(service.searchFilter(keyword, pageable)));
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyAuthority('admin')")
    public Response<ProductResponse> getById(@PathVariable String id) {
        return response(ProductMapper.toResponse(service.getById(id)));
    }

    @GetMapping("/slug/{slug}")
    public Response<ProductResponse> getBySlug(@PathVariable String slug) {
        return response(ProductMapper.toResponse(service.getBySlug(slug)));
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('admin')")
    public Response<ProductResponse> save(@RequestBody ProductRequest request) {
        return response(ProductMapper.toResponse(service.save(ProductMapper.toDto(request))));
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAnyAuthority('admin')")
    public Response<ProductResponse> update(@PathVariable String id, @RequestBody ProductRequest request) {
        return response(ProductMapper.toResponse(service.update(id, ProductMapper.toDto(request))));
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyAuthority('admin')")
    public Response<Void> delete(@PathVariable String id) {
        service.delete(id);
        return success();
    }
}
