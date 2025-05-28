package com.cafeteria.cafedealtura.dto;

import com.cafeteria.cafedealtura.model.Customer;

public class LoginResponseDTO {
    private Customer user;

    public LoginResponseDTO(Customer user) {
        this.user = user;
    }

    public Customer getUser() {
        return user;
    }

    public void setUser(Customer user) {
        this.user = user;
    }
}