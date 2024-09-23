package com.pragma.carrito.adapters.driven.feigns.adapter;

import com.pragma.carrito.adapters.driven.feigns.clients.TransactionalFeignClient;
import com.pragma.carrito.domain.spi.TransactionalFeignClientPort;


public class TransactionalFeignClientAdapter implements TransactionalFeignClientPort {
    private final TransactionalFeignClient transactionalFeignClient;

    public TransactionalFeignClientAdapter(TransactionalFeignClient transactionalFeignClient) {
        this.transactionalFeignClient = transactionalFeignClient;
    }
    @Override
    public String findNextStockDateById(Long idArticle) {
        return transactionalFeignClient.findNextStockDateById(idArticle);
    }


}
