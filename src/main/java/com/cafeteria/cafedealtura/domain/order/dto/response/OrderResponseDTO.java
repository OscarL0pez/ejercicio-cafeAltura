package com.cafeteria.cafedealtura.domain.order.dto.response;

import com.cafeteria.cafedealtura.domain.order.model.Order;
import com.cafeteria.cafedealtura.domain.order.model.OrderItem;
import com.cafeteria.cafedealtura.domain.user.dto.response.UserSummaryDTO;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * DTO para representar un pedido en las respuestas de la API.
 * Incluye información detallada del pedido, usuario y items.
 */
public class OrderResponseDTO {
    private final Long id;
    private final UserSummaryDTO user;
    private final List<OrderItemResponseDTO> items;
    private final Double total;
    private final LocalDateTime date;
    private final String status;

    public OrderResponseDTO(Order order) {
        this.id = order.getId();
        this.user = new UserSummaryDTO(order.getUser());
        this.items = order.getItems().stream()
                .map(OrderItemResponseDTO::new)
                .collect(Collectors.toList());
        this.total = order.getTotal();
        this.date = order.getDate();
        this.status = order.getStatus().getDescription();
    }

    // Getters
    public Long getId() {
        return id;
    }

    public UserSummaryDTO getUser() {
        return user;
    }

    public List<OrderItemResponseDTO> getItems() {
        return items;
    }

    public Double getTotal() {
        return total;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }
}

/**
 * DTO para representar un item de pedido en las respuestas de la API.
 */
class OrderItemResponseDTO {
    private final Long id;
    private final String name;
    private final Double price;
    private final Integer quantity;
    private final Double subtotal;

    public OrderItemResponseDTO(OrderItem item) {
        this.id = item.getId();
        this.name = item.getName();
        this.price = item.getPrice();
        this.quantity = item.getQuantity();
        this.subtotal = item.getSubtotal();
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Double getSubtotal() {
        return subtotal;
    }
}