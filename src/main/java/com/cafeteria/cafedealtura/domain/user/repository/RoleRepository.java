package com.cafeteria.cafedealtura.domain.user.repository;

import com.cafeteria.cafedealtura.domain.user.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Repositorio para la entidad Role.
 * Proporciona métodos para acceder y manipular los datos de roles.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);

    boolean existsByName(String name);
}