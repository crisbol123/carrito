package com.pragma.carrito.adapters.driving.http.controller;


import com.pragma.carrito.adapters.driving.http.dto.cart.request.AddArticleToCartRequest;
import com.pragma.carrito.adapters.driving.http.handlers.CartHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")

public class ShoppingCartRestControllerAdapter {
    private final CartHandler cartHandler;

    public ShoppingCartRestControllerAdapter(CartHandler cartHandler) {
        this.cartHandler = cartHandler;
    }

@RequestMapping("/add-article-to-cart")
public void addArticleToCart(@RequestBody AddArticleToCartRequest request) {
    cartHandler.addArticleToCart(request);

}
@RequestMapping("/delete-article-from-cart")
public void deleteArticleFromCart(@RequestParam Long articleId) {
    cartHandler.deleteArticleFromCart(articleId);


}}