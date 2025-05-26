package com.cafeteria.cafedealtura.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cafeteria.cafedealtura.model.Order;
import com.cafeteria.cafedealtura.model.OrderItem;
import com.cafeteria.cafedealtura.model.Customer;
import com.cafeteria.cafedealtura.service.OrderService;
import com.cafeteria.cafedealtura.service.CustomerService;
import com.cafeteria.cafedealtura.exception.ResourceNotFoundException;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final CustomerService customerService;

    public OrderController(OrderService orderService, CustomerService customerService) {
        this.orderService = orderService;
        this.customerService = customerService;
    }

    @PostMapping("/{customerId}")
    public ResponseEntity<Order> createOrder(
            @PathVariable Long customerId,
            @Valid @RequestBody List<OrderItem> items) {
        // Obtener el cliente
        Customer customer = customerService.getCustomerById(customerId);

        // Crear la orden con todos los datos necesarios
        Order order = new Order();
        order.setCustomer(customer);
        order.setFecha(LocalDateTime.now());
        order.setItems(items);

        // Calcular el total de la orden
        double total = items.stream()
                .mapToDouble(item -> item.getPrecio() * item.getCantidad())
                .sum();
        order.setTotal(total);

        // Guardar la orden
        Order savedOrder = orderService.createOrder(order);
        return ResponseEntity.ok(savedOrder);
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Order>> getOrdersByCustomerId(@PathVariable Long customerId) {
        List<Order> orders = orderService.getOrdersByCustomerId(customerId);
        return ResponseEntity.ok(orders);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}
