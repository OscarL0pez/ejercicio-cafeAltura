package com.cafeteria.cafedealtura.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.cafeteria.cafedealtura.model.Order;
import com.cafeteria.cafedealtura.model.OrderItem;

public class OrderResponseDTO {
    private Long id;
    private UserSummaryDTO user;
    private List<OrderItemResponseDTO> items;
    private double total;
    private LocalDateTime date;

    public OrderResponseDTO() {
    }

    public OrderResponseDTO(Order order) {
        this.id = order.getId();
        this.user = new UserSummaryDTO(order.getUser());
        this.items = order.getItems().stream()
                .map(OrderItemResponseDTO::new)
                .collect(Collectors.toList());
        this.total = order.getTotal();
        this.date = order.getDate();
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserSummaryDTO getUser() {
        return user;
    }

    public void setUser(UserSummaryDTO user) {
        this.user = user;
    }

    public List<OrderItemResponseDTO> getItems() {
        return items;
    }

    public void setItems(List<OrderItemResponseDTO> items) {
        this.items = items;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    // Clase interna para User
    public static class UserSummaryDTO {
        private Long id;
        private String name;

        public UserSummaryDTO() {
        }

        public UserSummaryDTO(com.cafeteria.cafedealtura.model.User user) {
            this.id = user.getId();
            this.name = user.getName();
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    // Clase interna para OrderItem
    public static class OrderItemResponseDTO {
        private Long id;
        private String name;
        private double price;
        private int quantity;
        private double subtotal;

        public OrderItemResponseDTO() {
        }

        public OrderItemResponseDTO(OrderItem item) {
            this.id = item.getId();
            this.name = item.getName();
            this.price = item.getPrice();
            this.quantity = item.getQuantity();
            this.subtotal = item.getSubtotal();
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public double getSubtotal() {
            return subtotal;
        }

        public void setSubtotal(double subtotal) {
            this.subtotal = subtotal;
        }
    }
}