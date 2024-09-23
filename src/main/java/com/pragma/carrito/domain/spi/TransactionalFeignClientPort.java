package com.pragma.carrito.domain.spi;

public interface TransactionalFeignClientPort {
    String findNextStockDateById(Long idArticle);
}
