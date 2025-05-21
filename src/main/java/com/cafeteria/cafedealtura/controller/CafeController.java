package com.cafeteria.cafedealtura.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cafeteria.cafedealtura.model.Cafe;
import com.cafeteria.cafedealtura.service.CafeService;

@RestController
@RequestMapping("/cafes")
public class CafeController {

    private final CafeService cafeService;

    public CafeController(CafeService cafeService) {
        this.cafeService = cafeService;
    }

    // GET /cafes
    @GetMapping
    public ResponseEntity<List<Cafe>> obtenerTodos() {
        return ResponseEntity.ok(cafeService.obtenerTodos());
    }

    // POST /cafes
    @PostMapping
    public ResponseEntity<?> crearCafe(@RequestBody Cafe cafe) {
        // Validaciones manuales
        if (cafe.getNombre() == null || cafe.getNombre().isEmpty()
                || cafe.getDescripcion() == null || cafe.getDescripcion().isEmpty()
                || cafe.getOrigen() == null || cafe.getOrigen().isEmpty()
                || cafe.getPrecio() <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Faltan atributos obligatorios o el precio no es válido.");
        }

        Cafe creado = cafeService.crear(cafe);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    // PUT /cafes/{id}
    @PutMapping("/{id}")
    public ResponseEntity<?> reemplazarCafe(@PathVariable Long id, @RequestBody Cafe cafe) {
        if (cafe.getNombre() == null || cafe.getNombre().isEmpty()
                || cafe.getDescripcion() == null || cafe.getDescripcion().isEmpty()
                || cafe.getOrigen() == null || cafe.getOrigen().isEmpty()
                || cafe.getPrecio() <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Faltan atributos obligatorios o el precio no es válido.");
        }

        if (!cafeService.existePorId(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Café no encontrado.");
        }

        Cafe actualizado = cafeService.reemplazar(id, cafe);
        return ResponseEntity.ok(actualizado);
    }

    // PATCH /cafes/{id}
    @PatchMapping("/{id}")
    public ResponseEntity<?> actualizarParcial(@PathVariable Long id, @RequestBody Cafe cafe) {
        if (!cafeService.existePorId(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Café no encontrado.");
        }

        Cafe actualizado = cafeService.actualizarParcial(id, cafe);
        return ResponseEntity.ok(actualizado);
    }

    // DELETE /cafes/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        if (!cafeService.existePorId(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Café no encontrado.");
        }

        cafeService.eliminar(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}
