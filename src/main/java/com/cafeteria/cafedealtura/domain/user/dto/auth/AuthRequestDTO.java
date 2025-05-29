package com.cafeteria.cafedealtura.domain.user.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import com.cafeteria.cafedealtura.common.utils.ValidationUtils;

/**
 * DTO base para solicitudes de autenticación.
 * Contiene los campos comunes para login y registro.
 */
public abstract class AuthRequestDTO {
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email debe tener un formato válido")
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    private String password;

    public AuthRequestDTO() {
    }

    public AuthRequestDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        ValidationUtils.validateEmail(email);
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        ValidationUtils.validatePassword(password);
        this.password = password;
    }
}