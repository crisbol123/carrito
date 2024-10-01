package com.pragma.carrito.domain.api;


import com.pragma.carrito.adapters.driving.http.dto.cart.response.ArticleCartResponse;
import com.pragma.carrito.domain.model.CartArticle;
import com.pragma.carrito.domain.util.ArticleCartPagedResponse;

public interface ICartServicePort {
void addArticleToCart(CartArticle cartArticle);
void deleteArticleFromCart(Long cartArticleId);

ArticleCartPagedResponse<ArticleCartResponse> getAllArticles(Boolean ascOrder, String marca, String categoria, Integer page, Integer size);
void buy();

}
