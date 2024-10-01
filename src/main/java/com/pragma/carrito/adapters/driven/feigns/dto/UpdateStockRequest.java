package com.pragma.carrito.adapters.driven.feigns.dto;

public class UpdateStockRequest {
    private Long idArticle;
    private int quantity;

    public UpdateStockRequest(Long idArticle, int quantity) {
        this.idArticle = idArticle;
        this.quantity = quantity;
    }

    public Long getIdArticle() {
        return idArticle;
    }

    public void setIdArticle(Long idArticle) {
        this.idArticle = idArticle;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
