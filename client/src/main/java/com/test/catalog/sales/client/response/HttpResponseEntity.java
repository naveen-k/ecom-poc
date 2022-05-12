package com.test.catalog.sales.client.response;


import lombok.NoArgsConstructor;

/**
 * The Class HttpResponseEntity.
 *
 * @param <T> the generic type
 */
@NoArgsConstructor
public class HttpResponseEntity<T> {

    /**
     * The status.
     */
    private Status status;

    /**
     * The data.
     */
    private T data;


    /**
     * Instantiates a new http response entity.
     *
     * @param status the status
     * @param data   the data
     */
    public HttpResponseEntity(Status status, T data) {
        this.status = status;
        this.data = data;
    }

    /**
     * Gets the status.
     *
     * @return the status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Sets the status.
     *
     * @param status the new status
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * Gets the data.
     *
     * @return the data
     */
    public T getData() {
        return data;
    }

    /**
     * Sets the data.
     *
     * @param data the new data
     */
    public void setData(T data) {
        this.data = data;
    }

}
