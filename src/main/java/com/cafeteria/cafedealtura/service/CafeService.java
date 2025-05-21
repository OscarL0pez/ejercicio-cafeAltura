package com.cafeteria.cafedealtura.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cafeteria.cafedealtura.model.Cafe;

@Service
public class CafeService {

    private final Map<Long, Cafe> cafes = new HashMap<>();
    private Long contadorId = 1L;

    public List<Cafe> obtenerTodos() {
        return new ArrayList<>(cafes.values());
    }

    public Optional<Cafe> obtenerPorId(Long id) {
        return Optional.ofNullable(cafes.get(id));
    }

    public Cafe crear(Cafe cafe) {
        cafe.setId(contadorId++);
        cafes.put(cafe.getId(), cafe);
        return cafe;
    }

    public boolean existePorId(Long id) {
        return cafes.containsKey(id);
    }

    public Cafe reemplazar(Long id, Cafe nuevoCafe) {
        nuevoCafe.setId(id);
        cafes.put(id, nuevoCafe);
        return nuevoCafe;
    }

    public Cafe actualizarParcial(Long id, Cafe cafeParcial) {
        Cafe existente = cafes.get(id);
        if (cafeParcial.getNombre() != null)
            existente.setNombre(cafeParcial.getNombre());
        if (cafeParcial.getDescripcion() != null)
            existente.setDescripcion(cafeParcial.getDescripcion());
        if (cafeParcial.getPrecio() != 0)
            existente.setPrecio(cafeParcial.getPrecio());
        if (cafeParcial.getOrigen() != null)
            existente.setOrigen(cafeParcial.getOrigen());
        return existente;
    }

    public boolean eliminar(Long id) {
        return cafes.remove(id) != null;
    }
}
