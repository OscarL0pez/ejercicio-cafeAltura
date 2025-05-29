package com.cafeteria.cafedealtura.common.exceptions;

import com.cafeteria.cafedealtura.common.constants.ApiConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Manejador global de excepciones para toda la aplicación.
 * Proporciona respuestas consistentes para diferentes tipos de errores.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> handleBaseException(BaseException ex) {
        logger.error("Error de negocio: {}", ex.getMessage());
        ErrorResponse error = new ErrorResponse(
                ex.getStatus(),
                ex.getErrorCode(),
                ex.getMessage(),
                LocalDateTime.now());
        return new ResponseEntity<>(error, ex.getStatus());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException ex) {
        logger.error("Error de autenticación: {}", ex.getMessage());
        ErrorResponse error = new ErrorResponse(
                HttpStatus.UNAUTHORIZED,
                "INVALID_CREDENTIALS",
                "Credenciales inválidas",
                LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        logger.error("Usuario no encontrado: {}", ex.getMessage());
        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_FOUND,
                "USER_NOT_FOUND",
                ex.getMessage(),
                LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        logger.error("Error de validación: {}", ex.getMessage());
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ValidationErrorResponse error = new ValidationErrorResponse(
                HttpStatus.BAD_REQUEST,
                "VALIDATION_ERROR",
                "Error de validación en los datos de entrada",
                LocalDateTime.now(),
                errors);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex) {
        logger.error("Error inesperado: ", ex);
        ErrorResponse error = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "INTERNAL_SERVER_ERROR",
                ApiConstants.ERROR_INTERNAL_SERVER,
                LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Clase para representar una respuesta de error estándar
     */
    public static class ErrorResponse {
        private final HttpStatus status;
        private final String errorCode;
        private final String message;
        private final LocalDateTime timestamp;

        public ErrorResponse(HttpStatus status, String errorCode, String message, LocalDateTime timestamp) {
            this.status = status;
            this.errorCode = errorCode;
            this.message = message;
            this.timestamp = timestamp;
        }

        // Getters
        public HttpStatus getStatus() {
            return status;
        }

        public String getErrorCode() {
            return errorCode;
        }

        public String getMessage() {
            return message;
        }

        public LocalDateTime getTimestamp() {
            return timestamp;
        }
    }

    /**
     * Clase para representar una respuesta de error de validación
     */
    public static class ValidationErrorResponse extends ErrorResponse {
        private final Map<String, String> errors;

        public ValidationErrorResponse(HttpStatus status, String errorCode, String message,
                LocalDateTime timestamp, Map<String, String> errors) {
            super(status, errorCode, message, timestamp);
            this.errors = errors;
        }

        public Map<String, String> getErrors() {
            return errors;
        }
    }
}