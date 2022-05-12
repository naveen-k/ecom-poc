package com.test.catalog.sales.exception.handler;

public class SalesCatalogDBException extends  RuntimeException{
    /**
     * Creates a new  SalesCatalogDBException object with the given message for any DB operation.
     *
     * @param message The description for this exception
     */
    public SalesCatalogDBException(String message) {
        super(message);
    }
    private String message;
    private Integer code;
    private Throwable cause;

    public SalesCatalogDBException(Throwable cause, String message, Integer code) {
        super();
        this.message = message;
        this.code = code;
        this.cause = cause;

    }

}
