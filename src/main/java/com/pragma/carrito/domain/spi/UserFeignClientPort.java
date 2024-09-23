package com.pragma.carrito.domain.spi;


import com.pragma.carrito.adapters.driven.feigns.dto.AuthorizationResponse;

public interface UserFeignClientPort {
    AuthorizationResponse validateToken(String token);

}
