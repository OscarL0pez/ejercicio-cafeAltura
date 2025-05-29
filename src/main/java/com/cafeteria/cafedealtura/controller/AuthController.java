package com.cafeteria.cafedealtura.controller;

import com.cafeteria.cafedealtura.domain.user.dto.auth.AuthResponseDTO;
import com.cafeteria.cafedealtura.domain.user.dto.auth.LoginRequestDTO;
import com.cafeteria.cafedealtura.domain.user.dto.auth.RegisterRequestDTO;
import com.cafeteria.cafedealtura.domain.user.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador para la autenticación y registro de usuarios.
 */
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Registra un nuevo usuario en el sistema.
     * 
     * @param registerRequest Datos del usuario a registrar
     * @return Respuesta con el token JWT y datos del usuario
     */
    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@Valid @RequestBody RegisterRequestDTO registerRequest) {
        return ResponseEntity.ok(authService.register(registerRequest));
    }

    /**
     * Autentica un usuario y genera un token JWT.
     * 
     * @param loginRequest Credenciales del usuario
     * @return Respuesta con el token JWT y datos del usuario
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }
}