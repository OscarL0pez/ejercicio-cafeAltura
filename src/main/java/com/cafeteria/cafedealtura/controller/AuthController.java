package com.cafeteria.cafedealtura.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cafeteria.cafedealtura.dto.LoginRequestDTO;
import com.cafeteria.cafedealtura.dto.LoginResponseDTO;
import com.cafeteria.cafedealtura.dto.RegisterRequestDTO;
import com.cafeteria.cafedealtura.model.Customer;
import com.cafeteria.cafedealtura.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<Customer> register(@Valid @RequestBody RegisterRequestDTO request) {
        Customer customer = authService.registerUser(request);
        return ResponseEntity.ok(customer);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO request) {
        Customer customer = authService.login(request);
        return ResponseEntity.ok(new LoginResponseDTO(customer));
    }
}
