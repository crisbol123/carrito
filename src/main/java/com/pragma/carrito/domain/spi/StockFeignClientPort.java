package com.pragma.carrito.domain.spi;


import java.util.List;
import java.util.Optional;

public interface StockFeignClientPort {
    int findQuantityById(Long idArticle);
    Optional<Long> findArticleIdById(Long idArticle);
    List<Long> findIdCategoriesByArticlesId(List<Long> idArticle);

}
