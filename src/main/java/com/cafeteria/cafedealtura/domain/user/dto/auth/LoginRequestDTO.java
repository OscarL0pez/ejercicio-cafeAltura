package com.cafeteria.cafedealtura.domain.user.dto.auth;

/**
 * DTO para solicitudes de inicio de sesión.
 * Extiende de AuthRequestDTO para reutilizar la validación de email y password.
 */
public class LoginRequestDTO extends AuthRequestDTO {
    public LoginRequestDTO() {
        super();
    }

    public LoginRequestDTO(String email, String password) {
        super(email, password);
    }
}