package com.examplesoft.ecommercemonolite.util;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Locale;

@RestControllerAdvice
@RequiredArgsConstructor
public class RestExceptionHandler {
    private final MessageSource messageSource;

    @ExceptionHandler(BaseException.class)
    public Response<Object> handleBaseException(BaseException e, Locale locale) {
        MessageUtil messageUtil = e.getMessageUtil();
        String message = messageSource.getMessage(messageUtil.getMessage(), e.getArgs(), locale);
        return new Response<>(messageUtil.getCode(),message);
    }
}
