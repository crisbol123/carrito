package com.pragma.carrito.adapters.driven.jpa.mysql.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "cart")
@Getter
@Setter
public class CartEntity {
     @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
     @Column(name = "id")
        private Long id;
     @Column(name = "update_date")
        private LocalDate updateDate;
     @Column(name = "create_date")
        private LocalDate createDate;
     @Column(name = "user_id")
        private Long userId;



}