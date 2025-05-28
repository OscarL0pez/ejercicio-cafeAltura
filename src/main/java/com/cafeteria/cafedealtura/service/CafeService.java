package com.cafeteria.cafedealtura.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cafeteria.cafedealtura.model.Cafe;
import com.cafeteria.cafedealtura.repository.CafeRepository;
import com.cafeteria.cafedealtura.exception.ResourceNotFoundException;

@Service
public class CafeService {

    private final CafeRepository cafeRepository;

    public CafeService(CafeRepository cafeRepository) {
        this.cafeRepository = cafeRepository;
    }

    public Page<Cafe> obtenerTodos(Pageable pageable) {
        return cafeRepository.findAll(pageable);
    }

    public Optional<Cafe> findById(Long id) {
        return cafeRepository.findById(id);
    }

    public Cafe save(Cafe cafe) {
        return cafeRepository.save(cafe);
    }

    public boolean existsById(Long id) {
        return cafeRepository.existsById(id);
    }

    public void deleteById(Long id) {
        cafeRepository.deleteById(id);
    }

    public Cafe crear(Cafe cafe) {
        return cafeRepository.save(cafe);
    }

    public Cafe reemplazar(Long id, Cafe nuevoCafe) {
        nuevoCafe.setId(id);
        return cafeRepository.save(nuevoCafe);
    }

    public Cafe actualizarParcial(Long id, Cafe cafeParcial) {
        Cafe existente = cafeRepository.findById(id).orElseThrow();
        if (cafeParcial.getNombre() != null)
            existente.setNombre(cafeParcial.getNombre());
        if (cafeParcial.getDescripcion() != null)
            existente.setDescripcion(cafeParcial.getDescripcion());
        if (cafeParcial.getPrecio() != 0)
            existente.setPrecio(cafeParcial.getPrecio());
        if (cafeParcial.getOrigen() != null)
            existente.setOrigen(cafeParcial.getOrigen());
        return cafeRepository.save(existente);
    }

    public boolean eliminar(Long id) {
        cafeRepository.deleteById(id);
        return true;
    }

    public Cafe getCafeById(Long id) {
        return cafeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Café", "id", id));
    }
}
