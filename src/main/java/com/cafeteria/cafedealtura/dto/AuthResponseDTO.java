package com.cafeteria.cafedealtura.dto;

import java.util.Set;

public class AuthResponseDTO {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String name;
    private String email;
    private Set<String> roles;

    public AuthResponseDTO() {
    }

    public AuthResponseDTO(String token, Long id, String name, String email, Set<String> roles) {
        this.token = token;
        this.id = id;
        this.name = name;
        this.email = email;
        this.roles = roles;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}