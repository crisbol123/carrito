package com.pragma.carrito.adapters.securityconfig;

import com.pragma.carrito.adapters.driven.feigns.dto.AuthorizationResponse;
import com.pragma.carrito.domain.spi.ISecurityContextPort;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;

@NoArgsConstructor
public class SecurityContextPortImpl implements ISecurityContextPort {

    @Override
    public Long getUserId() {
        AuthorizationResponse authorizationResponse = (AuthorizationResponse) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return authorizationResponse.getUserId();
    }
}
