package com.pragma.carrito.adapters.driving.http.controller;

import com.pragma.carrito.adapters.driving.http.dto.cart.request.AddArticleToCartRequest;
import com.pragma.carrito.adapters.driving.http.dto.cart.request.FilterRequest;
import com.pragma.carrito.adapters.driving.http.dto.cart.request.GetAllArticlesRequest;
import com.pragma.carrito.adapters.driving.http.dto.cart.response.ArticleCartResponse;
import com.pragma.carrito.adapters.driving.http.handlers.CartHandler;
import com.pragma.carrito.domain.util.ArticleCartPagedResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class ShoppingCartRestControllerAdapter {

    private final CartHandler cartHandler;

    public ShoppingCartRestControllerAdapter(CartHandler cartHandler) {
        this.cartHandler = cartHandler;
    }

    @Operation(summary = "Add an article to the cart",
            description = "Adds an article to the shopping cart for the current user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Article added successfully"),
            @ApiResponse(responseCode = "404", description = "Article not found",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request data",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PostMapping("/add-article-to-cart")
    public void addArticleToCart(
            @RequestBody(description = "Details of the article to add", required = true)
            @org.springframework.web.bind.annotation.RequestBody AddArticleToCartRequest request) {
        cartHandler.addArticleToCart(request);
    }

    @Operation(summary = "Delete an article from the cart",
            description = "Removes a specific article from the shopping cart by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Article removed successfully"),
            @ApiResponse(responseCode = "404", description = "Article not found in cart",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @DeleteMapping("/delete-article-from-cart")
    public void deleteArticleFromCart(
            @RequestParam( required = true) Long articleId) {
        cartHandler.deleteArticleFromCart(articleId);
    }

    @Operation(summary = "Get all articles in the cart",
            description = "Retrieves a paginated list of all articles in the shopping cart for the current user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of articles retrieved successfully",
                    content = @Content(schema = @Schema(implementation = ArticleCartPagedResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid filter or request parameters",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @GetMapping("/get-all-articles")
    public ResponseEntity<ArticleCartPagedResponse<ArticleCartResponse>> getAllArticles(
            @RequestParam( required = true)
            GetAllArticlesRequest request,
            @RequestParam( required = false)
            FilterRequest filterRequest) {
        ArticleCartPagedResponse<ArticleCartResponse> response = cartHandler.getAllArticles(request, filterRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/buy")
    public void buy() {
        cartHandler.buy();

    }

}
