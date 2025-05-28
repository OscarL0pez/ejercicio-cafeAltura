package com.cafeteria.cafedealtura.service;

import com.cafeteria.cafedealtura.dto.AuthResponseDTO;
import com.cafeteria.cafedealtura.dto.LoginRequestDTO;
import com.cafeteria.cafedealtura.dto.RegisterRequestDTO;
import com.cafeteria.cafedealtura.exception.BadRequestException;
import com.cafeteria.cafedealtura.model.Role;
import com.cafeteria.cafedealtura.model.User;
import com.cafeteria.cafedealtura.repository.UserRepository;
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

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Transactional
    public AuthResponseDTO register(RegisterRequestDTO registerRequest) {
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new BadRequestException("El email ya está registrado");
        }

        User user = new User();
        user.setName(registerRequest.getName());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        Set<Role> roles = new HashSet<>();
        Role userRole = roleService.findByName("USER")
                .orElseThrow(() -> new BadRequestException("Error: Rol no encontrado"));
        roles.add(userRole);
        user.setRoles(roles);

        userRepository.save(user);

        String jwt = jwtTokenProvider.generateToken(user);
        Set<String> roleNames = user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toSet());

        return new AuthResponseDTO(jwt, user.getId(), user.getName(), user.getEmail(), roleNames);
    }

    public AuthResponseDTO login(LoginRequestDTO loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = (User) authentication.getPrincipal();
        String jwt = jwtTokenProvider.generateToken(user);

        Set<String> roles = user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toSet());

        return new AuthResponseDTO(jwt, user.getId(), user.getName(), user.getEmail(), roles);
    }
}