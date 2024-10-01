package com.pragma.carrito.domain.usecases;

import com.pragma.carrito.adapters.driven.jpa.mysql.exception.ElementNotFoundException;
import com.pragma.carrito.adapters.driving.http.dto.cart.response.ArticleCartResponse;
import com.pragma.carrito.configuration.Constants;
import com.pragma.carrito.domain.model.Cart;
import com.pragma.carrito.domain.model.CartArticle;
import com.pragma.carrito.domain.spi.*;
import com.pragma.carrito.domain.util.ArticleCartPagedResponse;
import com.pragma.carrito.domain.util.PagedResponse;
import com.pragma.carrito.domain.util.StockInformationArticle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CartUseCaseTest {

    @Mock
    private StockFeignClientPort stockFeignClientPort;

    @Mock
    private TransactionalFeignClientPort transactionalFeignClientPort;

    @Mock
    private CartPersistencePort cartPersistencePort;

    @Mock
    private ISecurityContextPort securityContextPort;

    @InjectMocks
    private CartUseCase cartUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addArticleToCart_shouldAddArticle() {
        CartArticle cartArticle = new CartArticle();
        cartArticle.setArticleId(1L);
        cartArticle.setQuantity(1);
        cartArticle.setCart(new Cart(1L, 1L, null, null));

        when(securityContextPort.getUserId()).thenReturn(1L);
        when(stockFeignClientPort.findArticleIdById(1L)).thenReturn(Optional.of(1L));
        when(cartPersistencePort.findIdArticlesByUserId(1L)).thenReturn(new ArrayList<>());
        when(stockFeignClientPort.findQuantityById(1L)).thenReturn(10); // Suficiente stock
        when(transactionalFeignClientPort.findNextStockDateById(1L)).thenReturn("2024-12-31"); // Fecha válida de reabastecimiento

        cartUseCase.addArticleToCart(cartArticle);

        verify(cartPersistencePort, times(1)).saveArticleCart(any(CartArticle.class));
    }

    @Test
    void addArticleToCart_shouldThrowElementNotFoundException() {
        CartArticle cartArticle = new CartArticle();
        cartArticle.setArticleId(1L);
        cartArticle.setQuantity(1); // Asegurarse de que quantity no sea null
        cartArticle.setCart(new Cart(1L, 1L, null, null));

        when(stockFeignClientPort.findArticleIdById(1L)).thenReturn(Optional.empty());

        assertThrows(ElementNotFoundException.class, () -> cartUseCase.addArticleToCart(cartArticle));
    }

    @Test
    void getAllArticles_shouldReturnPagedResponse() {
        Long userId = 1L;
        List<CartArticle> cartArticles = List.of(new CartArticle(1L, new Cart(1L, userId, null, null), 1L, 2));
        List<StockInformationArticle> stockArticles = List.of(new StockInformationArticle(1L, "Article", 10, 100.0, "Mark", List.of("Category")));

        when(securityContextPort.getUserId()).thenReturn(userId);
        when(cartPersistencePort.findAllArticlesByUserId(userId)).thenReturn(Optional.of(cartArticles));
        when(stockFeignClientPort.getAllArticles(anyList(), anyBoolean(), anyString(), anyString(), anyInt(), anyInt()))
                .thenReturn(new PagedResponse<>(stockArticles, 1, 1, 1, true));

        ArticleCartPagedResponse<ArticleCartResponse> response = cartUseCase.getAllArticles(true, "Mark", "Category", 1, 10);

        assertNotNull(response);
        assertEquals(1, response.getTotalElements());
        assertEquals(1, response.getContent().size());
        assertEquals(200.0, response.getTotalAmount());
    }

    @Test
    void deleteArticleFromCart_shouldDeleteArticle() {
        Long articleId = 1L;
        Long userId = 1L;
        CartArticle cartArticle = new CartArticle(1L, new Cart(1L, userId, null, null), articleId, 1);

        when(securityContextPort.getUserId()).thenReturn(userId);
        when(cartPersistencePort.findArticleCartByUserIdAndArticleId(userId, articleId)).thenReturn(Optional.of(cartArticle));

        cartUseCase.deleteArticleFromCart(articleId);

        verify(cartPersistencePort, times(1)).deleteArticleCart(cartArticle);
    }

    @Test
    void deleteArticleFromCart_shouldThrowElementNotFoundException() {
        Long articleId = 1L;
        Long userId = 1L;

        when(securityContextPort.getUserId()).thenReturn(userId);
        when(cartPersistencePort.findArticleCartByUserIdAndArticleId(userId, articleId)).thenReturn(Optional.empty());

        assertThrows(ElementNotFoundException.class, () -> cartUseCase.deleteArticleFromCart(articleId));
    }
}