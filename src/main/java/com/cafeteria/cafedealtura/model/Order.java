package com.cafeteria.cafedealtura.model;

import java.time.LocalDateTime;
import java.util.List;

public class Order {
    private Long id;
    private Long idCliente;
    private String nombreCliente;
    private LocalDateTime fecha;
    private List<OrderItem> items;
    private double total;

    public Order() {}

    public Order(Long id, Long idCliente, String nombreCliente, LocalDateTime fecha, List<OrderItem> items, double total) {
        this.id = id;
        this.idCliente = idCliente;
        this.nombreCliente = nombreCliente;
        this.fecha = fecha;
        this.items = items;
        this.total = total;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
