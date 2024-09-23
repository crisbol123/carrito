package com.pragma.carrito.adapters.driving.http.mapper.cart;


import com.pragma.carrito.adapters.driving.http.dto.cart.request.AddArticleToCartRequest;
import com.pragma.carrito.domain.model.CartArticle;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ICartRequestMapper.class})
public interface IArticleCartRequestMapper {

    @Mapping(target = "cart", expression = "java(new com.pragma.carrito.domain.model.Cart())")
    CartArticle mapToArticleCart(AddArticleToCartRequest request);


}