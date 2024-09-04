package com.examplesoft.ecommercemonolite.domain.auth.api;

import com.examplesoft.ecommercemonolite.domain.auth.service.AuthService;
import com.examplesoft.ecommercemonolite.domain.user.entity.UserType;
import com.examplesoft.ecommercemonolite.util.BaseController;
import com.examplesoft.ecommercemonolite.util.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController extends BaseController {
    private final AuthService service;

    @PostMapping("login-user")
    public Response<TokenResponse> loginUser(@RequestBody LoginRequest request) {
        return response(new TokenResponse(service.login(request, UserType.USER).getToken()));
    }

    @PostMapping("login-admin")
    public Response<TokenResponse> loginAdmin(@RequestBody LoginRequest request) {
        return response(new TokenResponse(service.login(request, UserType.ADMIN).getToken()));
    }

    @PostMapping("register")
    public Response<Void> registerUser(@RequestBody RegisterRequest request) {
        service.register(request);
        return success();
    }
}
