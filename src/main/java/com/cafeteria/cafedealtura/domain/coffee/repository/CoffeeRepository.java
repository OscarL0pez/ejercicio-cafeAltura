package com.cafeteria.cafedealtura.domain.coffee.repository;

import com.cafeteria.cafedealtura.domain.coffee.model.Coffee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la entidad Coffee.
 * Proporciona métodos para acceder y manipular los datos de cafés.
 */
@Repository
public interface CoffeeRepository extends JpaRepository<Coffee, Long> {
    /**
     * Busca un café por su nombre.
     * 
     * @param name Nombre del café
     * @return Optional con el café si existe
     */
    Optional<Coffee> findByName(String name);

    /**
     * Verifica si existe un café con el nombre especificado.
     * 
     * @param name Nombre del café
     * @return true si existe un café con ese nombre
     */
    boolean existsByName(String name);

    /**
     * Busca cafés por su origen.
     * 
     * @param origin Origen del café
     * @return Lista de cafés con ese origen
     */
    List<Coffee> findByOrigin(String origin);

    /**
     * Busca cafés por rango de precio.
     * 
     * @param minPrice Precio mínimo (inclusive)
     * @param maxPrice Precio máximo (inclusive)
     * @return Lista de cafés dentro del rango de precio
     */
    List<Coffee> findByPriceBetween(Double minPrice, Double maxPrice);
}