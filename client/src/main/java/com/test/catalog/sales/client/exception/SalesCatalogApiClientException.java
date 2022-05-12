package com.test.catalog.sales.client.exception;

import lombok.Data;

@Data
public class SalesCatalogApiClientException extends RuntimeException {

    private String message;
    private Integer code;
    private Throwable cause;

    public SalesCatalogApiClientException(String message) {

        super(message);
    }

    public SalesCatalogApiClientException(Throwable cause, String message, Integer code) {
        super();
        this.message = message;
        this.code = code;
        this.cause = cause;

    }
    public SalesCatalogApiClientException(Throwable cause) {

        super(cause);
    }
}
