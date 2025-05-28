package com.cafeteria.cafedealtura.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.cafeteria.cafedealtura.model.Order;
import com.cafeteria.cafedealtura.model.OrderItem;

public class OrderResponseDTO {
    private Long id;
    private CustomerDTO customer;
    private LocalDateTime fecha;
    private List<OrderItemResponseDTO> items;
    private double total;

    public OrderResponseDTO() {
    }

    public OrderResponseDTO(Order order) {
        this.id = order.getId();
        this.customer = new CustomerDTO(order.getCustomer());
        this.fecha = order.getFecha();
        this.items = order.getItems().stream()
                .map(OrderItemResponseDTO::new)
                .collect(Collectors.toList());
        this.total = order.getTotal();
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CustomerDTO getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDTO customer) {
        this.customer = customer;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
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

    // Clase interna para Customer
    public static class CustomerDTO {
        private Long id;
        private String nombre;

        public CustomerDTO() {
        }

        public CustomerDTO(com.cafeteria.cafedealtura.model.Customer customer) {
            this.id = customer.getId();
            this.nombre = customer.getNombre();
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

    // Clase interna para OrderItem
    public static class OrderItemResponseDTO {
        private Long id;
        private String nombre;
        private double precio;
        private int cantidad;
        private double subtotal;

        public OrderItemResponseDTO() {
        }

        public OrderItemResponseDTO(OrderItem item) {
            this.id = item.getId();
            this.nombre = item.getNombre();
            this.precio = item.getPrecio();
            this.cantidad = item.getCantidad();
            this.subtotal = item.getSubtotal();
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

        public double getSubtotal() {
            return subtotal;
        }

        public void setSubtotal(double subtotal) {
            this.subtotal = subtotal;
        }
    }
}