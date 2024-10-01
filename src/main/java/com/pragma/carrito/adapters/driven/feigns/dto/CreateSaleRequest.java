package com.pragma.carrito.adapters.driven.feigns.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateSaleRequest
{
    Long articleId;
    int quantity;
    Long userId;

}
