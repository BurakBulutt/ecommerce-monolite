package com.examplesoft.ecommercemonolite.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Locale;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Response<T> {
    private T data;
    private int statusCode;
    private String description;

    public Response(T data) {
        this.data = data;
        statusCode = MessageUtil.SUCCESS.getCode();
        description = MessageSourceProvider.getMessageSource().getMessage(MessageUtil.SUCCESS.getMessage(),null, Locale.getDefault());
    }

    public Response(int statusCode,String description) {
        this.data = null;
        this.statusCode = statusCode;
        this.description = description;
    }

}
