package com.cafeteria.cafedealtura.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

/**
 * Entidad que representa un cliente en el sistema.
 * Esta clase mapea la tabla 'customers' en la base de datos.
 * 
 * Características principales:
 * - ID autoincremental
 * - Nombre obligatorio
 * - Relación One-to-Many con Order (un cliente puede tener muchos pedidos)
 */
@Entity
@Table(name = "customers")
public class Customer {
    /**
     * Identificador único del cliente.
     * Se genera automáticamente al crear un nuevo registro.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Nombre del cliente.
     * No puede estar vacío.
     */
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    public Customer() {
    }

    public Customer(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
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
}
