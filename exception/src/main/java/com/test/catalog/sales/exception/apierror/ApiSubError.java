package com.test.catalog.sales.exception.apierror;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor


public class ApiSubError {
    private String object;
    private String field;
    private Object rejectedValue;
    private String message;

    ApiSubError(String object, String message) {
        this.object = object;
        this.message = message;
    }

}
