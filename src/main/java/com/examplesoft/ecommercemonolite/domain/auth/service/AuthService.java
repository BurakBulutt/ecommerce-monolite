package com.examplesoft.ecommercemonolite.domain.auth.service;

import com.examplesoft.ecommercemonolite.domain.auth.api.LoginRequest;
import com.examplesoft.ecommercemonolite.domain.auth.api.RegisterRequest;
import com.examplesoft.ecommercemonolite.domain.auth.dto.Token;
import com.examplesoft.ecommercemonolite.domain.user.entity.UserType;

public interface AuthService {
    void register(RegisterRequest request);
    Token login(LoginRequest request, UserType userType);
}
