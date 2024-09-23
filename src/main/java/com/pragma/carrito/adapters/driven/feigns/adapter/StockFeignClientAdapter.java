package com.pragma.carrito.adapters.driven.feigns.adapter;


import com.pragma.carrito.adapters.driven.feigns.clients.StockFeignClient;
import com.pragma.carrito.adapters.driven.feigns.dto.FindQuantityByIdRequest;
import com.pragma.carrito.domain.spi.StockFeignClientPort;

import java.util.List;
import java.util.Optional;

public class StockFeignClientAdapter implements StockFeignClientPort {
    private final StockFeignClient stockFeignClient;

    public StockFeignClientAdapter(StockFeignClient stockFeignClient) {
        this.stockFeignClient = stockFeignClient;
    }

    @Override
    public int findQuantityById(Long idArticle) {
        return stockFeignClient.findQuantityById(idArticle);
    }
    public Optional<Long> findArticleIdById(Long idArticle) {
        return stockFeignClient.findIdArticleById(idArticle);
    }
public List<Long> findIdCategoriesByArticlesId(List<Long> idArticle) {
        return stockFeignClient.findIdCategoryByArticlesId(idArticle);
    }

}
