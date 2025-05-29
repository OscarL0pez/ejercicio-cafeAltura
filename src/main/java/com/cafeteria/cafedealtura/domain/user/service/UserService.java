package com.cafeteria.cafedealtura.domain.user.service;

import com.cafeteria.cafedealtura.common.exceptions.BadRequestException;
import com.cafeteria.cafedealtura.common.exceptions.ResourceNotFoundException;
import com.cafeteria.cafedealtura.domain.user.dto.profile.UserProfileDTO;
import com.cafeteria.cafedealtura.domain.user.model.Role;
import com.cafeteria.cafedealtura.domain.user.model.User;
import com.cafeteria.cafedealtura.domain.user.repository.RoleRepository;
import com.cafeteria.cafedealtura.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Servicio para la gestión de usuarios.
 * Implementa la lógica de negocio relacionada con los usuarios.
 */
@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Registra un nuevo usuario en el sistema.
     * 
     * @param user El usuario a registrar
     * @return El usuario registrado
     * @throws BadRequestException si el email ya está registrado
     */
    @Transactional
    public User registerUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new BadRequestException("El email ya está registrado");
        }

        // Encriptar contraseña
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Asignar rol por defecto
        Role userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new BadRequestException("Rol USER no encontrado"));
        user.setRoles(Set.of(userRole));

        return userRepository.save(user);
    }

    /**
     * Busca un usuario por su email.
     * 
     * @param email Email del usuario
     * @return Optional con el usuario si existe
     */
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Obtiene todos los usuarios del sistema.
     * 
     * @return Lista de perfiles de usuario
     */
    public List<UserProfileDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToProfileDTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene un usuario por su ID.
     * 
     * @param id ID del usuario
     * @return Perfil del usuario
     * @throws ResourceNotFoundException si el usuario no existe
     */
    public UserProfileDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", id));
        return convertToProfileDTO(user);
    }

    /**
     * Actualiza los datos de un usuario.
     * 
     * @param id             ID del usuario
     * @param userProfileDTO Nuevos datos del usuario
     * @return Perfil actualizado del usuario
     * @throws ResourceNotFoundException si el usuario no existe
     * @throws BadRequestException       si el email ya está en uso
     */
    @Transactional
    public UserProfileDTO updateUser(Long id, UserProfileDTO userProfileDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", id));

        // Verificar si el nuevo email ya está en uso por otro usuario
        if (!user.getEmail().equals(userProfileDTO.getEmail()) &&
                userRepository.existsByEmail(userProfileDTO.getEmail())) {
            throw new BadRequestException("El email ya está en uso");
        }

        user.setName(userProfileDTO.getName());
        user.setEmail(userProfileDTO.getEmail());

        return convertToProfileDTO(userRepository.save(user));
    }

    /**
     * Elimina un usuario del sistema.
     * 
     * @param id ID del usuario a eliminar
     * @throws ResourceNotFoundException si el usuario no existe
     */
    @Transactional
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("Usuario", "id", id);
        }
        userRepository.deleteById(id);
    }

    /**
     * Convierte una entidad User a UserProfileDTO.
     * 
     * @param user Entidad User
     * @return UserProfileDTO
     */
    private UserProfileDTO convertToProfileDTO(User user) {
        return new UserProfileDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRoles().stream()
                        .map(Role::getName)
                        .collect(Collectors.toList()));
    }
}