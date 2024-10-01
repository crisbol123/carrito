package com.pragma.carrito.adapters.driven.feigns;

import com.pragma.carrito.adapters.driven.feigns.exception.NoEnoughStockException;
import com.pragma.carrito.adapters.driven.feigns.exception.NoNegativeStockException;
import com.pragma.carrito.adapters.driven.jpa.mysql.exception.ElementNotFoundException;
import com.pragma.carrito.domain.util.Constants;
import feign.Response;
import feign.codec.ErrorDecoder;

public class CustomErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {


        switch (response.status()) {
            case Constants.HTTP_STATUS_NO_NEGATIVE_STOCK:
                return new NoEnoughStockException(Constants.NO_ENOUGH_STOCK);
            case Constants.HTTP_STATUS_ELEMENT_NOT_FOUND:
                return new ElementNotFoundException(Constants.ARTICLE_ID);
            default:
                return new Exception();
        }
    }
}
