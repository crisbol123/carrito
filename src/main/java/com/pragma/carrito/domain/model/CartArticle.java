package com.pragma.carrito.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CartArticle {
    private Long id;
   private Cart cart;
    private Long articleId;
    private Integer quantity;
    }

