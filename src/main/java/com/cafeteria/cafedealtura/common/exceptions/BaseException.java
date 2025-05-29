package com.cafeteria.cafedealtura.common.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Clase base para todas las excepciones personalizadas de la aplicación.
 * Proporciona una estructura común para manejar errores de manera consistente.
 */
public abstract class BaseException extends RuntimeException {
    private final HttpStatus status;
    private final String errorCode;

    protected BaseException(String message, HttpStatus status, String errorCode) {
        super(message);
        this.status = status;
        this.errorCode = errorCode;
    }

    protected BaseException(String message, HttpStatus status, String errorCode, Throwable cause) {
        super(message, cause);
        this.status = status;
        this.errorCode = errorCode;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getErrorCode() {
        return errorCode;
    }
}