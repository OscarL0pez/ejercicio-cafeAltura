package com.cafeteria.cafedealtura.controller;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cafeteria.cafedealtura.model.Cafe;
import com.cafeteria.cafedealtura.repository.CafeRepository;
import com.cafeteria.cafedealtura.dto.CafeUpdateDTO;
import com.cafeteria.cafedealtura.dto.PaginatedResponse;
import com.cafeteria.cafedealtura.service.CafeService;

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

    private final CafeService cafeService;

    public CafeController(CafeService cafeService) {
        this.cafeService = cafeService;
    }

    /**
     * Obtiene todos los cafés disponibles con paginación.
     * 
     * @param pageable Parámetros de paginación (page, size)
     * @return Lista paginada de cafés con metadatos
     */
    @GetMapping
    public ResponseEntity<PaginatedResponse<Cafe>> getAllCafes(
            @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(new PaginatedResponse<>(cafeService.obtenerTodos(pageable)));
    }

    /**
     * Obtiene un café específico por su ID.
     * 
     * @param id ID del café a buscar
     * @return Café encontrado con estado 200 OK, o 404 Not Found si no existe
     */
    @GetMapping("/{id}")
    public ResponseEntity<Cafe> getCafeById(@PathVariable Long id) {
        return cafeService.findById(id)
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
        Cafe savedCafe = cafeService.save(cafe);
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
        if (!cafeService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        cafe.setId(id);
        Cafe updatedCafe = cafeService.save(cafe);
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
        if (!cafeService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        cafeService.deleteById(id);
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
        return cafeService.findById(id)
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

                    Cafe updatedCafe = cafeService.save(existingCafe);
                    return ResponseEntity.ok(updatedCafe);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
