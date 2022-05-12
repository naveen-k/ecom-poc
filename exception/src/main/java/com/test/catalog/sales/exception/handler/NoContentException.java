package com.test.catalog.sales.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom exception class for no content.
 */
@ResponseStatus(HttpStatus.NO_CONTENT)
public class NoContentException extends RuntimeException {
    private String message;
    private Integer code;
    private Throwable cause;


    public NoContentException() {
    }

    public NoContentException(String message) {
        super(message);
    }

    public NoContentException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoContentException(Throwable cause, String message, Integer code) {
        super();
        this.message = message;
        this.code = code;
        this.cause = cause;
    }

}
