package com.pragma.carrito.adapters.driven.jpa.mysql.adapter;

import com.pragma.carrito.adapters.driven.jpa.mysql.entity.CartArticleEntity;
import com.pragma.carrito.adapters.driven.jpa.mysql.entity.CartEntity;
import com.pragma.carrito.adapters.driven.jpa.mysql.mapper.ICartArticleEntityMapper;
import com.pragma.carrito.adapters.driven.jpa.mysql.repository.CartArticleRepository;
import com.pragma.carrito.adapters.driven.jpa.mysql.repository.CartRepository;
import com.pragma.carrito.domain.model.Cart;
import com.pragma.carrito.domain.model.CartArticle;
import com.pragma.carrito.domain.spi.CartPersistencePort;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class CartAdapter implements CartPersistencePort {
private final CartArticleRepository cartArticleRepository;
private final ICartArticleEntityMapper cartArticleEntityMapper;
private final CartRepository cartRepository;

    @Override
    public List<Long> findIdArticlesByUserId(Long userId) {
      return cartArticleRepository.findIdArticlesByUserId(userId);
    }

    @Override
    public Optional<CartArticle> findArticleCartByUserIdAndArticleId(Long userId, Long articleId) {
      Optional<CartArticleEntity>cartArticleEntity= cartArticleRepository.findArticleCartByUserIdAndArticleId(articleId, userId);
        return cartArticleEntity.map(cartArticleEntityMapper::toModel);
    }

    @Override
    public Optional<Cart> findCartByUserId(Long userId) {
        Optional<CartEntity>cartEntity= cartRepository.findCartByUserId(userId);
        return cartEntity.map(cartArticleEntityMapper::toModel);
    }




    @Override
    public void saveArticleCart(CartArticle cartArticle) {
        CartEntity cartEntity = cartArticleEntityMapper.toEntity(cartArticle).getCart();

        if (cartEntity.getId() == null) {
            cartEntity = cartRepository.save(cartEntity);
        }

        CartArticleEntity cartArticleEntity = cartArticleEntityMapper.toEntity(cartArticle);
        cartArticleEntity.setCart(cartEntity);
        cartArticleRepository.save(cartArticleEntity);;
    }

    @Override
    public void deleteArticleCart(CartArticle cartArticle) {
        CartArticleEntity cartArticleEntity = cartArticleEntityMapper.toEntity(cartArticle);
        cartRepository.save(cartArticleEntity.getCart());
        cartArticleRepository.delete(cartArticleEntity);
    }

    @Override
    public List<Integer> findQuantitiesByUserId(Long userId) {
        return cartArticleRepository.findQuantitiesByUserId(userId);
    }

    @Override
    public Optional<List<CartArticle>> findAllArticlesByUserId(Long userId) {
        return Optional.of(cartArticleEntityMapper.toModelList(cartArticleRepository.findAllArticlesByUserId(userId)));
    }


}
