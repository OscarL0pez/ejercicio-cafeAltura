package com.cafeteria.cafedealtura.domain.order.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

/**
 * DTO para la actualización de un pedido existente.
 * Similar a CreateOrderRequestDTO pero usado específicamente para
 * actualizaciones.
 */
public class UpdateOrderRequestDTO {
    @NotNull(message = "El ID del usuario es obligatorio")
    private Long userId;

    @NotEmpty(message = "Debe incluir al menos un item en el pedido")
    @Valid
    private List<OrderItemRequestDTO> items;

    @NotNull(message = "El estado del pedido es obligatorio")
    private String status;

    public UpdateOrderRequestDTO() {
    }

    public UpdateOrderRequestDTO(Long userId, List<OrderItemRequestDTO> items, String status) {
        this.userId = userId;
        this.items = items;
        this.status = status;
    }

    // Getters y setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<OrderItemRequestDTO> getItems() {
        return items;
    }

    public void setItems(List<OrderItemRequestDTO> items) {
        this.items = items;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * DTO para representar un item de pedido en las solicitudes de actualización.
     */
    public static class OrderItemRequestDTO {
        @NotNull(message = "El ID del café es obligatorio")
        private Long coffeeId;

        @NotNull(message = "La cantidad es obligatoria")
        private Integer quantity;

        public OrderItemRequestDTO() {
        }

        public OrderItemRequestDTO(Long coffeeId, Integer quantity) {
            this.coffeeId = coffeeId;
            this.quantity = quantity;
        }

        // Getters y setters
        public Long getCoffeeId() {
            return coffeeId;
        }

        public void setCoffeeId(Long coffeeId) {
            this.coffeeId = coffeeId;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }
    }
}