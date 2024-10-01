package com.pragma.carrito.adapters.driven.jpa.mysql.mapper;

import com.pragma.carrito.adapters.driven.jpa.mysql.entity.CartArticleEntity;
import com.pragma.carrito.adapters.driven.jpa.mysql.entity.CartEntity;
import com.pragma.carrito.domain.model.Cart;
import com.pragma.carrito.domain.model.CartArticle;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ICartArticleEntityMapper {

    CartEntity toEntity(Cart cart);

    CartArticleEntity toEntity(CartArticle cartArticle);
@Mapping(target = "id", source = "id")
    Cart toModel(CartEntity cartEntity);

    CartArticle toModel(CartArticleEntity cartArticleEntity);


    List<CartArticle> toModelList(List<CartArticleEntity> cartArticleEntities);


}
