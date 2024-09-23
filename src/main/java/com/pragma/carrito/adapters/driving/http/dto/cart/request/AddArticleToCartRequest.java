package com.pragma.carrito.adapters.driving.http.dto.cart.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AddArticleToCartRequest {
    private Long articleId;
    private Integer quantity;

}
