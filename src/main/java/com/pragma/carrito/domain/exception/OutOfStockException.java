package com.pragma.carrito.domain.exception;

public class OutOfStockException extends RuntimeException {
    public OutOfStockException(String message, String estimatedStockDate) {
        super(message + ", Estimated stock date: " + estimatedStockDate);
    }
}
