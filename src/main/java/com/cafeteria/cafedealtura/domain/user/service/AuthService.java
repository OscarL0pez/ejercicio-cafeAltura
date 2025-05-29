package com.cafeteria.cafedealtura.domain.user.service;

import com.cafeteria.cafedealtura.common.exceptions.BadRequestException;
import com.cafeteria.cafedealtura.domain.user.dto.auth.AuthResponseDTO;
import com.cafeteria.cafedealtura.domain.user.dto.auth.LoginRequestDTO;
import com.cafeteria.cafedealtura.domain.user.dto.auth.RegisterRequestDTO;
import com.cafeteria.cafedealtura.domain.user.model.Role;
import com.cafeteria.cafedealtura.domain.user.model.User;
import com.cafeteria.cafedealtura.domain.user.repository.RoleRepository;
import com.cafeteria.cafedealtura.domain.user.repository.UserRepository;
import com.cafeteria.cafedealtura.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Servicio para la gestión de autenticación.
 * Maneja el registro y login de usuarios.
 */
@Service
public class AuthService {
        private final AuthenticationManager authenticationManager;
        private final UserRepository userRepository;
        private final RoleRepository roleRepository;
        private final PasswordEncoder passwordEncoder;
        private final JwtTokenProvider jwtTokenProvider;

        @Autowired
        public AuthService(AuthenticationManager authenticationManager,
                        UserRepository userRepository,
                        RoleRepository roleRepository,
                        PasswordEncoder passwordEncoder,
                        JwtTokenProvider jwtTokenProvider) {
                this.authenticationManager = authenticationManager;
                this.userRepository = userRepository;
                this.roleRepository = roleRepository;
                this.passwordEncoder = passwordEncoder;
                this.jwtTokenProvider = jwtTokenProvider;
        }

        /**
         * Registra un nuevo usuario en el sistema.
         * 
         * @param registerRequest Datos del usuario a registrar
         * @return Respuesta de autenticación con token JWT
         * @throws BadRequestException si el email ya está registrado
         */
        @Transactional
        public AuthResponseDTO register(RegisterRequestDTO registerRequest) {
                if (userRepository.existsByEmail(registerRequest.getEmail())) {
                        throw new BadRequestException("El email ya está registrado");
                }

                // Crear usuario
                User user = new User();
                user.setName(registerRequest.getName());
                user.setEmail(registerRequest.getEmail());
                user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

                // Asignar rol por defecto
                Set<Role> roles = new HashSet<>();
                Role userRole = roleRepository.findByName("USER")
                                .orElseThrow(() -> new BadRequestException("Error: Rol no encontrado"));
                roles.add(userRole);
                user.setRoles(roles);

                // Guardar usuario
                user = userRepository.save(user);

                // Generar token JWT
                String jwt = jwtTokenProvider.generateToken(user);

                // Obtener roles del usuario
                Set<String> roleNames = user.getRoles().stream()
                                .map(Role::getName)
                                .collect(Collectors.toSet());

                return new AuthResponseDTO(jwt, user.getId(), user.getName(), user.getEmail(), roleNames);
        }

        /**
         * Autentica un usuario y genera un token JWT.
         * 
         * @param loginRequest Credenciales del usuario
         * @return Respuesta de autenticación con token JWT
         */
        public AuthResponseDTO login(LoginRequestDTO loginRequest) {
                // Autenticar usuario
                Authentication authentication = authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(
                                                loginRequest.getEmail(),
                                                loginRequest.getPassword()));

                SecurityContextHolder.getContext().setAuthentication(authentication);

                // Obtener usuario autenticado
                User user = userRepository.findByEmail(loginRequest.getEmail())
                                .orElseThrow(() -> new BadRequestException("Usuario no encontrado"));

                // Generar token JWT
                String jwt = jwtTokenProvider.generateToken(user);

                // Obtener roles del usuario
                Set<String> roleNames = user.getRoles().stream()
                                .map(Role::getName)
                                .collect(Collectors.toSet());

                return new AuthResponseDTO(jwt, user.getId(), user.getName(), user.getEmail(), roleNames);
        }

        /**
         * Obtiene el usuario actualmente autenticado.
         * 
         * @return Usuario autenticado
         * @throws BadRequestException si no hay usuario autenticado
         */
        public User getCurrentUser() {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                if (authentication == null || !authentication.isAuthenticated()) {
                        throw new BadRequestException("No hay usuario autenticado");
                }

                return userRepository.findByEmail(authentication.getName())
                                .orElseThrow(() -> new BadRequestException("Usuario no encontrado"));
        }
}