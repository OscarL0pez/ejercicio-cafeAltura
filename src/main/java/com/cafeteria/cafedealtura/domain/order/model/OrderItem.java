package com.cafeteria.cafedealtura.domain.order.model;

import com.cafeteria.cafedealtura.domain.coffee.model.Coffee;
import com.cafeteria.cafedealtura.common.utils.ValidationUtils;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * Entidad que representa un item individual dentro de un pedido.
 * Esta clase mapea la tabla 'order_items' en la base de datos.
 * 
 * Características principales:
 * - Relación Many-to-One con Order (muchos items pueden pertenecer a un pedido)
 * - Relación Many-to-One con Coffee (muchos items pueden referenciar al mismo
 * café)
 * - Cálculo automático del subtotal (precio * cantidad)
 * - Validaciones de negocio
 */
@Entity
@Table(name = "order_items")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coffee_id", nullable = false)
    private Coffee coffee;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Double price;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Double subtotal;

    public OrderItem() {
    }

    public OrderItem(Coffee coffee, Integer quantity) {
        this.coffee = coffee;
        this.name = coffee.getName();
        this.price = coffee.getPrice();
        this.quantity = quantity;
        calculateSubtotal();
    }

    // Getters y setters con validación
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        ValidationUtils.validatePositive(id, "id");
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Coffee getCoffee() {
        return coffee;
    }

    public void setCoffee(Coffee coffee) {
        ValidationUtils.validateNotNull(coffee, "café");
        this.coffee = coffee;
        this.name = coffee.getName();
        this.price = coffee.getPrice();
        calculateSubtotal();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        ValidationUtils.validateNotEmpty(name, "nombre");
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        ValidationUtils.validatePositive(price, "precio");
        this.price = price;
        calculateSubtotal();
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        ValidationUtils.validatePositive(quantity, "cantidad");
        this.quantity = quantity;
        calculateSubtotal();
    }

    public Double getSubtotal() {
        return subtotal;
    }

    private void calculateSubtotal() {
        if (price != null && quantity != null) {
            this.subtotal = price * quantity;
        }
    }
}