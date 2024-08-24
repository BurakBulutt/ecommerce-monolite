package com.examplesoft.ecommercemonolite.util;

import lombok.Getter;

@Getter
public enum MessageUtil {
    SUCCESS(200,"general.success"),
    FAIL(500,"general.fail"),
    UNAUTHORIZED(403,"general.unauthorized"),
    ENTITY_NOT_FOUND(1000,"general.entityNotFound"),
    ENTITY_ALREADY_EXISTS(1001,"general.entityAlreadyExists"),
    USERNAME_OR_PASSWORD_WRONG(1002,"general.usernameOrPasswordWrong"),
    BASKET_NOT_FOUND(1003,"general.basketNotFound"),
    BASKET_EMPTY(1004,"general.basketEmpty"),
    NO_STOCK_PRODUCT(1005,"general.noStockProduct"),;

    private final int code;
    private final String message;

    MessageUtil(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
