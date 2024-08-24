package com.examplesoft.ecommercemonolite.domain.auth.api;

public record LoginRequest(
        String username,
        String password
) {
}
