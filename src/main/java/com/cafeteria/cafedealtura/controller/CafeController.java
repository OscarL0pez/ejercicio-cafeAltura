package com.cafeteria.cafedealtura.controller;

import java.util.List;
import java.util.Optional;
import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cafeteria.cafedealtura.model.Cafe;
import com.cafeteria.cafedealtura.repository.CafeRepository;
import com.cafeteria.cafedealtura.dto.CafeDTO;
import com.cafeteria.cafedealtura.dto.CafeCreateDTO;
import com.cafeteria.cafedealtura.dto.CafeUpdateDTO;
import com.cafeteria.cafedealtura.dto.PaginatedResponse;
import com.cafeteria.cafedealtura.service.CafeService;
import com.cafeteria.cafedealtura.security.annotation.RequireRole;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Controlador REST para la gestión de cafés.
 * Proporciona endpoints para realizar operaciones CRUD sobre los cafés,
 * incluyendo soporte para paginación en los endpoints de listado.
 * 
 * Endpoints disponibles:
 * - GET /api/cafes - Listar todos los cafés (paginado)
 * Parámetros de paginación:
 * - page: número de página (0-based, default: 0)
 * - size: tamaño de página (default: 10)
 * Ejemplo: GET /api/cafes?page=0&size=5
 * 
 * - GET /api/cafes/{id} - Obtener un café por ID
 * - POST /api/cafes - Crear un nuevo café
 * - PUT /api/cafes/{id} - Actualizar un café completo
 * - DELETE /api/cafes/{id} - Eliminar un café
 * 
 * Todas las respuestas de listado (GET /api/cafes) incluyen metadatos de
 * paginación:
 * - content: Lista de cafés en la página actual
 * - currentPage: Número de página actual
 * - totalPages: Total de páginas disponibles
 * - totalElements: Total de cafés
 * - pageSize: Tamaño de página
 * - hasNext: Indica si hay página siguiente
 * - hasPrevious: Indica si hay página anterior
 */
@RestController
@RequestMapping("/api/cafes")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CafeController {

    @Autowired
    private CafeService cafeService;

    /**
     * Obtiene todos los cafés disponibles con paginación.
     * 
     * @param pageable Parámetros de paginación (page, size)
     *                 - page: número de página (0-based, default: 0)
     *                 - size: tamaño de página (default: 10)
     * @return ResponseEntity con PaginatedResponse que contiene:
     *         - Lista de cafés en la página actual
     *         - Metadatos de paginación (currentPage, totalPages, etc.)
     * 
     *         Ejemplo de uso:
     *         GET /api/cafes?page=0&size=5
     * 
     *         Ejemplo de respuesta:
     *         {
     *         "content": [...],
     *         "currentPage": 0,
     *         "totalPages": 3,
     *         "totalElements": 12,
     *         "pageSize": 5,
     *         "hasNext": true,
     *         "hasPrevious": false
     *         }
     */
    @GetMapping
    public ResponseEntity<List<CafeDTO>> getAllCafes() {
        return ResponseEntity.ok(cafeService.findAll());
    }

    /**
     * Obtiene un café específico por su ID.
     * 
     * @param id ID del café a buscar
     * @return Café encontrado con estado 200 OK, o 404 Not Found si no existe
     */
    @GetMapping("/{id}")
    public ResponseEntity<CafeDTO> getCafeById(@PathVariable Long id) {
        return ResponseEntity.ok(cafeService.findById(id));
    }

    /**
     * Crea un nuevo café.
     * 
     * @param cafe Datos del café a crear (validados con @Valid)
     * @return Café creado con estado 201 Created
     */
    @RequireRole("ADMIN")
    @PostMapping
    public ResponseEntity<CafeDTO> createCafe(@Valid @RequestBody CafeDTO cafeDTO) {
        return ResponseEntity.ok(cafeService.create(cafeDTO));
    }

    /**
     * Actualiza un café existente completamente.
     * 
     * @param id   ID del café a actualizar
     * @param cafe Nuevos datos del café (validados con @Valid)
     * @return Café actualizado con estado 200 OK, o 404 Not Found si no existe
     */
    @RequireRole("ADMIN")
    @PutMapping("/{id}")
    public ResponseEntity<CafeDTO> updateCafe(@PathVariable Long id, @Valid @RequestBody CafeDTO cafeDTO) {
        return ResponseEntity.ok(cafeService.update(id, cafeDTO));
    }

    /**
     * Elimina un café existente.
     * 
     * @param id ID del café a eliminar
     * @return 204 No Content si se eliminó correctamente, o 404 Not Found si no
     *         existe
     */
    @RequireRole("ADMIN")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCafe(@PathVariable Long id) {
        cafeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // ADMIN y MANAGER pueden ver estadísticas de ventas
    @RequireRole({ "ADMIN", "MANAGER" })
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getCafeStats() {
        return ResponseEntity.ok(cafeService.getStats());
    }

    // ADMIN y MANAGER pueden actualizar el stock
    @RequireRole({ "ADMIN", "MANAGER" })
    @PatchMapping("/{id}/stock")
    public ResponseEntity<CafeDTO> updateStock(@PathVariable Long id, @RequestParam int quantity) {
        return ResponseEntity.ok(cafeService.updateStock(id, quantity));
    }
}
