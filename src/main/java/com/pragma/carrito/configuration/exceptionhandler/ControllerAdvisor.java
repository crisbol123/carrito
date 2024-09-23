package com.pragma.carrito.configuration.exceptionhandler;


import com.pragma.carrito.adapters.driven.feigns.exception.NoNegativeStockException;
import com.pragma.carrito.adapters.driven.jpa.mysql.exception.ElementNotFoundException;
import com.pragma.carrito.configuration.Constants;
import com.pragma.carrito.domain.exception.ExcededCategoriesLimitException;
import com.pragma.carrito.domain.exception.OutOfStockException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ControllerAdvisor {




  @ExceptionHandler(ElementNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleElementNotFoundException(ElementNotFoundException ex) {
        ExceptionResponse response = new ExceptionResponse(
                String.format(Constants.ELEMENT_NOT_FOUND_EXCEPTION_MESSAGE,ex.getMessage()),
                HttpStatus.NOT_FOUND.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(OutOfStockException.class)
    public ResponseEntity<ExceptionResponse> handleOutOfStockException(OutOfStockException ex) {
        ExceptionResponse response = new ExceptionResponse(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ExcededCategoriesLimitException.class)
    public ResponseEntity<ExceptionResponse> handleExcededCategoriesLimitException(ExcededCategoriesLimitException ex) {
        ExceptionResponse response = new ExceptionResponse(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.toString(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }



}
