package com.examplesoft.ecommercemonolite.domain.useraddress.api;

public record UserAddressRequest(
        String title,
        String address,
        String userId
) {
}
