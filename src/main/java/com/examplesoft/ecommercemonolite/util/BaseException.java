package com.examplesoft.ecommercemonolite.util;

import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {
    private final MessageUtil messageUtil;
    private final Object[] args;

    public BaseException(MessageUtil messageUtil, Object... args) {
        this.messageUtil = messageUtil;
        this.args = args;
    }
}
