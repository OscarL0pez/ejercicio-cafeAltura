package com.cafeteria.cafedealtura.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cafeteria.cafedealtura.dto.UpdateRolesRequestDTO;
import com.cafeteria.cafedealtura.model.Customer;
import com.cafeteria.cafedealtura.model.Role;
import com.cafeteria.cafedealtura.repository.CustomerRepository;
import com.cafeteria.cafedealtura.repository.RoleRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    private final RoleRepository roleRepository;
    private final CustomerRepository customerRepository;

    public RoleController(RoleRepository roleRepository, CustomerRepository customerRepository) {
        this.roleRepository = roleRepository;
        this.customerRepository = customerRepository;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Role>> getAllRoles() {
        return ResponseEntity.ok(roleRepository.findAll());
    }

    @PostMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Customer> updateUserRoles(@Valid @RequestBody UpdateRolesRequestDTO request) {
        // Buscar el usuario
        Customer customer = customerRepository.findByEmail(request.getUserEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Obtener los roles solicitados
        Set<Role> newRoles = new HashSet<>();
        for (Role.RoleType roleType : request.getRoles()) {
            Role role = roleRepository.findByName(roleType)
                    .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + roleType));
            newRoles.add(role);
        }

        // Actualizar los roles del usuario
        customer.setRoles(newRoles);
        Customer updatedCustomer = customerRepository.save(customer);

        return ResponseEntity.ok(updatedCustomer);
    }
}