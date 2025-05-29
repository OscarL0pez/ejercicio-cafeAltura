package com.cafeteria.cafedealtura.common.utils;

import com.cafeteria.cafedealtura.common.exceptions.BadRequestException;
import org.springframework.util.StringUtils;

/**
 * Clase de utilidades para validaciones comunes en toda la aplicación.
 */
public final class ValidationUtils {
    private ValidationUtils() {
        // Prevenir instanciación
    }

    /**
     * Valida que un objeto no sea nulo.
     * 
     * @param value     El objeto a validar
     * @param fieldName El nombre del campo para el mensaje de error
     * @throws BadRequestException si el objeto es nulo
     */
    public static void validateNotNull(Object value, String fieldName) {
        if (value == null) {
            throw new BadRequestException("El " + fieldName + " no puede ser nulo");
        }
    }

    /**
     * Valida que un string no sea nulo o vacío.
     * 
     * @param value     El valor a validar
     * @param fieldName El nombre del campo para el mensaje de error
     * @throws BadRequestException si el valor es nulo o vacío
     */
    public static void validateNotEmpty(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new BadRequestException("El " + fieldName + " no puede estar vacío");
        }
    }

    /**
     * Valida que una colección no sea nula o vacía.
     * 
     * @param value     La colección a validar
     * @param fieldName El nombre del campo para el mensaje de error
     * @throws BadRequestException si la colección es nula o vacía
     */
    public static void validateNotEmpty(java.util.Collection<?> value, String fieldName) {
        if (value == null || value.isEmpty()) {
            throw new BadRequestException("La lista de " + fieldName + " no puede estar vacía");
        }
    }

    /**
     * Valida que un número sea positivo.
     * 
     * @param value     El valor a validar
     * @param fieldName El nombre del campo para el mensaje de error
     * @throws BadRequestException si el valor no es positivo
     */
    public static void validatePositive(Number value, String fieldName) {
        if (value == null || value.doubleValue() <= 0) {
            throw new BadRequestException("El " + fieldName + " debe ser mayor que 0");
        }
    }

    /**
     * Valida que un número esté dentro de un rango.
     * 
     * @param value     El valor a validar
     * @param min       El valor mínimo (inclusive)
     * @param max       El valor máximo (inclusive)
     * @param fieldName El nombre del campo para el mensaje de error
     * @throws BadRequestException si el valor está fuera del rango
     */
    public static void validateRange(Number value, Number min, Number max, String fieldName) {
        if (value == null || value.doubleValue() < min.doubleValue() || value.doubleValue() > max.doubleValue()) {
            throw new BadRequestException(
                    String.format("%s debe estar entre %s y %s", fieldName, min, max));
        }
    }

    /**
     * Valida que un email tenga un formato válido.
     * 
     * @param email El email a validar
     * @throws BadRequestException si el email no tiene un formato válido
     */
    public static void validateEmail(String email) {
        validateNotEmpty(email, "email");
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new BadRequestException("El email debe tener un formato válido");
        }
    }

    /**
     * Valida que una contraseña cumpla con los requisitos mínimos.
     * 
     * @param password La contraseña a validar
     * @throws BadRequestException si la contraseña no cumple con los requisitos
     */
    public static void validatePassword(String password) {
        validateNotEmpty(password, "contraseña");
        validateMinLength(password.length(), 6, "contraseña");
        if (!password.matches(".*[A-Z].*")) {
            throw new BadRequestException("La contraseña debe contener al menos una mayúscula");
        }
        if (!password.matches(".*[a-z].*")) {
            throw new BadRequestException("La contraseña debe contener al menos una minúscula");
        }
        if (!password.matches(".*\\d.*")) {
            throw new BadRequestException("La contraseña debe contener al menos un número");
        }
    }

    /**
     * Valida que una cadena tenga una longitud mínima.
     * 
     * @param length    Longitud de la cadena
     * @param min       Longitud mínima permitida
     * @param fieldName Nombre del campo para el mensaje de error
     * @throws BadRequestException si la longitud es menor que el mínimo
     */
    public static void validateMinLength(int length, int min, String fieldName) {
        if (length < min) {
            throw new BadRequestException("El " + fieldName + " debe tener al menos " + min + " caracteres");
        }
    }
}