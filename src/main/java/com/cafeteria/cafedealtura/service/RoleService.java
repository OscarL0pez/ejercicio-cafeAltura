package com.cafeteria.cafedealtura.service;

import com.cafeteria.cafedealtura.model.Role;
import com.cafeteria.cafedealtura.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public Role createRoleIfNotExists(String name) {
        return roleRepository.findByName(name).orElseGet(() -> roleRepository.save(new Role(name)));
    }

    public Optional<Role> findByName(String name) {
        return roleRepository.findByName(name);
    }
}