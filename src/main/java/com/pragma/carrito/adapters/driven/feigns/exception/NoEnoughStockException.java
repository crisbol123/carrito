package com.pragma.carrito.adapters.driven.feigns.exception;

public class NoEnoughStockException extends RuntimeException {
    public NoEnoughStockException(String message) {
        super(message);
    }
}
