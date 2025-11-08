package com.commons.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.commons.dto.ErrorResponse;

/**
 * Centralized error mapping to your commons-dto.
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponse> handleConflict(ConflictException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(err("CONFLICT", ex.getMessage(), ex.getMessage()));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(err("NOT_FOUND", "Resource not found", ex.getMessage()));
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCustomerNotFound(CustomerNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(err("CUSTOMER_NOT_FOUND", "The customer ID does not exist", ex.getMessage()));
    }

    @ExceptionHandler(VersionMismatchException.class)
    public ResponseEntity<ErrorResponse> handleVersionMismatch(VersionMismatchException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(err("VERSION_MISMATCH", "Stale version or ETag", ex.getMessage()));
    }

    @ExceptionHandler(PreconditionRequiredException.class)
    public ResponseEntity<ErrorResponse> handlePreconditionRequired(PreconditionRequiredException ex) {
        return ResponseEntity.status(HttpStatus.PRECONDITION_REQUIRED)
                .body(err("HEADER_REQUIRED", "If-Match header required", ex.getMessage()));
    }

    // ---------- builder function ----------
    private ErrorResponse err(String code, String message, String details) {
        return ErrorResponse.builder()
                .error(ErrorResponse.ErrorDetail.builder()
                        .code(code)
                        .message(message)
                        .details(details)
                        .build())
                .build();
    }
}
