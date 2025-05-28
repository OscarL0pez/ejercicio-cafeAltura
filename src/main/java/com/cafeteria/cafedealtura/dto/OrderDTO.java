package com.cafeteria.cafedealtura.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public class OrderDTO {
    @NotNull(message = "El ID del usuario es obligatorio")
    private Long userId;

    @NotEmpty(message = "Debe incluir al menos un item en el pedido")
    private List<OrderItemDTO> items;

    public OrderDTO() {
    }

    public OrderDTO(Long userId, List<OrderItemDTO> items) {
        this.userId = userId;
        this.items = items;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<OrderItemDTO> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDTO> items) {
        this.items = items;
    }

    public static class OrderItemDTO {
        @NotNull(message = "El ID del café es obligatorio")
        private Long cafeId;

        @NotNull(message = "La cantidad es obligatoria")
        private Integer quantity;

        public OrderItemDTO() {
        }

        public OrderItemDTO(Long cafeId, Integer quantity) {
            this.cafeId = cafeId;
            this.quantity = quantity;
        }

        public Long getCafeId() {
            return cafeId;
        }

        public void setCafeId(Long cafeId) {
            this.cafeId = cafeId;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }
    }
}