package com.cafeteria.cafedealtura.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * Entidad que representa un pedido en el sistema.
 * Esta clase mapea la tabla 'orders' en la base de datos.
 * 
 * Características principales:
 * - Relación Many-to-One con Customer (un cliente puede tener muchos pedidos)
 * - Relación One-to-Many con OrderItem (un pedido puede tener muchos items)
 * - Cálculo automático del total
 * - Fecha de creación automática
 */
@Entity
@Table(name = "orders")
public class Order {
    /**
     * Identificador único del pedido.
     * Se genera automáticamente al crear un nuevo registro.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Cliente que realiza el pedido.
     * Relación Many-to-One: muchos pedidos pueden pertenecer a un mismo cliente.
     */
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    /**
     * Fecha y hora en que se realizó el pedido.
     * Se establece automáticamente al crear el pedido.
     */
    private LocalDateTime fecha;

    /**
     * Lista de items (cafés) en el pedido.
     * Relación One-to-Many: un pedido puede tener varios items.
     * CascadeType.ALL: las operaciones se propagan a los items.
     * orphanRemoval: los items sin pedido se eliminan automáticamente.
     */
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items;

    /**
     * Total del pedido.
     * Se calcula automáticamente sumando los subtotales de los items.
     */
    private double total;

    public Order() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
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
