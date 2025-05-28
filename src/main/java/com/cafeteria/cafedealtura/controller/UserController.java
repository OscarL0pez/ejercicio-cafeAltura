package com.cafeteria.cafedealtura.controller;

import com.cafeteria.cafedealtura.dto.UserRegisterDTO;
import com.cafeteria.cafedealtura.dto.LoginRequestDTO;
import com.cafeteria.cafedealtura.dto.UserResponseDTO;
import com.cafeteria.cafedealtura.dto.AuthResponseDTO;
import com.cafeteria.cafedealtura.service.UserService;
import com.cafeteria.cafedealtura.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@Valid @RequestBody UserRegisterDTO dto) {
        UserResponseDTO user = userService.registerUser(dto);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginRequestDTO dto) {
        AuthResponseDTO response = authService.login(dto);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }
}