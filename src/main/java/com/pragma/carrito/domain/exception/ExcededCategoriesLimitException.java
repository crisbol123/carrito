package com.pragma.carrito.domain.exception;

public class ExcededCategoriesLimitException extends RuntimeException {
    public ExcededCategoriesLimitException(String message) {
        super(message);
    }
}
