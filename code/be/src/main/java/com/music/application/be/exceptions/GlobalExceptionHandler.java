package com.music.application.be.exceptions;

import jakarta.persistence.EntityNotFoundException; // Thay javax.persistence bằng jakarta.persistence
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.security.core.AuthenticationException; // ĐÚNG
import org.springframework.security.access.AccessDeniedException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> handleIllegalArgumentException(IllegalArgumentException ex) {
        ApiError apiError = new ApiError(
                HttpStatus.BAD_REQUEST,
                LocalDateTime.now(),
                "Validation Error",
                ex.getMessage()
        );
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiError> handleRuntimeException(RuntimeException ex) {
        ApiError apiError = new ApiError(
                HttpStatus.INTERNAL_SERVER_ERROR,
                LocalDateTime.now(),
                "Internal Server Error",
                ex.getMessage()
        );
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MultipartException.class)
    public ResponseEntity<ApiError> handleMultipartException(MultipartException ex) {
        ApiError apiError = new ApiError(
                HttpStatus.BAD_REQUEST,
                LocalDateTime.now(),
                "File Upload Error",
                "Invalid or missing file in the request: " + ex.getMessage()
        );
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(jakarta.persistence.EntityNotFoundException.class) // Sửa lại package
    public ResponseEntity<ApiError> handleEntityNotFoundException(jakarta.persistence.EntityNotFoundException ex) {
        ApiError apiError = new ApiError(
                HttpStatus.NOT_FOUND,
                LocalDateTime.now(),
                "Not Found",
                ex.getMessage()
        );
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiError> handleAuthenticationException(AuthenticationException ex) {
        ApiError apiError = new ApiError(
                HttpStatus.UNAUTHORIZED,
                LocalDateTime.now(),
                "Authentication Failed",
                "Invalid or expired token: " + ex.getMessage()
        );
        return new ResponseEntity<>(apiError, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiError> handleAccessDeniedException(AccessDeniedException ex) {
        ApiError apiError = new ApiError(
                HttpStatus.FORBIDDEN,
                LocalDateTime.now(),
                "Authorization Failed",
                "You do not have permission to access this resource: " + ex.getMessage()
        );
        return new ResponseEntity<>(apiError, HttpStatus.FORBIDDEN);
    }
}