package com.cafeteria.cafedealtura.domain.coffee.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import com.cafeteria.cafedealtura.common.utils.ValidationUtils;

/**
 * Entidad que representa un café en el sistema.
 * Esta clase mapea la tabla 'coffees' en la base de datos.
 * 
 * Características principales:
 * - ID autoincremental
 * - Validaciones de campos obligatorios
 * - Precio debe ser positivo
 * - Nombre y origen con longitud mínima y máxima
 * - Descripción con longitud mínima
 */
@Entity
@Table(name = "coffees", uniqueConstraints = {
        @UniqueConstraint(columnNames = "name")
})
public class Coffee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    @Column(nullable = false, unique = true)
    private String name;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(min = 10, message = "La descripción debe tener al menos 10 caracteres")
    @Column(nullable = false, length = 1000)
    private String description;

    @NotNull(message = "El precio es obligatorio")
    @Positive(message = "El precio debe ser mayor que 0")
    @Column(nullable = false)
    private Double price;

    @NotBlank(message = "El origen es obligatorio")
    @Size(min = 2, max = 100, message = "El origen debe tener entre 2 y 100 caracteres")
    @Column(nullable = false)
    private String origin;

    public Coffee() {
    }

    public Coffee(String name, String description, Double price, String origin) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.origin = origin;
    }

    // Getters y setters con validación
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        ValidationUtils.validatePositive(id, "id");
        this.id = id;
    }

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