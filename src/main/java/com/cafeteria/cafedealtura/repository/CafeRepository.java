package com.cafeteria.cafedealtura.repository;

import com.cafeteria.cafedealtura.model.Cafe;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class CafeRepository {

    private final Map<Long, Cafe> cafes = new HashMap<>();
    private Long nextId = 1L;

    public List<Cafe> findAll() {
        return new ArrayList<>(cafes.values());
    }

    public Optional<Cafe> findById(Long id) {
        return Optional.ofNullable(cafes.get(id));
    }

    public Cafe save(Cafe cafe) {
        if (cafe.getId() == null) {
            cafe.setId(nextId++);
        }
        cafes.put(cafe.getId(), cafe);
        return cafe;
    }

    public boolean existsById(Long id) {
        return cafes.containsKey(id);
    }

    public boolean deleteById(Long id) {
        return cafes.remove(id) != null;
    }
}
