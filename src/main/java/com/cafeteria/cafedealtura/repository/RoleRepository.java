package com.cafeteria.cafedealtura.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cafeteria.cafedealtura.model.Role;
import com.cafeteria.cafedealtura.model.Role.RoleType;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleType name);
}