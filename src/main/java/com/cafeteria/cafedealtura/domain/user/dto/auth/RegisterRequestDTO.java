package com.cafeteria.cafedealtura.domain.user.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import com.cafeteria.cafedealtura.common.utils.ValidationUtils;

/**
 * DTO para solicitudes de registro de usuarios.
 * Extiende de AuthRequestDTO y añade el campo de nombre.
 */
public class RegisterRequestDTO extends AuthRequestDTO {
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    private String name;

    public RegisterRequestDTO() {
        super();
    }

    public RegisterRequestDTO(String name, String email, String password) {
        super(email, password);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        ValidationUtils.validateNotEmpty(name, "nombre");
        ValidationUtils.validateRange(name.length(), 2, 50, "nombre");
        this.name = name;
    }
}