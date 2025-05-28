package com.cafeteria.cafedealtura.service;

import com.cafeteria.cafedealtura.dto.UserRegisterDTO;
import com.cafeteria.cafedealtura.dto.UserResponseDTO;
import com.cafeteria.cafedealtura.model.Role;
import com.cafeteria.cafedealtura.model.User;
import com.cafeteria.cafedealtura.repository.RoleRepository;
import com.cafeteria.cafedealtura.repository.UserRepository;
import com.cafeteria.cafedealtura.util.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponseDTO registerUser(UserRegisterDTO dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }
        Role userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new RuntimeException("Rol USER no encontrado"));
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRoles(Set.of(userRole));
        user = userRepository.save(user);
        return UserMapper.toDTO(user);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll().stream().map(UserMapper::toDTO).collect(Collectors.toList());
    }
}