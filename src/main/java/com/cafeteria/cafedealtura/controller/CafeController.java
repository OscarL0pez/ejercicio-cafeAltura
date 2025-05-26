package com.cafeteria.cafedealtura.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cafeteria.cafedealtura.model.Cafe;
import com.cafeteria.cafedealtura.repository.CafeRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/cafes")
public class CafeController {

    private final CafeRepository cafeRepository;

    public CafeController(CafeRepository cafeRepository) {
        this.cafeRepository = cafeRepository;
    }

    @GetMapping
    public ResponseEntity<List<Cafe>> getAllCafes() {
        List<Cafe> cafes = cafeRepository.findAll();
        return ResponseEntity.ok(cafes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cafe> getCafeById(@PathVariable Long id) {
        return cafeRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Cafe> createCafe(@Valid @RequestBody Cafe cafe) {
        Cafe savedCafe = cafeRepository.save(cafe);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCafe);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cafe> updateCafe(@PathVariable Long id, @Valid @RequestBody Cafe cafe) {
        if (!cafeRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        cafe.setId(id);
        Cafe updatedCafe = cafeRepository.save(cafe);
        return ResponseEntity.ok(updatedCafe);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCafe(@PathVariable Long id) {
        if (!cafeRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        cafeRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
