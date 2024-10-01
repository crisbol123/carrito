package com.pragma.carrito.domain.exception;

import com.pragma.carrito.configuration.Constants;
import com.pragma.carrito.domain.util.DomainConstants;
import org.yaml.snakeyaml.scanner.Constant;

public class NoElementsInCartException extends RuntimeException {
    public NoElementsInCartException() {
        super(Constants.NO_ELEMENTS_IN_CART);
    }
}
