package com.pragma.carrito.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Cart {
    private Long id;
    private Long userId;
    private LocalDate UpdateDate;
    private LocalDate CreateDate;
}
