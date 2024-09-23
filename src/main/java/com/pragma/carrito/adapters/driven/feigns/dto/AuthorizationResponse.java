package com.pragma.carrito.adapters.driven.feigns.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuthorizationResponse
{
    private boolean isPresent;
    private String email;
    private String role;
    private Long userId;


}
