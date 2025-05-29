package com.cafeteria.cafedealtura.domain.user.dto.auth;

import java.util.Set;

/**
 * DTO para respuestas de autenticación (login y registro).
 * Contiene el token JWT y la información básica del usuario.
 */
public class AuthResponseDTO {
    private final String token;
    private final Long userId;
    private final String name;
    private final String email;
    private final Set<String> roles;

    public AuthResponseDTO(String token, Long userId, String name, String email, Set<String> roles) {
        this.token = token;
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.roles = roles;
    }

    // Getters
    public String getToken() {
        return token;
    }

    public Long getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Set<String> getRoles() {
        return roles;
    }
}