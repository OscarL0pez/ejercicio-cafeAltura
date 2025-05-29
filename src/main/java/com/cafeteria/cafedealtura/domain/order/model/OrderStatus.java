package com.cafeteria.cafedealtura.domain.order.model;

/**
 * Enum que representa los posibles estados de un pedido.
 */
public enum OrderStatus {
    PENDING("Pendiente"), // Pedido creado pero no confirmado
    CONFIRMED("Confirmado"), // Pedido confirmado
    PREPARING("En preparación"), // Pedido en preparación
    READY("Listo"), // Pedido listo para entrega
    DELIVERED("Entregado"), // Pedido entregado
    CANCELLED("Cancelado"); // Pedido cancelado

    private final String description;

    OrderStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}