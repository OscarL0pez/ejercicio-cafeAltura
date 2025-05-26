package com.cafeteria.cafedealtura.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cafeteria.cafedealtura.model.Cafe;

@Repository
public interface CafeRepository extends JpaRepository<Cafe, Long> {
    // Los métodos básicos CRUD vienen incluidos en JpaRepository
    // Podemos agregar métodos personalizados aquí si los necesitamos
}
