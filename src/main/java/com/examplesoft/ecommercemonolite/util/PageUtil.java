package com.examplesoft.ecommercemonolite.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PageUtil {

    public static <T,R> Page<R> toPage(Page<T> page, Function<? super T, ? extends R> mapper) {
        List<R> list = page.stream().map(mapper).collect(Collectors.toList());
        return new PageImplUtil<>(list, page.getPageable(), page.getTotalElements());
    }
}
