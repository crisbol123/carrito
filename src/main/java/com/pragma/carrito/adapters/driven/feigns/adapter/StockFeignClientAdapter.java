package com.pragma.carrito.adapters.driven.feigns.adapter;


import com.pragma.carrito.adapters.driven.feigns.clients.StockFeignClient;
import com.pragma.carrito.adapters.driven.feigns.dto.UpdateStockRequest;
import com.pragma.carrito.domain.spi.StockFeignClientPort;
import com.pragma.carrito.domain.util.PagedResponse;
import com.pragma.carrito.domain.util.StockInformationArticle;

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

    @Override
    public PagedResponse<StockInformationArticle> getAllArticles(List<Long> articleId, Boolean ascOrder, String mark, String category, int page, int size) {
        return stockFeignClient.getAllArticles(articleId, ascOrder, mark, category, page, size);
    }

    @Override
    public void updateStock(Long idArticle, int quantity) {
        UpdateStockRequest updateStockRequest = new UpdateStockRequest(idArticle, quantity);
        stockFeignClient.updateStock(updateStockRequest);
    }

}
