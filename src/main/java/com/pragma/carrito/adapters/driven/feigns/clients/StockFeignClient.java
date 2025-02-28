package com.pragma.carrito.adapters.driven.feigns.clients;


import com.pragma.carrito.adapters.driven.feigns.FeignClientConfig;
import com.pragma.carrito.adapters.driven.feigns.dto.FindQuantityByIdRequest;
import com.pragma.carrito.adapters.driven.feigns.dto.QuantityResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@FeignClient(name = "stock-service", url = "${stock.service.url}", configuration = FeignClientConfig.class)
public interface StockFeignClient {
    @GetMapping("/article/get-quantity")
    int findQuantityById(@RequestParam Long articleId);

    @GetMapping("/article/get-id-article-by-id")
    Optional<Long> findIdArticleById(@RequestParam Long id);

    @GetMapping("/article/get-id-categories-by-articles-id")
    List<Long> findIdCategoryByArticlesId(@RequestParam List<Long> articleId);

}
