package com.pragma.carrito.adapters.driven.jpa.mysql.adapter;

import com.pragma.carrito.adapters.driven.jpa.mysql.entity.CartArticleEntity;
import com.pragma.carrito.adapters.driven.jpa.mysql.entity.CartEntity;
import com.pragma.carrito.adapters.driven.jpa.mysql.mapper.ICartArticleEntityMapper;
import com.pragma.carrito.adapters.driven.jpa.mysql.repository.CartArticleRepository;
import com.pragma.carrito.adapters.driven.jpa.mysql.repository.CartRepository;
import com.pragma.carrito.domain.model.Cart;
import com.pragma.carrito.domain.model.CartArticle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CartAdapterTest {

    @Mock
    private CartArticleRepository cartArticleRepository;

    @Mock
    private ICartArticleEntityMapper cartArticleEntityMapper;

    @Mock
    private CartRepository cartRepository;

    @InjectMocks
    private CartAdapter cartAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findIdArticlesByUserId_shouldReturnListOfIds() {
        Long userId = 1L;
        List<Long> expectedIds = List.of(1L, 2L, 3L);

        when(cartArticleRepository.findIdArticlesByUserId(userId)).thenReturn(expectedIds);

        List<Long> result = cartAdapter.findIdArticlesByUserId(userId);

        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals(expectedIds, result);
        verify(cartArticleRepository, times(1)).findIdArticlesByUserId(userId);
    }

    @Test
    void findArticleCartByUserIdAndArticleId_shouldReturnArticle() {
        Long userId = 1L;
        Long articleId = 1L;
        CartArticleEntity cartArticleEntity = new CartArticleEntity();
        CartArticle cartArticle = new CartArticle();

        when(cartArticleRepository.findArticleCartByUserIdAndArticleId(articleId, userId))
                .thenReturn(Optional.of(cartArticleEntity));
        when(cartArticleEntityMapper.toModel(cartArticleEntity)).thenReturn(cartArticle);

        Optional<CartArticle> result = cartAdapter.findArticleCartByUserIdAndArticleId(userId, articleId);

        assertTrue(result.isPresent());
        assertEquals(cartArticle, result.get());
        verify(cartArticleRepository, times(1)).findArticleCartByUserIdAndArticleId(articleId, userId);
    }

    @Test
    void findArticleCartByUserIdAndArticleId_shouldReturnEmpty() {
        Long userId = 1L;
        Long articleId = 1L;

        when(cartArticleRepository.findArticleCartByUserIdAndArticleId(articleId, userId)).thenReturn(Optional.empty());

        Optional<CartArticle> result = cartAdapter.findArticleCartByUserIdAndArticleId(userId, articleId);

        assertTrue(result.isEmpty());
        verify(cartArticleRepository, times(1)).findArticleCartByUserIdAndArticleId(articleId, userId);
    }

    @Test
    void findCartByUserId_shouldReturnCart() {
        Long userId = 1L;
        CartEntity cartEntity = new CartEntity();
        Cart cart = new Cart();

        when(cartRepository.findCartByUserId(userId)).thenReturn(Optional.of(cartEntity));
        when(cartArticleEntityMapper.toModel(cartEntity)).thenReturn(cart);

        Optional<Cart> result = cartAdapter.findCartByUserId(userId);

        assertTrue(result.isPresent());
        assertEquals(cart, result.get());
        verify(cartRepository, times(1)).findCartByUserId(userId);
    }

    @Test
    void findCartByUserId_shouldReturnEmpty() {
        Long userId = 1L;

        when(cartRepository.findCartByUserId(userId)).thenReturn(Optional.empty());

        Optional<Cart> result = cartAdapter.findCartByUserId(userId);

        assertTrue(result.isEmpty());
        verify(cartRepository, times(1)).findCartByUserId(userId);
    }

    @Test
    void saveArticleCart_shouldSaveArticle() {
        CartArticle cartArticle = new CartArticle();
        CartEntity cartEntity = new CartEntity();
        CartArticleEntity cartArticleEntity = new CartArticleEntity();
        cartArticleEntity.setCart(cartEntity);

        when(cartArticleEntityMapper.toEntity(cartArticle)).thenReturn(cartArticleEntity);
        when(cartRepository.save(cartEntity)).thenReturn(cartEntity);

        cartAdapter.saveArticleCart(cartArticle);

        verify(cartRepository, times(1)).save(cartEntity);
        verify(cartArticleRepository, times(1)).save(cartArticleEntity);
    }

    @Test
    void deleteArticleCart_shouldDeleteArticle() {
        CartArticle cartArticle = new CartArticle();
        CartArticleEntity cartArticleEntity = new CartArticleEntity();
        cartArticleEntity.setCart(new CartEntity());

        when(cartArticleEntityMapper.toEntity(cartArticle)).thenReturn(cartArticleEntity);

        cartAdapter.deleteArticleCart(cartArticle);

        verify(cartRepository, times(1)).save(cartArticleEntity.getCart());
        verify(cartArticleRepository, times(1)).delete(cartArticleEntity);
    }

    @Test
    void findQuantitiesByUserId_shouldReturnListOfQuantities() {
        Long userId = 1L;
        List<Integer> expectedQuantities = List.of(2, 3, 4);

        when(cartArticleRepository.findQuantitiesByUserId(userId)).thenReturn(expectedQuantities);

        List<Integer> result = cartAdapter.findQuantitiesByUserId(userId);

        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals(expectedQuantities, result);
        verify(cartArticleRepository, times(1)).findQuantitiesByUserId(userId);
    }

    @Test
    void findAllArticlesByUserId_shouldReturnListOfCartArticles() {
        Long userId = 1L;
        List<CartArticleEntity> cartArticleEntities = List.of(new CartArticleEntity());
        List<CartArticle> expectedCartArticles = List.of(new CartArticle());

        when(cartArticleRepository.findAllArticlesByUserId(userId)).thenReturn(cartArticleEntities);
        when(cartArticleEntityMapper.toModelList(cartArticleEntities)).thenReturn(expectedCartArticles);

        Optional<List<CartArticle>> result = cartAdapter.findAllArticlesByUserId(userId);

        assertTrue(result.isPresent());
        assertEquals(expectedCartArticles, result.get());
        verify(cartArticleRepository, times(1)).findAllArticlesByUserId(userId);
    }
}
