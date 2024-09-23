package com.pragma.carrito.domain.api;


import com.pragma.carrito.domain.model.CartArticle;

public interface ICartServicePort {
void addArticleToCart(CartArticle cartArticle);
void deleteArticleFromCart(Long cartArticleId);


}
