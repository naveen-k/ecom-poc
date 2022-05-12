package com.test.catalog.sales.exception.handler;

public class SalesCatalogGenericException extends RuntimeException{

    private String message;
    private Integer code;
    private Throwable cause;

    public SalesCatalogGenericException(String message, Integer code) {
        super();
        this.message =message;
        this.code = code;
    }

    public SalesCatalogGenericException(Throwable cause, String message, Integer code) {
        super();
        this.message =message;
        this.code = code;
        this.cause = cause;
    }

    public SalesCatalogGenericException(String message){
        super(message);
    }
}
