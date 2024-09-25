package com.examplesoft.ecommercemonolite.domain.payment.api;

import com.examplesoft.ecommercemonolite.domain.payment.service.PaymentServiceImpl;
import com.examplesoft.ecommercemonolite.util.BaseController;
import com.examplesoft.ecommercemonolite.util.PageResponse;
import com.examplesoft.ecommercemonolite.util.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("payment")
@RequiredArgsConstructor
public class PaymentController extends BaseController {
    private final PaymentServiceImpl service;

    @PostMapping("create-payment")
    @PreAuthorize("hasAnyAuthority('payment:write','admin')")
    public Response<Void> createPayment(@RequestBody PaymentRequest request){
        service.createPayment(PaymentMapper.toDto(request));
        return success();
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('admin')")
    public Response<PageResponse<PaymentResponse>> getAll(Pageable pageable){
        return response(PaymentMapper.toPageResponse(service.getAll(pageable)));
    }

}
