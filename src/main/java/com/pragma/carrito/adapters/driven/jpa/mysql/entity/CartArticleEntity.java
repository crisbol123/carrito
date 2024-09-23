package com.pragma.carrito.adapters.driven.jpa.mysql.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "car_article")
@Getter
@Setter
public class CartArticleEntity {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "id")
private Long id;
    @ManyToOne
    @JoinColumn(name = "cart_id")
    private CartEntity cart;
    @Column(name = "article_id")
    private Long articleId;
    @Column(name = "quantity")
    private int quantity;
}
