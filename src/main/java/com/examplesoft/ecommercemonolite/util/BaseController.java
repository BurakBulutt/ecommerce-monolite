package com.examplesoft.ecommercemonolite.util;

import org.springframework.data.domain.Page;

import java.util.List;

public class BaseController {

    public static <T> Response<T> response(T data) {
        return new Response<>(data);
    }

    public static <T> Response<DataResponse<T>> response(List<T> items) {
        return new Response<>(new DataResponse<>(items));
    }

    public static <T> Response<PageResponse<T>> response(Page<T> items) {
        return new Response<>(new PageResponse<>(items));
    }

    public static <T> Response<T> success() {
        return new Response<>(null);
    }
}
