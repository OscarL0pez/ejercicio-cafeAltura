package com.cafeteria.cafedealtura.common.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Excepción lanzada cuando el usuario no tiene los permisos necesarios.
 */
public class UnauthorizedException extends BaseException {
    private static final String ERROR_CODE = "UNAUTHORIZED";

    public UnauthorizedException(String message) {
        super(message, HttpStatus.FORBIDDEN, ERROR_CODE);
    }

    public UnauthorizedException(String message, Throwable cause) {
        super(message, HttpStatus.FORBIDDEN, ERROR_CODE, cause);
    }
}