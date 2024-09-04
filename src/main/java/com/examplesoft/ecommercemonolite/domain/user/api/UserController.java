package com.examplesoft.ecommercemonolite.domain.user.api;

import com.examplesoft.ecommercemonolite.domain.user.service.UserService;
import com.examplesoft.ecommercemonolite.util.BaseController;
import com.examplesoft.ecommercemonolite.util.PageResponse;
import com.examplesoft.ecommercemonolite.util.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController extends BaseController {
    private final UserService service;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('admin')")
    public Response<PageResponse<UserResponse>> getAll(Pageable pageable) {
        return response(UserMapper.toPageResponse(service.getAll(pageable)));
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyAuthority('admin')")
    public Response<UserResponse> getById(@PathVariable String id) {
        return response(UserMapper.toResponse(service.getById(id)));
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('admin')")
    public Response<UserResponse> save(@RequestBody UserRequest request) {
        return response(UserMapper.toResponse(service.save(UserMapper.toDto(request))));
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAnyAuthority('admin')")
    public Response<UserResponse> update(@PathVariable String id,@RequestBody UserRequest request) {
        return response(UserMapper.toResponse(service.update(id, UserMapper.toDto(request))));
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyAuthority('admin')")
    public Response<Void> delete(@PathVariable String id) {
        service.delete(id);
        return success();
    }
}
