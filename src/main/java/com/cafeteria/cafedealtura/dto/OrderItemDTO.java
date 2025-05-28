package com.cafeteria.cafedealtura.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class OrderItemDTO {
    @NotNull(message = "El ID del café es obligatorio")
    private Long cafeId;
    private String nombre; // (opcional, se puede usar el nombre del café existente)
    private double precio; // (opcional, se puede usar el precio del café existente)
    @Min(value = 1, message = "La cantidad debe ser mayor o igual a 1")
    private int cantidad;

    public OrderItemDTO() {
    }

    public Long getCafeId() {
        return cafeId;
    }

    public void setCafeId(Long cafeId) {
        this.cafeId = cafeId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}