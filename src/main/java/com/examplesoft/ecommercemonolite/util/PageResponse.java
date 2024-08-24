package com.examplesoft.ecommercemonolite.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PageResponse<T> {
    private Page<T> items = Page.empty();
}
