package com.cafeteria.cafedealtura.controller;

import com.cafeteria.cafedealtura.domain.user.dto.profile.UserProfileDTO;
import com.cafeteria.cafedealtura.domain.user.model.User;
import com.cafeteria.cafedealtura.domain.user.service.AuthService;
import com.cafeteria.cafedealtura.domain.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador para la gestión de usuarios.
 */
@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;
    private final AuthService authService;

    @Autowired
    public UserController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    /**
     * Obtiene el perfil del usuario actualmente autenticado.
     * 
     * @return Perfil del usuario
     */
    @GetMapping("/me")
    public ResponseEntity<UserProfileDTO> getCurrentUser() {
        User user = authService.getCurrentUser();
        return ResponseEntity.ok(new UserProfileDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRoles().stream()
                        .map(role -> role.getName())
                        .toList()));
    }

    /**
     * Obtiene todos los usuarios del sistema.
     * Requiere rol ADMIN.
     * 
     * @return Lista de perfiles de usuario
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserProfileDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }
}