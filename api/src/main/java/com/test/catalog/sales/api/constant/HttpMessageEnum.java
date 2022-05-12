package com.test.catalog.sales.api.constant;

public enum HttpMessageEnum {

    /**
     * The retrieved success.
     */
    RETRIEVED_SUCCESS("Retrieved Successfully"),

    /**
     * The retrieved success.
     */
    CREATED_SUCCESS("Created Successfully"),

    /**
     * The updated success.
     */
    UPDATED_SUCCESS("updated Successfully"),

    /**
     * The bad request.
     */
    BAD_REQUEST("Bad Request"),

    /**
     * NOT Foud.
     */
    NOT_FOUND("Not Found"),

    /**
     * The retrieved success.
     */
    ARCHIVED_SUCCESS("Archived Successfully"),

    /**
     * The bad request.
     */
    INTERNAL_SERVER_ERROR("Internal Server Error");

    /**
     * The message.
     */
    private final String message;

    /**
     * Instantiates a new http message.
     *
     * @param message the message
     */
    HttpMessageEnum(String message) {
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
