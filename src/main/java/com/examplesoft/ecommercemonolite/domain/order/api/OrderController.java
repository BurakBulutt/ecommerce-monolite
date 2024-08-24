package com.examplesoft.ecommercemonolite.domain.order.api;

import com.examplesoft.ecommercemonolite.domain.order.entity.OrderStatus;
import com.examplesoft.ecommercemonolite.domain.order.service.OrderService;
import com.examplesoft.ecommercemonolite.util.BaseController;
import com.examplesoft.ecommercemonolite.util.PageResponse;
import com.examplesoft.ecommercemonolite.util.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("orders")
@RequiredArgsConstructor
public class OrderController extends BaseController {
    private final OrderService service;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('order:read','admin')")
    public Response<PageResponse<OrderResponse>> getAll(Pageable pageable) {
        return response(OrderMapper.toPageResponse(service.getAll(pageable)));
    }

    @GetMapping("filter")
    @PreAuthorize("hasAnyAuthority('order:read','admin')")
    public Response<PageResponse<OrderResponse>> filter(Pageable pageable) {
        return response(OrderMapper.toPageResponse(service.filter(pageable)));
    }

    @GetMapping("{code}")
    @PreAuthorize("hasAnyAuthority('order:read','admin')")
    public Response<OrderResponse> getByCode(@PathVariable String code) {
        return response(OrderMapper.toResponse(service.getOrderByCode(code)));
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAnyAuthority('order:write','admin')")
    public Response<OrderResponse> updateOrderStatus(@PathVariable String id,@RequestPart OrderStatus status) {
        return response(OrderMapper.toResponse(service.updateOrderStatus(id, status)));
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyAuthority('order:write','admin')")
    public Response<OrderResponse> deleteOrder(@PathVariable String id) {
        service.deleteOrder(id);
        return success();
    }
}
