package com.cafeteria.cafedealtura.common.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Excepción lanzada cuando la solicitud del cliente es inválida.
 */
public class BadRequestException extends BaseException {
    private static final String ERROR_CODE = "BAD_REQUEST";

    public BadRequestException(String message) {
        super(message, HttpStatus.BAD_REQUEST, ERROR_CODE);
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, HttpStatus.BAD_REQUEST, ERROR_CODE, cause);
    }
}