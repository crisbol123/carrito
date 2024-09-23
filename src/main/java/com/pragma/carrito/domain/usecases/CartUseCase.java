package com.pragma.carrito.domain.usecases;


import com.pragma.carrito.adapters.driven.jpa.mysql.exception.ElementNotFoundException;
import com.pragma.carrito.configuration.Constants;
import com.pragma.carrito.domain.api.ICartServicePort;

import com.pragma.carrito.domain.exception.ExcededCategoriesLimitException;
import com.pragma.carrito.domain.exception.OutOfStockException;
import com.pragma.carrito.domain.model.Cart;
import com.pragma.carrito.domain.model.CartArticle;
import com.pragma.carrito.domain.spi.*;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@AllArgsConstructor
public class CartUseCase implements ICartServicePort {
    private final StockFeignClientPort stockFeignClientPort;
    private final TransactionalFeignClientPort transactionalFeignClientPort;
    private final CartPersistencePort cartPersistencePort;
    private final ISecurityContextPort securityContextPort;

    @Override
    public void addArticleToCart(CartArticle cartArticle) {
        validateIdArticle(cartArticle.getArticleId());
        Long userId = securityContextPort.getUserId();

        cartArticle.getCart().setUserId(userId);

        List<Long> idArticles = cartPersistencePort.findIdArticlesByUserId(cartArticle.getCart().getUserId());
        validateCategoriesLimit(idArticles, cartArticle.getArticleId());


        saveOrUpdateArticleCart(cartArticle);


    }

    private void validateIdArticle(Long idArticle) {
        if (stockFeignClientPort.findArticleIdById(idArticle).isEmpty()) {
            throw new ElementNotFoundException(Constants.ARTICLE_ID);
        }
    }

    private void validateStock(Long idArticle, int quantity) {
        if (stockFeignClientPort.findQuantityById(idArticle) < quantity) {
            String dateNextStock = transactionalFeignClientPort.findNextStockDateById(idArticle);
            throw new OutOfStockException(Constants.OUT_OF_STOCK, dateNextStock);
        }
    }

    private void validateCategoriesLimit(List<Long> idArticles, Long idArticle) {
        if (!idArticles.contains(idArticle)) {
            idArticles.add(idArticle);
        }
        List<Long> IdsCategories = stockFeignClientPort.findIdCategoriesByArticlesId(idArticles);
        Map<Long, Integer> categoryCount = new HashMap<>();
        for (Long categoryId : IdsCategories) {
            categoryCount.put(categoryId, categoryCount.getOrDefault(categoryId, 0) + 1);
        }

        for (Map.Entry<Long, Integer> entry : categoryCount.entrySet()) {
            if (entry.getValue() > 3) {
                throw new ExcededCategoriesLimitException(Constants.EXCEDED_CATEGORIES_LIMIT);
            }
        }

    }

    private void saveOrUpdateArticleCart(CartArticle cartArticle) {
        Optional<Cart> cartDatabase = cartPersistencePort.findCartByUserId(cartArticle.getCart().getUserId());
        if (cartDatabase.isPresent()) {
            Optional<CartArticle> cartArticleDatabase = cartPersistencePort.findArticleCartByUserIdAndArticleId(cartArticle.getCart().getUserId(), cartArticle.getArticleId());
            if (cartArticleDatabase.isPresent()) {

                int quantityInDatabase = cartArticleDatabase.get().getQuantity();
                validateStock(cartArticle.getArticleId(), cartArticle.getQuantity() + quantityInDatabase);
                cartArticleDatabase.get().setQuantity(cartArticle.getQuantity() + quantityInDatabase);
                cartArticleDatabase.get().getCart().setUpdateDate(LocalDate.now());
                cartPersistencePort.saveArticleCart(cartArticleDatabase.get());
            } else {
                cartArticle.getCart().setId(cartDatabase.get().getId());
                cartArticle.getCart().setUpdateDate(LocalDate.now());
                cartPersistencePort.saveArticleCart(cartArticle);
            }

        } else {

            validateStock(cartArticle.getArticleId(), cartArticle.getQuantity());
            cartArticle.getCart().setUpdateDate(LocalDate.now());
            cartArticle.getCart().setCreateDate(LocalDate.now());
            cartPersistencePort.saveArticleCart(cartArticle);
        }


    }

    @Override
    public void deleteArticleFromCart(Long articleId) {
        Long userId = securityContextPort.getUserId();
        Optional<CartArticle> cartArticle = cartPersistencePort.findArticleCartByUserIdAndArticleId(userId, articleId);
        if (cartArticle.isPresent()) {
            cartArticle.get().getCart().setUpdateDate(LocalDate.now());
            cartPersistencePort.deleteArticleCart(cartArticle.get());
        } else {
            throw new ElementNotFoundException(Constants.ARTICLE_ID);
        }
    }
}


