package com.pragma.carrito.adapters.driven.feigns.clients;


import com.pragma.carrito.adapters.driven.feigns.FeignClientConfig;
import com.pragma.carrito.adapters.driven.feigns.dto.AuthorizationRequest;
import com.pragma.carrito.adapters.driven.feigns.dto.AuthorizationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user", url = "${usuario.service.url}", configuration = FeignClientConfig.class)
public interface UserFeignClient {
    @GetMapping("/auth-user/validate")
    AuthorizationResponse validateToken(@RequestBody AuthorizationRequest authorizationRequest);



}