package com.cafeteria.cafedealtura.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cafeteria.cafedealtura.model.Cafe;
import com.cafeteria.cafedealtura.repository.CafeRepository;
import com.cafeteria.cafedealtura.dto.CafeUpdateDTO;

import jakarta.validation.Valid;

/**
 * Controlador REST para la gestión de cafés.
 * Proporciona endpoints para realizar operaciones CRUD sobre los cafés.
 * 
 * Endpoints disponibles:
 * - GET /api/cafes - Listar todos los cafés
 * - GET /api/cafes/{id} - Obtener un café por ID
 * - POST /api/cafes - Crear un nuevo café
 * - PUT /api/cafes/{id} - Actualizar un café completo
 * - PATCH /api/cafes/{id} - Actualizar parcialmente un café
 * - DELETE /api/cafes/{id} - Eliminar un café
 */
@RestController
@RequestMapping("/api/cafes")
public class CafeController {

    private final CafeRepository cafeRepository;

    public CafeController(CafeRepository cafeRepository) {
        this.cafeRepository = cafeRepository;
    }

    /**
     * Obtiene todos los cafés disponibles.
     * 
     * @return Lista de todos los cafés con estado 200 OK
     */
    @GetMapping
    public ResponseEntity<List<Cafe>> getAllCafes() {
        List<Cafe> cafes = cafeRepository.findAll();
        return ResponseEntity.ok(cafes);
    }

    /**
     * Obtiene un café específico por su ID.
     * 
     * @param id ID del café a buscar
     * @return Café encontrado con estado 200 OK, o 404 Not Found si no existe
     */
    @GetMapping("/{id}")
    public ResponseEntity<Cafe> getCafeById(@PathVariable Long id) {
        return cafeRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Crea un nuevo café.
     * 
     * @param cafe Datos del café a crear (validados con @Valid)
     * @return Café creado con estado 201 Created
     */
    @PostMapping
    public ResponseEntity<Cafe> createCafe(@Valid @RequestBody Cafe cafe) {
        Cafe savedCafe = cafeRepository.save(cafe);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCafe);
    }

    /**
     * Actualiza un café existente completamente.
     * 
     * @param id   ID del café a actualizar
     * @param cafe Nuevos datos del café (validados con @Valid)
     * @return Café actualizado con estado 200 OK, o 404 Not Found si no existe
     */
    @PutMapping("/{id}")
    public ResponseEntity<Cafe> updateCafe(@PathVariable Long id, @Valid @RequestBody Cafe cafe) {
        if (!cafeRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        cafe.setId(id);
        Cafe updatedCafe = cafeRepository.save(cafe);
        return ResponseEntity.ok(updatedCafe);
    }

    /**
     * Elimina un café existente.
     * 
     * @param id ID del café a eliminar
     * @return 204 No Content si se eliminó correctamente, o 404 Not Found si no
     *         existe
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCafe(@PathVariable Long id) {
        if (!cafeRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        cafeRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Actualiza parcialmente un café existente.
     * Solo se actualizan los campos que no son null en el DTO.
     * 
     * @param id         ID del café a actualizar
     * @param cafeUpdate DTO con los campos a actualizar (validados con @Valid)
     * @return Café actualizado con estado 200 OK, o 404 Not Found si no existe
     */
    @PatchMapping("/{id}")
    public ResponseEntity<Cafe> partialUpdateCafe(
            @PathVariable Long id,
            @Valid @RequestBody CafeUpdateDTO cafeUpdate) {
        return cafeRepository.findById(id)
                .map(existingCafe -> {
                    // Actualizar solo los campos que no son null
                    if (cafeUpdate.getNombre() != null) {
                        existingCafe.setNombre(cafeUpdate.getNombre());
                    }
                    if (cafeUpdate.getDescripcion() != null) {
                        existingCafe.setDescripcion(cafeUpdate.getDescripcion());
                    }
                    if (cafeUpdate.getPrecio() != null) {
                        existingCafe.setPrecio(cafeUpdate.getPrecio());
                    }
                    if (cafeUpdate.getOrigen() != null) {
                        existingCafe.setOrigen(cafeUpdate.getOrigen());
                    }

                    Cafe updatedCafe = cafeRepository.save(existingCafe);
                    return ResponseEntity.ok(updatedCafe);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
