package com.pragma.carrito.domain.spi;

import com.pragma.carrito.domain.model.Cart;
import com.pragma.carrito.domain.model.CartArticle;

import java.util.List;
import java.util.Optional;

public interface CartPersistencePort {
    List<Long> findIdArticlesByUserId(Long userId);

   Optional<CartArticle> findArticleCartByUserIdAndArticleId(Long userId, Long articleId);

    Optional<Cart> findCartByUserId(Long userId);

    void saveArticleCart(CartArticle cartArticle);

    void deleteArticleCart(CartArticle cartArticle);

    List<Integer> findQuantitiesByUserId(Long userId);

    Optional<List<CartArticle>> findAllArticlesByUserId(Long userId);







}
