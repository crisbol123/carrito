package com.pragma.carrito.domain.usecases;

import com.pragma.carrito.domain.exception.NoElementsInCartException;
import com.pragma.carrito.domain.util.AddArticleReportRequest;
import com.pragma.carrito.domain.util.AddReportRequest;
import com.pragma.carrito.adapters.driven.jpa.mysql.exception.ElementNotFoundException;
import com.pragma.carrito.adapters.driving.http.dto.cart.response.ArticleCartResponse;
import com.pragma.carrito.configuration.Constants;
import com.pragma.carrito.domain.api.ICartServicePort;
import com.pragma.carrito.domain.exception.ExcededCategoriesLimitException;
import com.pragma.carrito.domain.exception.OutOfStockException;
import com.pragma.carrito.domain.model.Cart;
import com.pragma.carrito.domain.model.CartArticle;
import com.pragma.carrito.domain.spi.*;
import com.pragma.carrito.domain.util.ArticleCartPagedResponse;
import com.pragma.carrito.domain.util.PagedResponse;
import com.pragma.carrito.domain.util.StockInformationArticle;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.*;

@AllArgsConstructor
public class CartUseCase implements ICartServicePort {
    private final StockFeignClientPort stockFeignClientPort;
    private final TransactionalFeignClientPort transactionalFeignClientPort;
    private final CartPersistencePort cartPersistencePort;
    private final ISecurityContextPort securityContextPort;
    private final ReportFeignClientPort reportFeignClientPort;

    @Override
    public void addArticleToCart(CartArticle cartArticle) {
        validateIdArticle(cartArticle.getArticleId());
        Long userId = securityContextPort.getUserId();
        cartArticle.getCart().setUserId(userId);

        List<Long> idArticles = cartPersistencePort.findIdArticlesByUserId(cartArticle.getCart().getUserId());
        validateCategoriesLimit(idArticles, cartArticle.getArticleId());

        saveOrUpdateCart(cartArticle);
    }

    private void validateIdArticle(Long idArticle) {
        if (stockFeignClientPort.findArticleIdById(idArticle).isEmpty()) {
            throw new ElementNotFoundException(Constants.ARTICLE_ID);
        }
    }

    public void validateStock(Long idArticle, int quantity) {
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

    private void saveOrUpdateCart(CartArticle cartArticle) {
        Optional<Cart> cartDatabase = cartPersistencePort.findCartByUserId(cartArticle.getCart().getUserId());

        if (cartDatabase.isPresent()) {
            Optional<CartArticle> cartArticleDatabase = cartPersistencePort.findArticleCartByUserIdAndArticleId(cartArticle.getCart().getUserId(), cartArticle.getArticleId());

            if (cartArticleDatabase.isPresent()) {
                // Actualizar cantidad de artículo existente en el carrito
                updateArticleInCart(cartArticle, cartArticleDatabase.get());
            } else {
                // Añadir un nuevo artículo al carrito existente
                addArticleToExistingCart(cartArticle, cartDatabase.get());
            }
        } else {
            // Crear un nuevo carrito y añadir el artículo
            createNewCartWithArticle(cartArticle);
        }
    }

    private void updateArticleInCart(CartArticle cartArticle, CartArticle cartArticleDatabase) {
        int quantityInDatabase = cartArticleDatabase.getQuantity();
        validateStock(cartArticle.getArticleId(), cartArticle.getQuantity() + quantityInDatabase);
        cartArticleDatabase.setQuantity(cartArticle.getQuantity() + quantityInDatabase);
        cartArticleDatabase.getCart().setUpdateDate(LocalDate.now());
        cartPersistencePort.saveArticleCart(cartArticleDatabase);
    }

    private void addArticleToExistingCart(CartArticle cartArticle, Cart cartDatabase) {
        validateStock(cartArticle.getArticleId(), cartArticle.getQuantity());
        cartArticle.getCart().setId(cartDatabase.getId());
        cartArticle.getCart().setUpdateDate(LocalDate.now());
        cartPersistencePort.saveArticleCart(cartArticle);
    }

    private void createNewCartWithArticle(CartArticle cartArticle) {
        validateStock(cartArticle.getArticleId(), cartArticle.getQuantity());
        cartArticle.getCart().setUpdateDate(LocalDate.now());
        cartArticle.getCart().setCreateDate(LocalDate.now());
        cartPersistencePort.saveArticleCart(cartArticle);
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

    @Override
    public ArticleCartPagedResponse<ArticleCartResponse> getAllArticles(Boolean ascOrder, String mark, String category, Integer page, Integer size) {
        Long userId = securityContextPort.getUserId();
        Optional<List<CartArticle>> cartArticles = cartPersistencePort.findAllArticlesByUserId(userId);

        if (cartArticles.isEmpty()) {
            return new ArticleCartPagedResponse<>();
        }

        List<Long> articleIds = cartArticles.get().stream().map(CartArticle::getArticleId).toList();
        PagedResponse<StockInformationArticle> stockArticlesInformation = fetchStockInformation(articleIds, ascOrder, mark, category, page, size);

        if (stockArticlesInformation.getContent() == null) {
            throw new ElementNotFoundException(Constants.ARTICLE_ID);
        }

        List<ArticleCartResponse> articleCartResponses = mapCartArticlesToResponses(cartArticles.get(), stockArticlesInformation);
        updateInvalidArticles(articleCartResponses);

        return buildPagedResponse(articleCartResponses, stockArticlesInformation);
    }



    private PagedResponse<StockInformationArticle> fetchStockInformation(List<Long> articleIds, Boolean ascOrder, String mark, String category, Integer page, Integer size) {
        return stockFeignClientPort.getAllArticles(articleIds, ascOrder, mark, category, page, size);
    }

    private List<ArticleCartResponse> mapCartArticlesToResponses(List<CartArticle> cartArticles, PagedResponse<StockInformationArticle> stockArticlesInformation) {
        return stockArticlesInformation.getContent().stream()
                .map(stockInformationArticle -> {
                    CartArticle cartArticle = cartArticles.stream()
                            .filter(article -> article.getArticleId().equals(stockInformationArticle.getId()))
                            .findFirst()
                            .orElseThrow(() -> new ElementNotFoundException(Constants.ARTICLE_ID));
                    return new ArticleCartResponse(stockInformationArticle, cartArticle.getQuantity());
                })
                .toList();
    }

    private void updateInvalidArticles(List<ArticleCartResponse> articleCartResponses) {
        List<ArticleCartResponse> invalidArticles = articleCartResponses.stream()
                .filter(article -> article.getQuantityCart() > article.getQuantity())
                .toList();

        invalidArticles.forEach(article -> {
            article.setNextSupplyDate(transactionalFeignClientPort.findNextStockDateById(article.getId()));
        });
    }

    private ArticleCartPagedResponse<ArticleCartResponse> buildPagedResponse(List<ArticleCartResponse> articleCartResponses, PagedResponse<StockInformationArticle> stockArticlesInformation) {
        ArticleCartPagedResponse<ArticleCartResponse> articleCartPagedResponse = new ArticleCartPagedResponse<>();

        articleCartPagedResponse.setTotalAmount(articleCartResponses.stream().mapToDouble(ArticleCartResponse::getSubTotal).sum());
        articleCartPagedResponse.setContent(articleCartResponses);
        articleCartPagedResponse.setTotalElements(stockArticlesInformation.getTotalElements());
        articleCartPagedResponse.setTotalPages(stockArticlesInformation.getTotalPages());
        articleCartPagedResponse.setCurrentPage(stockArticlesInformation.getCurrentPage());
        articleCartPagedResponse.setLastPage(stockArticlesInformation.isLastPage());

        return articleCartPagedResponse;
    }
    @Override
    @Transactional
    public void buy() {
        Long userId = securityContextPort.getUserId();
        List<CartArticle> cartArticles = cartPersistencePort.findAllArticlesByUserId(userId)
                .orElseThrow(() -> new ElementNotFoundException(Constants.ARTICLE_ID));
if(cartArticles.isEmpty()){
            throw new NoElementsInCartException();
        }
        cartArticles.forEach(this::validateStockForArticle);

        List<CartArticle> updatedStockArticles = new ArrayList<>();
        try {
            for (CartArticle cartArticle : cartArticles) {
                updateStockForArticle(cartArticle);
                updatedStockArticles.add(cartArticle);
            }
            for (CartArticle cartArticle : cartArticles) {
                addSaleForArticle(cartArticle);
            }
            for (CartArticle cartArticle : cartArticles) {
                deleteArticleFromCart(cartArticle);
            }
            generatePurchaseReport(userId, cartArticles);
        } catch (Exception e) {
            for (CartArticle cartArticle : updatedStockArticles) {
                stockFeignClientPort.updateStock(cartArticle.getArticleId(), cartArticle.getQuantity());
            }
            throw new RuntimeException("Failed to complete the purchase. Stock has been restored.", e);
        }
    }

    private void validateStockForArticle(CartArticle cartArticle) {
        validateStock(cartArticle.getArticleId(), cartArticle.getQuantity());
    }

    private void updateStockForArticle(CartArticle cartArticle) {
        stockFeignClientPort.updateStock(cartArticle.getArticleId(), -cartArticle.getQuantity());
    }

    private void addSaleForArticle(CartArticle cartArticle) {
        Long userId = securityContextPort.getUserId();
        transactionalFeignClientPort.addSale(cartArticle.getArticleId(), cartArticle.getQuantity(), userId);
    }

    private void deleteArticleFromCart(CartArticle cartArticle) {
        cartPersistencePort.deleteArticleCart(cartArticle);
    }
    private void generatePurchaseReport(Long userId, List<CartArticle> cartArticles) {
        List<Long> articleIds = cartArticles.stream()
                .map(CartArticle::getArticleId)
                .toList();

        PagedResponse<StockInformationArticle> stockArticlesInformation = stockFeignClientPort.getAllArticles(articleIds, true, "", "", 0, articleIds.size());

        List<AddArticleReportRequest> articleReports = stockArticlesInformation.getContent().stream()
                .map(stockInfo -> new AddArticleReportRequest(
                        stockInfo.getName(),
                        cartArticles.stream()
                                .filter(cartArticle -> cartArticle.getArticleId().equals(stockInfo.getId()))
                                .findFirst()
                                .orElseThrow(() -> new ElementNotFoundException(Constants.ARTICLE_ID))
                                .getQuantity(),
                        stockInfo.getPrice()
                ))
                .toList();

        AddReportRequest reportRequest = new AddReportRequest(
                securityContextPort.getEmail(),
                articleReports.stream().mapToDouble(AddArticleReportRequest::getSubTotal).sum(),
                LocalDate.now(),
                articleReports
        );
        reportFeignClientPort.generateReport(reportRequest);
    }
}