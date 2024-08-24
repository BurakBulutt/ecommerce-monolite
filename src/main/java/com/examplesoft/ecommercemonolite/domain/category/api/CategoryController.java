package com.examplesoft.ecommercemonolite.domain.category.api;

import com.examplesoft.ecommercemonolite.domain.category.service.CategoryService;
import com.examplesoft.ecommercemonolite.util.BaseController;
import com.examplesoft.ecommercemonolite.util.DataResponse;
import com.examplesoft.ecommercemonolite.util.PageResponse;
import com.examplesoft.ecommercemonolite.util.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("categories")
@RequiredArgsConstructor
public class CategoryController extends BaseController {
    private final CategoryService service;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('category:read','admin')")
    public Response<PageResponse<CategoryResponse>> getAll(Pageable pageable) {
        return response(CategoryMapper.toPageResponse(service.getAll(pageable)));
    }

    @GetMapping("filter")
    @PreAuthorize("hasAnyAuthority('category:read','admin')")
    public Response<PageResponse<CategoryResponse>> filter(Pageable pageable) {
        return response(CategoryMapper.toPageResponse(service.filter(pageable)));
    }

    @GetMapping("tree")
    @PreAuthorize("hasAnyAuthority('category:read','admin')")
    public Response<DataResponse<CategoryTreeResponse>> getTree(){
        return response(CategoryMapper.toDataTreeResponse(service.getMainCategoriesTree()));
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyAuthority('category:read','admin')")
    public Response<CategoryResponse> getById(@PathVariable String id) {
        return response(CategoryMapper.toResponse(service.getById(id)));
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('category:write','admin')")
    public Response<CategoryResponse> save(@RequestBody CategoryRequest request) {
        return response(CategoryMapper.toResponse(service.save(CategoryMapper.toDto(request))));
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAnyAuthority('category:write','admin')")
    public Response<CategoryResponse> update(@PathVariable String id,@RequestBody CategoryRequest request) {
        return response(CategoryMapper.toResponse(service.update(id, CategoryMapper.toDto(request))));
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyAuthority('category:write','admin')")
    public Response<Void> delete(@PathVariable String id) {
        service.delete(id);
        return success();
    }
}
