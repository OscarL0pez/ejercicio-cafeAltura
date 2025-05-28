package com.cafeteria.cafedealtura.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cafeteria.cafedealtura.dto.LoginRequestDTO;
import com.cafeteria.cafedealtura.dto.RegisterRequestDTO;
import com.cafeteria.cafedealtura.model.Customer;
import com.cafeteria.cafedealtura.model.Role;
import com.cafeteria.cafedealtura.model.Role.RoleType;
import com.cafeteria.cafedealtura.repository.CustomerRepository;
import com.cafeteria.cafedealtura.repository.RoleRepository;

@Service
public class AuthService {

    private final CustomerRepository customerRepository;
    private final RoleRepository roleRepository;

    public AuthService(CustomerRepository customerRepository,
            RoleRepository roleRepository) {
        this.customerRepository = customerRepository;
        this.roleRepository = roleRepository;
    }

    @Transactional
    public Customer registerUser(RegisterRequestDTO request) {
        if (customerRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("El email ya está registrado");
        }
        Customer customer = new Customer();
        customer.setNombre(request.getNombre());
        customer.setEmail(request.getEmail());
        customer.setPassword(request.getPassword()); // Guardar en texto plano
        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName(RoleType.ROLE_USER)
                .orElseGet(() -> {
                    Role role = new Role(RoleType.ROLE_USER);
                    return roleRepository.save(role);
                });
        roles.add(userRole);
        customer.setRoles(roles);
        return customerRepository.save(customer);
    }

    @Transactional(readOnly = true)
    public Customer login(LoginRequestDTO request) {
        Customer customer = customerRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        if (!request.getPassword().equals(customer.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta");
        }
        return customer;
    }
}