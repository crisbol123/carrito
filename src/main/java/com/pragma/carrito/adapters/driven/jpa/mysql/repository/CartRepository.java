package com.pragma.carrito.adapters.driven.jpa.mysql.repository;

import com.pragma.carrito.adapters.driven.jpa.mysql.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, Long> {

    Optional<CartEntity> findCartByUserId(Long userId);
}
