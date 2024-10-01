package com.pragma.carrito.adapters.driving.http.dto.cart.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetAllArticlesRequest {
    private boolean ascOrderByName;
    private int page;
    private int size;


}
