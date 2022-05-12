package com.test.catalog.sales.exception.handler;

import com.test.catalog.sales.exception.TestExceptionHandler;
import com.test.catalog.sales.exception.apierror.ApiError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

import static org.springframework.http.HttpStatus.*;

/**
 * controller advice to handle exception response
 */
@ControllerAdvice
public class SalesCatalogControllerAdvice extends TestExceptionHandler {
    /**
     * LOG.
     */
    private static final Logger LOG = LoggerFactory.getLogger(SalesCatalogControllerAdvice.class);

    /**
     * Resource not found exceptions related to processing master catalog.
     *
     * @param ex the ex
     * @return the response entity
     */
    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler({ResourceNotFoundException.class})
    protected ResponseEntity<Object> resourceNotFoundException(
            RuntimeException ex, WebRequest request) {
        LOG.warn("Calling resourceNotFoundException of SalesCatalogControllerAdvice ");
        ApiError apiError = new ApiError(NOT_FOUND);
        apiError.setTestErrorCode(NOT_FOUND.value());
        apiError.setTitle(ex.getMessage());
        apiError.setPath(request.getDescription(false));
        LOG.error(apiError.getTitle(), apiError, ex);
        return buildResponseEntity(apiError);
    }


    /**
     * internal error related to processing master catalog.
     *
     * @param ex the ex
     * @return the response entity
     */
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler({SalesCatalogGenericException.class,
            SalesCatalogDBException.class})
    protected ResponseEntity<Object> genericExceptions(
            RuntimeException ex, WebRequest request) {
        LOG.warn("Calling genericExceptions of SalesCatalogControllerAdvice ");
        ApiError apiError = new ApiError(INTERNAL_SERVER_ERROR);
        apiError.setTestErrorCode(INTERNAL_SERVER_ERROR.value());
        apiError.setTitle(ex.getMessage());
        apiError.setPath(request.getDescription(false));
        LOG.error(apiError.getTitle(), apiError, ex);
        return buildResponseEntity(apiError);
    }

    /**
     * internal error related to processing master catalog.
     *
     * @param ex the ex
     * @return the response entity
     */
    @ResponseStatus(NO_CONTENT)
    @ExceptionHandler({NoContentException.class})
    protected ResponseEntity<Object> noContentExceptions(
            RuntimeException ex, WebRequest request) {
        LOG.warn("Calling noContentExceptions of SalesCatalogControllerAdvice ");
        ApiError apiError = new ApiError(NO_CONTENT);
        apiError.setTestErrorCode(NO_CONTENT.value());
        apiError.setTitle(ex.getMessage());
        apiError.setPath(request.getDescription(false));
        LOG.error(apiError.getTitle(), apiError, ex);
        return buildResponseEntity(apiError);
    }
    /**
     * handleNoHandlerFoundException related to processing master catalog.
     *
     * @param ex the ex
     * @param headers the headers
     * @param status the status
     * @param request the request
     * @return the response entity
     */
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        LOG.warn("Calling genericExceptions of SalesCatalogControllerAdvice ");
        ApiError apiError = new ApiError(NOT_FOUND);
        apiError.setTestErrorCode(NOT_FOUND.value());
        apiError.setTitle(ex.getMessage());
        apiError.setPath(request.getDescription(false));
        LOG.error(apiError.getTitle(), apiError, ex);
        return buildResponseEntity(apiError);
    }
}
