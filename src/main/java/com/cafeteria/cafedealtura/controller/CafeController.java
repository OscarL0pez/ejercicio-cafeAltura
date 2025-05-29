package com.cafeteria.cafedealtura.controller;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cafeteria.cafedealtura.domain.coffee.dto.request.CreateCoffeeRequestDTO;
import com.cafeteria.cafedealtura.domain.coffee.dto.request.UpdateCoffeeRequestDTO;
import com.cafeteria.cafedealtura.domain.coffee.dto.response.CoffeeResponseDTO;
import com.cafeteria.cafedealtura.domain.coffee.service.CoffeeService;
import com.cafeteria.cafedealtura.security.annotation.RequireRole;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Controlador para la gestión de cafés.
 */
@RestController
@RequestMapping("/api/coffees")
@CrossOrigin(origins = "*")
public class CafeController {

    private final CoffeeService coffeeService;

    @Autowired
    public CafeController(CoffeeService coffeeService) {
        this.coffeeService = coffeeService;
    }

    /**
     * Obtiene todos los cafés con paginación.
     * 
     * @param pageable Configuración de paginación
     * @return Lista de cafés
     */
    @GetMapping
    public ResponseEntity<List<CoffeeResponseDTO>> getAllCoffees(@PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(coffeeService.findAll(pageable));
    }

    /**
     * Busca un café por su ID.
     * 
     * @param id ID del café
     * @return Café encontrado
     */
    @GetMapping("/{id}")
    public ResponseEntity<CoffeeResponseDTO> getCoffeeById(@PathVariable Long id) {
        return ResponseEntity.ok(coffeeService.findById(id));
    }

    /**
     * Crea un nuevo café.
     * Requiere rol ADMIN.
     * 
     * @param createDTO Datos del café a crear
     * @return Café creado
     */
    @PostMapping
    @RequireRole("ADMIN")
    public ResponseEntity<CoffeeResponseDTO> createCoffee(@Valid @RequestBody CreateCoffeeRequestDTO createDTO) {
        return ResponseEntity.ok(coffeeService.create(createDTO));
    }

    /**
     * Actualiza un café existente.
     * Requiere rol ADMIN.
     * 
     * @param id        ID del café a actualizar
     * @param updateDTO Nuevos datos del café
     * @return Café actualizado
     */
    @PutMapping("/{id}")
    @RequireRole("ADMIN")
    public ResponseEntity<CoffeeResponseDTO> updateCoffee(
            @PathVariable Long id,
            @Valid @RequestBody UpdateCoffeeRequestDTO updateDTO) {
        return ResponseEntity.ok(coffeeService.update(id, updateDTO));
    }

    /**
     * Elimina un café.
     * Requiere rol ADMIN.
     * 
     * @param id ID del café a eliminar
     */
    @DeleteMapping("/{id}")
    @RequireRole("ADMIN")
    public ResponseEntity<Void> deleteCoffee(@PathVariable Long id) {
        coffeeService.delete(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Obtiene estadísticas de los cafés.
     * 
     * @return Estadísticas de cafés
     */
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getStats() {
        return ResponseEntity.ok(coffeeService.getStats());
    }
}
