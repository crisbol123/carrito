package com.pragma.carrito.adapters.driving.http.handlers;

import com.pragma.carrito.adapters.driving.http.dto.cart.request.AddArticleToCartRequest;
import com.pragma.carrito.adapters.driving.http.mapper.cart.IArticleCartRequestMapper;
import com.pragma.carrito.domain.api.ICartServicePort;

import org.springframework.stereotype.Component;

@Component


public class CartHandler {
private final ICartServicePort cartServicePort;
private final IArticleCartRequestMapper cartRequestMapper;

public CartHandler(ICartServicePort cartServicePort, IArticleCartRequestMapper cartRequestMapper) {
        this.cartServicePort = cartServicePort;
        this.cartRequestMapper = cartRequestMapper;
    }
    public void addArticleToCart(AddArticleToCartRequest addArticleToCartRequest) {
        cartServicePort.addArticleToCart(cartRequestMapper.mapToArticleCart(addArticleToCartRequest));
    }
    public void deleteArticleFromCart(Long articleId) {
        cartServicePort.deleteArticleFromCart(articleId);
    }
}
