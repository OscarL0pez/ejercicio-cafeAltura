package com.cafeteria.cafedealtura.domain.coffee.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import com.cafeteria.cafedealtura.common.utils.ValidationUtils;

/**
 * DTO para la creación de un nuevo café.
 * Incluye validaciones para todos los campos requeridos.
 */
public class CreateCoffeeRequestDTO {
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    private String name;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(min = 10, message = "La descripción debe tener al menos 10 caracteres")
    private String description;

    @NotNull(message = "El precio es obligatorio")
    @Positive(message = "El precio debe ser mayor que 0")
    private Double price;

    @NotBlank(message = "El origen es obligatorio")
    @Size(min = 2, max = 100, message = "El origen debe tener entre 2 y 100 caracteres")
    private String origin;

    public CreateCoffeeRequestDTO() {
    }

    public CreateCoffeeRequestDTO(String name, String description, Double price, String origin) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.origin = origin;
    }

    // Getters y setters con validación
    public String getName() {
        return name;
    }

    public void setName(String name) {
        ValidationUtils.validateNotEmpty(name, "nombre");
        ValidationUtils.validateRange(name.length(), 3, 100, "nombre");
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        ValidationUtils.validateNotEmpty(description, "descripción");
        ValidationUtils.validateMinLength(description.length(), 10, "descripción");
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        ValidationUtils.validatePositive(price, "precio");
        this.price = price;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        ValidationUtils.validateNotEmpty(origin, "origen");
        ValidationUtils.validateRange(origin.length(), 2, 100, "origen");
        this.origin = origin;
    }
}