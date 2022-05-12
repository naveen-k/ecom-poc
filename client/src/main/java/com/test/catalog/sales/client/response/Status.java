package com.test.catalog.sales.client.response;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;


/**
 * The Class Status.
 */
@NoArgsConstructor
public class Status {

    /**
     * The http status.
     */
    private HttpStatus httpStatus;

    /**
     * The status code.
     */
    private Integer statusCode;

    /**
     * The message.
     */
    private String message;

    /**
     * Instantiates a new status.
     *
     * @param httpStatus the http status
     * @param message    the message
     */
    public Status(HttpStatus httpStatus, Integer statusCode, String message) {
        this.httpStatus = httpStatus;
        this.statusCode = statusCode;
        this.message = message;
    }

    /**
     * Gets the http status.
     *
     * @return the http status
     */
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    /**
     * Sets the http status.
     *
     * @param httpStatus the new http status
     */
    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    /**
     * Gets the message.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the message.
     *
     * @param message the new message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }
}
