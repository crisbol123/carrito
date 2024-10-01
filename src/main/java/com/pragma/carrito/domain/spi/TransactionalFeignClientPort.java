package com.pragma.carrito.domain.spi;

public interface TransactionalFeignClientPort {
    String findNextStockDateById(Long idArticle);

    void  addSale(Long idArticle, int quantity, Long userId);
}
