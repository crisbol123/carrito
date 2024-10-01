package com.pragma.carrito.domain.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StockInformationArticle {
    private Long id;
    private String name;
    private int quantity;
    private double price;
    private String mark;
    private List<String> categories;
}
