package com.test.catalog.sales.exception.apierror;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonTypeIdResolver;

import com.test.catalog.sales.exception.LowerCaseClassNameResolver;
import lombok.Data;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import javax.validation.ConstraintViolation;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.CUSTOM, property = "error", visible = true)
@JsonTypeIdResolver(LowerCaseClassNameResolver.class)
@JsonInclude(JsonInclude.Include.NON_NULL)

public
class ApiError {

    private HttpStatus status;
    private int testErrorCode;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy hh:mm:ss")

    private LocalDateTime timestamp;
    private String title;
    private String detail;
    private String debugMessage;
    private String path;

    private List<ApiSubError> subErrors;

    private ApiError() {
        timestamp = LocalDateTime.now();
    }

    public ApiError(HttpStatus status) {
        this();
        this.status = status;
    }
    public ApiError(HttpStatus status, int testErrorCode) {
        this();
        this.status = status;
        this.testErrorCode = testErrorCode;
    }

    public ApiError(HttpStatus status, Throwable ex) {
        this();
        this.status = status;
        this.title = "Unexpected error";
        this.debugMessage = ex.getLocalizedMessage();
    }
    public ApiError(HttpStatus status, int testErrorCode, Throwable ex) {
        this();
        this.status = status;
        this.testErrorCode = testErrorCode;
        this.title = "Unexpected error";
        this.debugMessage = ex.getLocalizedMessage();
    }

    public ApiError(HttpStatus status, String title, Throwable ex) {
        this();
        this.status = status;
        this.title = title;
        this.debugMessage = ex.getLocalizedMessage();
    }
    public ApiError(HttpStatus status, int testErrorCode, String title, Throwable ex) {
        this();
        this.status = status;
        this.title = title;
        this.testErrorCode = testErrorCode;
        this.debugMessage = ex.getLocalizedMessage();
    }

    public ApiError(HttpStatus status, String title, Throwable ex, String path) {
        this();
        this.status = status;
        this.title = title;
        this.debugMessage = ex.getLocalizedMessage();
        this.path = path;
    }

    public ApiError(HttpStatus status, int testErrorCode, String title, Throwable ex, String path) {
        this();
        this.status = status;
        this.title = title;
        this.testErrorCode = testErrorCode;
        this.debugMessage = ex.getLocalizedMessage();
        this.path = path;
    }

    private void addSubError(ApiSubError subError) {
        if (subErrors == null) {
            subErrors = new ArrayList<>();
        }
        subErrors.add(subError);
    }

    private void addValidationError(String object, String field, Object rejectedValue, String message) {
        addSubError(new ApiSubError(object, field, rejectedValue, message));
    }

    private void addValidationError(String object, String message) {
        addSubError(new ApiSubError(object, message));
    }

    private void addValidationError(FieldError fieldError) {
        this.addValidationError(
                fieldError.getObjectName(),
                fieldError.getField(),
                fieldError.getRejectedValue(),
                fieldError.getDefaultMessage());
    }

    public void addValidationErrors(List<FieldError> fieldErrors) {
        fieldErrors.forEach(this::addValidationError);
    }

    private void addValidationError(ObjectError objectError) {
        this.addValidationError(
                objectError.getObjectName(),
                objectError.getDefaultMessage());
    }

    public void addValidationError(List<ObjectError> globalErrors) {
        globalErrors.forEach(this::addValidationError);
    }

    /**
     * Utility method for adding error of ConstraintViolation. Usually when a @Validated validation fails.
     *
     * @param cv the ConstraintViolation
     */
    private void addValidationError(ConstraintViolation<?> cv) {
        this.addValidationError(
                cv.getRootBeanClass().getSimpleName(),
                ((PathImpl) cv.getPropertyPath()).getLeafNode().asString(),
                cv.getInvalidValue(),
                cv.getMessage());
    }

    public void addValidationErrors(Set<ConstraintViolation<?>> constraintViolations) {
        constraintViolations.forEach(this::addValidationError);
    }


}

