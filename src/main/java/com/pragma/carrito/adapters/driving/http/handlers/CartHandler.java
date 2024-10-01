package com.pragma.carrito.adapters.driving.http.handlers;

import com.pragma.carrito.adapters.driving.http.dto.cart.request.AddArticleToCartRequest;
import com.pragma.carrito.adapters.driving.http.dto.cart.request.FilterRequest;
import com.pragma.carrito.adapters.driving.http.dto.cart.request.GetAllArticlesRequest;
import com.pragma.carrito.adapters.driving.http.dto.cart.response.ArticleCartResponse;
import com.pragma.carrito.adapters.driving.http.mapper.cart.IArticleCartRequestMapper;
import com.pragma.carrito.domain.api.ICartServicePort;

import com.pragma.carrito.domain.util.ArticleCartPagedResponse;
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
    public ArticleCartPagedResponse<ArticleCartResponse> getAllArticles(GetAllArticlesRequest request, FilterRequest filterRequest) {
ArticleCartPagedResponse<ArticleCartResponse> articles= cartServicePort.getAllArticles(request.isAscOrderByName(),filterRequest.getMark(),filterRequest.getCategory(),request.getPage(),request.getSize());
        return articles;
    }
    public void buy() {
        cartServicePort.buy();
    }
}
