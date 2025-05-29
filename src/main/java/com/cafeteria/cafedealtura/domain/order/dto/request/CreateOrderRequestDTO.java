package com.cafeteria.cafedealtura.domain.order.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

/**
 * DTO para la creación de un nuevo pedido.
 * Incluye validaciones para todos los campos requeridos.
 */
public class CreateOrderRequestDTO {
    @NotNull(message = "El ID del usuario es obligatorio")
    private Long userId;

    @NotEmpty(message = "Debe incluir al menos un item en el pedido")
    @Valid
    private List<OrderItemRequestDTO> items;

    public CreateOrderRequestDTO() {
    }

    public CreateOrderRequestDTO(Long userId, List<OrderItemRequestDTO> items) {
        this.userId = userId;
        this.items = items;
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

    /**
     * DTO para representar un item de pedido en las solicitudes.
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