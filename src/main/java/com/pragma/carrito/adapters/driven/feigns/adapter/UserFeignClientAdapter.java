package com.pragma.carrito.adapters.driven.feigns.adapter;


import com.pragma.carrito.adapters.driven.feigns.clients.UserFeignClient;
import com.pragma.carrito.adapters.driven.feigns.dto.AuthorizationRequest;
import com.pragma.carrito.adapters.driven.feigns.dto.AuthorizationResponse;
import com.pragma.carrito.domain.spi.UserFeignClientPort;
import org.springframework.context.annotation.Bean;

public class UserFeignClientAdapter implements UserFeignClientPort {
    private final UserFeignClient userFeignClient;

    public UserFeignClientAdapter(UserFeignClient userFeignClient) {
        this.userFeignClient = userFeignClient;
    }

    @Override
    public AuthorizationResponse validateToken(String token) {

        return userFeignClient.validateToken(new AuthorizationRequest(token));

    }


}
