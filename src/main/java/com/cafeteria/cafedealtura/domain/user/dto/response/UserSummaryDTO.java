package com.cafeteria.cafedealtura.domain.user.dto.response;

import com.cafeteria.cafedealtura.domain.user.model.User;

/**
 * DTO para representar un resumen de usuario en las respuestas de la API.
 * Incluye solo la información básica del usuario.
 */
public class UserSummaryDTO {
    private final Long id;
    private final String name;
    private final String email;

    public UserSummaryDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}