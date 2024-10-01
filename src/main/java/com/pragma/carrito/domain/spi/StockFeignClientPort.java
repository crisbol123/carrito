package com.pragma.carrito.domain.spi;


import com.pragma.carrito.domain.util.PagedResponse;
import com.pragma.carrito.domain.util.StockInformationArticle;

import java.util.List;
import java.util.Optional;

public interface StockFeignClientPort {
    int findQuantityById(Long idArticle);
    Optional<Long> findArticleIdById(Long idArticle);
    List<Long> findIdCategoriesByArticlesId(List<Long> idArticle);

    PagedResponse<StockInformationArticle> getAllArticles(List<Long> articleId, Boolean  ascOrder, String mark, String category, int page, int size);

    void updateStock(Long idArticle, int quantity);
}
