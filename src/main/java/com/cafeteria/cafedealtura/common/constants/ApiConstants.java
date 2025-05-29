package com.cafeteria.cafedealtura.common.constants;

/**
 * Constantes utilizadas en toda la API.
 * Centraliza valores comunes para mantener consistencia y facilitar cambios.
 */
public final class ApiConstants {
    private ApiConstants() {
        // Prevenir instanciación
    }

    // Rutas base de la API
    public static final String API_BASE_PATH = "/api";
    public static final String API_V1 = API_BASE_PATH + "/v1";

    // Rutas de endpoints
    public static final String CAFE_PATH = API_V1 + "/cafes";
    public static final String ORDER_PATH = API_V1 + "/orders";
    public static final String USER_PATH = API_V1 + "/users";
    public static final String AUTH_PATH = API_V1 + "/auth";

    // Parámetros comunes
    public static final String DEFAULT_PAGE_SIZE = "10";
    public static final String DEFAULT_PAGE_NUMBER = "0";

    // Mensajes de error comunes
    public static final String ERROR_RESOURCE_NOT_FOUND = "Recurso no encontrado";
    public static final String ERROR_UNAUTHORIZED = "No autorizado";
    public static final String ERROR_FORBIDDEN = "Acceso denegado";
    public static final String ERROR_BAD_REQUEST = "Solicitud inválida";
    public static final String ERROR_INTERNAL_SERVER = "Error interno del servidor";

    // Roles del sistema
    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_MANAGER = "MANAGER";
    public static final String ROLE_USER = "USER";

    // Configuración de seguridad
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final long TOKEN_EXPIRATION_TIME = 86400000; // 24 horas en milisegundos
}