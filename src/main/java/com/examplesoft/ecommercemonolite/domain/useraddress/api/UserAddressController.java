package com.examplesoft.ecommercemonolite.domain.useraddress.api;

import com.examplesoft.ecommercemonolite.domain.useraddress.service.UserAddressService;
import com.examplesoft.ecommercemonolite.util.BaseController;
import com.examplesoft.ecommercemonolite.util.DataResponse;
import com.examplesoft.ecommercemonolite.util.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user-addresses")
@RequiredArgsConstructor
public class UserAddressController extends BaseController {
    private final UserAddressService service;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('admin')")
    public Response<DataResponse<UserAddressResponse>> getAll() {
        return response(UserAddressMapper.toDataResponse(service.getAll()));
    }

    @GetMapping("find-user-addresses/{userId}")
    @PreAuthorize("hasAnyAuthority('user:address:read','admin')")
    public Response<DataResponse<UserAddressResponse>> getUserAddresses(@PathVariable String userId) {
        return response(UserAddressMapper.toDataResponse(service.getUserAddresses(userId)));
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyAuthority('admin')")
    public Response<UserAddressResponse> getById(@PathVariable String id) {
        return response(UserAddressMapper.toResponse(service.getById(id)));
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('admin')")
    public Response<UserAddressResponse> save(@RequestBody UserAddressRequest request) {
        return response(UserAddressMapper.toResponse(service.save(UserAddressMapper.toDto(request))));
    }

    @PostMapping("user-create")
    @PreAuthorize("hasAnyAuthority('user:address:write','admin')")
    public Response<UserAddressResponse> save(@RequestBody UserAddressUserRequest request) {
        return response(UserAddressMapper.toResponse(service.save(UserAddressMapper.toDto(request))));
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAnyAuthority('admin')")
    public Response<UserAddressResponse> update(@PathVariable String id,@RequestBody UserAddressRequest request) {
        return response(UserAddressMapper.toResponse(service.update(id, UserAddressMapper.toDto(request))));
    }

    @PutMapping("user-update/{id}")
    @PreAuthorize("hasAnyAuthority('user:address:write','admin')")
    public Response<UserAddressResponse> update(@PathVariable String id,@RequestBody UserAddressUserRequest request) {
        return response(UserAddressMapper.toResponse(service.update(id, UserAddressMapper.toDto(request))));
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyAuthority('user:address:write','admin')")
    public Response<Void> delete(@PathVariable String id) {
        service.delete(id);
        return success();
    }
}
