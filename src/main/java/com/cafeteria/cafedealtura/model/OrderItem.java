package com.cafeteria.cafedealtura.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Entidad que representa un item individual dentro de un pedido.
 * Esta clase mapea la tabla 'order_items' en la base de datos.
 * 
 * Características principales:
 * - Relación Many-to-One con Order (muchos items pueden pertenecer a un pedido)
 * - Relación Many-to-One con Cafe (muchos items pueden referenciar al mismo
 * café)
 * - Cálculo automático del subtotal (precio * cantidad)
 */
@Entity
@Table(name = "order_items")
public class OrderItem {
    /**
     * Identificador único del item.
     * Se genera automáticamente al crear un nuevo registro.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Pedido al que pertenece este item.
     * Relación Many-to-One: muchos items pueden pertenecer a un mismo pedido.
     */
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    /**
     * Café asociado a este item.
     * Relación Many-to-One: muchos items pueden referenciar al mismo café.
     */
    @ManyToOne
    @JoinColumn(name = "cafe_id")
    private Cafe cafe;

    /**
     * Nombre del café en el momento de la compra.
     * Se guarda una copia para mantener el histórico.
     */
    private String nombre;

    /**
     * Precio unitario del café en el momento de la compra.
     * Se guarda una copia para mantener el histórico.
     */
    private double precio;

    /**
     * Cantidad de unidades del café en este item.
     */
    private int cantidad;

    /**
     * Subtotal del item (precio * cantidad).
     * Se calcula automáticamente al establecer el precio y la cantidad.
     */
    private double subtotal;

    public OrderItem() {
    }

    public OrderItem(Cafe cafe, String nombre, double precio, int cantidad) {
        this.cafe = cafe;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
        this.subtotal = precio * cantidad;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Cafe getCafe() {
        return cafe;
    }

    public void setCafe(Cafe cafe) {
        this.cafe = cafe;
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
