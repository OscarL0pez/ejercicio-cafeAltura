package com.cafeteria.cafedealtura.domain.user.dto.profile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import com.cafeteria.cafedealtura.common.utils.ValidationUtils;
import java.util.List;

/**
 * DTO para representar el perfil completo de un usuario.
 * Incluye información básica y roles.
 */
public class UserProfileDTO {
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    private String name;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email debe tener un formato válido")
    private String email;

    private List<String> roles;

    public UserProfileDTO() {
    }

    public UserProfileDTO(Long id, String name, String email, List<String> roles) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.roles = roles;
    }

    // Getters y setters con validación
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        ValidationUtils.validatePositive(id, "id");
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        ValidationUtils.validateNotEmpty(name, "nombre");
        ValidationUtils.validateRange(name.length(), 2, 50, "nombre");
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        ValidationUtils.validateEmail(email);
        this.email = email;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}