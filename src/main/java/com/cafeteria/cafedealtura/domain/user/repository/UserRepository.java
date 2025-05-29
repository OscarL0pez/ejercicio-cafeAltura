package com.cafeteria.cafedealtura.domain.user.repository;

import com.cafeteria.cafedealtura.domain.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Repositorio para la entidad User.
 * Proporciona métodos para acceder y manipular los datos de usuarios.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    Optional<User> findByName(String name);

    boolean existsByName(String name);
}