package com.examplesoft.ecommercemonolite.domain.auth.api;

public record RegisterRequest(
        String name,
        String surname,
        String username,
        String password
) {
}
