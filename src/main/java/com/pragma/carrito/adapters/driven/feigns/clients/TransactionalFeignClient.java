package com.pragma.carrito.adapters.driven.feigns.clients;


import com.pragma.carrito.adapters.driven.feigns.FeignClientConfig;
import com.pragma.carrito.adapters.driven.feigns.dto.CreateSaleRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "transactional-service", url = "${transactional.service.url}", configuration = FeignClientConfig.class)

public interface TransactionalFeignClient {
    @GetMapping("/supply/get-next-stock-date")
    String findNextStockDateById(@RequestParam Long idArticle);

    @PostMapping("/sales/create")
    void addSale(@RequestBody CreateSaleRequest saleRequest);

}
