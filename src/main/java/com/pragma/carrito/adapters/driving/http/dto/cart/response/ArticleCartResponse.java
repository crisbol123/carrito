package com.pragma.carrito.adapters.driving.http.dto.cart.response;

import com.pragma.carrito.domain.util.StockInformationArticle;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ArticleCartResponse {
    private Long id;
    private String name;
    private int quantity;
    private int quantityCart;
    private double price;
    private String mark;
    private List<String> categories;
    private double subTotal;
    private String nextSupplyDate;


    public ArticleCartResponse(StockInformationArticle stockInformationArticle, int quantityCart) {
        this.id = stockInformationArticle.getId();
        this.name = stockInformationArticle.getName();
        this.quantity = stockInformationArticle.getQuantity();
        this.price = stockInformationArticle.getPrice();
        this.mark = stockInformationArticle.getMark();
        this.categories = stockInformationArticle.getCategories();
        this.quantityCart = quantityCart;
        this.subTotal = this.price * this.quantityCart;

    }

}
