package com.pragma.carrito.adapters.driven.feigns.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor

@Setter
public class QuantityResponse {
    private int quantity;
    public int getQuantity() {
        return quantity;
    }
}
