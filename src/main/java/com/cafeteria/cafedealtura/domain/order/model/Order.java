package com.cafeteria.cafedealtura.domain.order.model;

import com.cafeteria.cafedealtura.domain.user.model.User;
import com.cafeteria.cafedealtura.common.utils.ValidationUtils;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidad que representa un pedido en el sistema.
 * Esta clase mapea la tabla 'orders' en la base de datos.
 * 
 * Características principales:
 * - Relación Many-to-One con User (un usuario puede tener muchos pedidos)
 * - Relación One-to-Many con OrderItem (un pedido puede tener muchos items)
 * - Cálculo automático del total
 * - Fecha de creación automática
 * - Validaciones de negocio
 */
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private LocalDateTime date;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();

    @Column(nullable = false)
    private Double total;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    public Order() {
        this.date = LocalDateTime.now();
        this.status = OrderStatus.PENDING;
        this.total = 0.0;
    }

    public Order(User user) {
        this();
        ValidationUtils.validateNotNull(user, "usuario");
        this.user = user;
    }

    // Getters y setters con validación
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        ValidationUtils.validatePositive(id, "id");
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        ValidationUtils.validateNotNull(user, "usuario");
        this.user = user;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        ValidationUtils.validateNotNull(date, "fecha");
        this.date = date;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        ValidationUtils.validateNotNull(items, "items");
        this.items = items;
        calculateTotal();
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        ValidationUtils.validatePositive(total, "total");
        this.total = total;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        ValidationUtils.validateNotNull(status, "estado");
        this.status = status;
    }

    // Métodos de negocio
    public void addItem(OrderItem item) {
        ValidationUtils.validateNotNull(item, "item");
        items.add(item);
        item.setOrder(this);
        calculateTotal();
    }

    public void removeItem(OrderItem item) {
        ValidationUtils.validateNotNull(item, "item");
        items.remove(item);
        item.setOrder(null);
        calculateTotal();
    }

    private void calculateTotal() {
        this.total = items.stream()
                .mapToDouble(OrderItem::getSubtotal)
                .sum();
    }

    public boolean canBeModified() {
        return status == OrderStatus.PENDING;
    }

    public boolean canBeCancelled() {
        return status == OrderStatus.PENDING || status == OrderStatus.CONFIRMED;
    }
}