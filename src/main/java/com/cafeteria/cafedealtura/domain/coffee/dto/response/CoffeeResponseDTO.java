package com.cafeteria.cafedealtura.domain.coffee.dto.response;

import com.cafeteria.cafedealtura.common.utils.ValidationUtils;

/**
 * DTO para representar un café en las respuestas de la API.
 * Incluye todos los campos del modelo Coffee.
 */
public class CoffeeResponseDTO {
    private final Long id;
    private final String name;
    private final String description;
    private final Double price;
    private final String origin;

    public CoffeeResponseDTO(Long id, String name, String description, Double price, String origin) {
        ValidationUtils.validatePositive(id, "id");
        ValidationUtils.validateNotEmpty(name, "nombre");
        ValidationUtils.validateNotEmpty(description, "descripción");
        ValidationUtils.validatePositive(price, "precio");
        ValidationUtils.validateNotEmpty(origin, "origen");

        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.origin = origin;
    }

    public CoffeeResponseDTO(com.cafeteria.cafedealtura.domain.coffee.model.Coffee coffee) {
        this(
                coffee.getId(),
                coffee.getName(),
                coffee.getDescription(),
                coffee.getPrice(),
                coffee.getOrigin());
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Double getPrice() {
        return price;
    }

    public String getOrigin() {
        return origin;
    }
}