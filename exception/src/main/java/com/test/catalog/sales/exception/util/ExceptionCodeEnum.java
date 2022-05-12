package com.test.catalog.sales.exception.util;

public enum ExceptionCodeEnum {

    DB_ERROR(1001,"DB Error"),
    GENERIC_DB_ERROR(1002,"Generic DB Error");

    private final int exceptionCode;

    private final String exceptionMessage;

    ExceptionCodeEnum(int exceptionCode, String exceptionMessage){
        this.exceptionCode = exceptionCode;
        this.exceptionMessage = exceptionMessage;
    }

    public int getExceptionCode() {
        return exceptionCode;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }
}
