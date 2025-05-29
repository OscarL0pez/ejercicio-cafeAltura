package com.cafeteria.cafedealtura.domain.user.dto.profile;

import com.cafeteria.cafedealtura.common.utils.ValidationUtils;

/**
 * DTO simplificado para representar información básica de un usuario.
 * Se utiliza en respuestas donde no se necesita toda la información del perfil.
 */
public class UserSummaryDTO {
    private final Long id;
    private final String name;

    public UserSummaryDTO(Long id, String name) {
        ValidationUtils.validatePositive(id, "id");
        ValidationUtils.validateNotEmpty(name, "nombre");
        this.id = id;
        this.name = name;
    }

    public UserSummaryDTO(com.cafeteria.cafedealtura.domain.user.model.User user) {
        this(user.getId(), user.getName());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}