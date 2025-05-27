package com.cafeteria.cafedealtura.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * Entidad que representa un café en el sistema.
 * Esta clase mapea la tabla 'cafes' en la base de datos.
 * 
 * Características principales:
 * - ID autoincremental
 * - Validaciones de campos obligatorios
 * - Precio debe ser positivo
 */
@Entity
@Table(name = "cafes")
public class Cafe {
    /**
     * Identificador único del café.
     * Se genera automáticamente al crear un nuevo registro.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Nombre del café.
     * No puede estar vacío.
     */
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    /**
     * Descripción detallada del café.
     * No puede estar vacía.
     */
    @NotBlank(message = "La descripción es obligatoria")
    private String descripcion;

    /**
     * Precio del café.
     * Debe ser un valor positivo mayor que cero.
     */
    @NotNull(message = "El precio es obligatorio")
    @Positive(message = "El precio debe ser mayor que 0")
    private double precio;

    /**
     * País o región de origen del café.
     * No puede estar vacío.
     */
    @NotBlank(message = "El origen es obligatorio")
    private String origen;

    public Cafe() {
    }

    public Cafe(Long id, String nombre, String descripcion, double precio, String origen) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.origen = origen;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }
}
