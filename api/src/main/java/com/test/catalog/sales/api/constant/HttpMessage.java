package com.test.catalog.sales.api.constant;


/**
 * The Enum HttpMessage.
 * Enum for HTTP messages.
 */
public enum HttpMessage {

    /**
     * The retrieved success.
     */
    RETRIEVED_SUCCESS("Retrieved Successfully"),

    /**
     * The bad request.
     */
    BAD_REQUEST("Bad Request"),

    /**
     * The bad request.
     */
    INTERNAL_SERVER_ERROR("Internal Server Error");

    /**
     * The message.
     */
    private String message;

    /**
     * Instantiates a new http message.
     *
     * @param message the message
     */
    HttpMessage(String message) {
        this.message = message;
    }

    /**
     * Gets the message.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }
}
