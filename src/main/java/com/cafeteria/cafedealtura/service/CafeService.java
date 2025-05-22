package com.cafeteria.cafedealtura.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cafeteria.cafedealtura.model.Cafe;
import com.cafeteria.cafedealtura.repository.CafeRepository;

@Service
public class CafeService {

    private final CafeRepository cafeRepository;

    public CafeService(CafeRepository cafeRepository) {
        this.cafeRepository = cafeRepository;
    }

    public List<Cafe> obtenerTodos() {
        return cafeRepository.findAll();
    }

    public Optional<Cafe> obtenerPorId(Long id) {
        return cafeRepository.findById(id);
    }

    public Cafe crear(Cafe cafe) {
        return cafeRepository.save(cafe);
    }

    public boolean existePorId(Long id) {
        return cafeRepository.existsById(id);
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
        return cafeRepository.deleteById(id);
    }
}
