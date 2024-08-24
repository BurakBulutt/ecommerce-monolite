package com.examplesoft.ecommercemonolite.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class DataResponse<T> {
    private List<T> items = List.of();
}
