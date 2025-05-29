package com.cafeteria.cafedealtura.domain.coffee.service;

import com.cafeteria.cafedealtura.common.exceptions.BadRequestException;
import com.cafeteria.cafedealtura.common.exceptions.ResourceNotFoundException;
import com.cafeteria.cafedealtura.domain.coffee.dto.request.CreateCoffeeRequestDTO;
import com.cafeteria.cafedealtura.domain.coffee.dto.request.UpdateCoffeeRequestDTO;
import com.cafeteria.cafedealtura.domain.coffee.dto.response.CoffeeResponseDTO;
import com.cafeteria.cafedealtura.domain.coffee.model.Coffee;
import com.cafeteria.cafedealtura.domain.coffee.repository.CoffeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Servicio para la gestión de cafés.
 * Implementa la lógica de negocio relacionada con los cafés.
 */
@Service
public class CoffeeService {
    private final CoffeeRepository coffeeRepository;

    @Autowired
    public CoffeeService(CoffeeRepository coffeeRepository) {
        this.coffeeRepository = coffeeRepository;
    }

    /**
     * Obtiene todos los cafés disponibles con paginación.
     * 
     * @param pageable Configuración de paginación y ordenamiento
     * @return Lista de cafés
     */
    public List<CoffeeResponseDTO> findAll(Pageable pageable) {
        Page<Coffee> page = coffeeRepository.findAll(pageable);
        return page.getContent().stream()
                .map(CoffeeResponseDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Busca un café por su ID.
     * 
     * @param id ID del café
     * @return Café encontrado
     * @throws ResourceNotFoundException si el café no existe
     */
    public CoffeeResponseDTO findById(Long id) {
        Coffee coffee = coffeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Café", "id", id));
        return new CoffeeResponseDTO(coffee);
    }

    /**
     * Crea un nuevo café.
     * 
     * @param createDTO Datos del café a crear
     * @return Café creado
     * @throws BadRequestException si ya existe un café con ese nombre
     */
    @Transactional
    public CoffeeResponseDTO create(CreateCoffeeRequestDTO createDTO) {
        if (coffeeRepository.existsByName(createDTO.getName())) {
            throw new BadRequestException("Ya existe un café con ese nombre");
        }

        Coffee coffee = new Coffee(
                createDTO.getName(),
                createDTO.getDescription(),
                createDTO.getPrice(),
                createDTO.getOrigin());

        return new CoffeeResponseDTO(coffeeRepository.save(coffee));
    }

    /**
     * Actualiza un café existente.
     * 
     * @param id        ID del café a actualizar
     * @param updateDTO Nuevos datos del café
     * @return Café actualizado
     * @throws ResourceNotFoundException si el café no existe
     * @throws BadRequestException       si el nuevo nombre ya está en uso
     */
    @Transactional
    public CoffeeResponseDTO update(Long id, UpdateCoffeeRequestDTO updateDTO) {
        Coffee coffee = coffeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Café", "id", id));

        // Verificar si el nuevo nombre ya está en uso por otro café
        if (!coffee.getName().equals(updateDTO.getName()) &&
                coffeeRepository.existsByName(updateDTO.getName())) {
            throw new BadRequestException("Ya existe un café con ese nombre");
        }

        coffee.setName(updateDTO.getName());
        coffee.setDescription(updateDTO.getDescription());
        coffee.setPrice(updateDTO.getPrice());
        coffee.setOrigin(updateDTO.getOrigin());

        return new CoffeeResponseDTO(coffeeRepository.save(coffee));
    }

    /**
     * Elimina un café.
     * 
     * @param id ID del café a eliminar
     * @throws ResourceNotFoundException si el café no existe
     */
    @Transactional
    public void delete(Long id) {
        if (!coffeeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Café", "id", id);
        }
        coffeeRepository.deleteById(id);
    }

    /**
     * Busca cafés por origen.
     * 
     * @param origin Origen del café
     * @return Lista de cafés con ese origen
     */
    public List<CoffeeResponseDTO> findByOrigin(String origin) {
        return coffeeRepository.findByOrigin(origin).stream()
                .map(CoffeeResponseDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Busca cafés por rango de precio.
     * 
     * @param minPrice Precio mínimo (inclusive)
     * @param maxPrice Precio máximo (inclusive)
     * @return Lista de cafés dentro del rango de precio
     */
    public List<CoffeeResponseDTO> findByPriceRange(Double minPrice, Double maxPrice) {
        return coffeeRepository.findByPriceBetween(minPrice, maxPrice).stream()
                .map(CoffeeResponseDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene estadísticas de los cafés.
     * 
     * @return Mapa con estadísticas (total, precio promedio, orígenes)
     */
    public Map<String, Object> getStats() {
        Map<String, Object> stats = new HashMap<>();
        List<Coffee> coffees = coffeeRepository.findAll();

        stats.put("totalCafes", coffees.size());
        stats.put("precioPromedio", coffees.stream()
                .mapToDouble(Coffee::getPrice)
                .average()
                .orElse(0.0));
        stats.put("origenes", coffees.stream()
                .collect(Collectors.groupingBy(
                        Coffee::getOrigin,
                        Collectors.counting())));

        return stats;
    }
}