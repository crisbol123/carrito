package com.pragma.carrito.adapters.driven.feigns.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter

@NoArgsConstructor
public class FindQuantityByIdRequest {
    private Long idArticle;

    public FindQuantityByIdRequest(Long idArticle) {
        this.idArticle = idArticle;
    }
}
