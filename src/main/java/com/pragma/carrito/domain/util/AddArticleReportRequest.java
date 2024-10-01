package com.pragma.carrito.domain.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class AddArticleReportRequest {
    private String name;
    private int quantity;
    private double unitPrice;
    private double subTotal;

    public AddArticleReportRequest(String name, int quantity, double unitPrice) {
        this.name = name;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.subTotal = quantity * unitPrice;
    }
}
