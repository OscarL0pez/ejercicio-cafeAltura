package com.cafeteria.cafedealtura.dto;

import java.util.Set;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import com.cafeteria.cafedealtura.model.Role.RoleType;

public class UpdateRolesRequestDTO {

    @NotNull(message = "El email del usuario es obligatorio")
    private String userEmail;

    @NotEmpty(message = "Debe especificar al menos un rol")
    private Set<RoleType> roles;

    public UpdateRolesRequestDTO() {
    }

    public UpdateRolesRequestDTO(String userEmail, Set<RoleType> roles) {
        this.userEmail = userEmail;
        this.roles = roles;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Set<RoleType> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleType> roles) {
        this.roles = roles;
    }
}