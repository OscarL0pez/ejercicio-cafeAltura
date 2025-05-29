package com.cafeteria.cafedealtura.common.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Excepción lanzada cuando no se encuentra un recurso solicitado.
 */
public class ResourceNotFoundException extends BaseException {
    private static final String ERROR_CODE = "RESOURCE_NOT_FOUND";

    public ResourceNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND, ERROR_CODE);
    }

    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(
                String.format("%s no encontrado con %s : '%s'", resourceName, fieldName, fieldValue),
                HttpStatus.NOT_FOUND,
                ERROR_CODE);
    }
}