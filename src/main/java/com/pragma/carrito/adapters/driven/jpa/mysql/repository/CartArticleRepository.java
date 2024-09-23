// src/main/java/com/pragma/carrito/adapters/driven/jpa/mysql/repository/CarRepository.java
package com.pragma.carrito.adapters.driven.jpa.mysql.repository;

import com.pragma.carrito.adapters.driven.jpa.mysql.entity.CartArticleEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface CartArticleRepository extends JpaRepository<CartArticleEntity, Long> {
    @Query("SELECT ca.articleId FROM CartArticleEntity ca JOIN ca.cart c WHERE c.userId = :userId")
    List<Long> findIdArticlesByUserId(@Param("userId") Long userId);

    @Query("SELECT ca.quantity FROM CartArticleEntity ca WHERE ca.articleId = :articleId AND ca.cart.userId = :userId")
    Optional<Integer> findQuantityByUserIdAndArticleId(@Param("articleId") Long articleId, @Param("userId") Long userId);

    @Query("SELECT ca FROM CartArticleEntity ca WHERE ca.articleId = :articleId AND ca.cart.userId = :userId")
    Optional<CartArticleEntity> findArticleCartByUserIdAndArticleId(@Param("articleId") Long articleId, @Param("userId") Long userId);
}