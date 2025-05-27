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

/**
 * Controlador REST para la gestión de pedidos.
 * Proporciona endpoints para crear, consultar y eliminar pedidos.
 * 
 * Endpoints disponibles:
 * - POST /api/orders/{customerId} - Crear un nuevo pedido para un cliente
 * - GET /api/orders - Listar todos los pedidos
 * - GET /api/orders/{id} - Obtener un pedido por ID
 * - GET /api/orders/customer/{customerId} - Obtener pedidos por cliente
 * - DELETE /api/orders/{id} - Eliminar un pedido
 */
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final CustomerService customerService;

    public OrderController(OrderService orderService, CustomerService customerService) {
        this.orderService = orderService;
        this.customerService = customerService;
    }

    /**
     * Crea un nuevo pedido para un cliente específico.
     * El pedido incluye una lista de items (cafés) y calcula automáticamente el
     * total.
     * 
     * @param customerId ID del cliente que realiza el pedido
     * @param items      Lista de items (cafés) en el pedido
     * @return Pedido creado con estado 200 OK
     * @throws ResourceNotFoundException si el cliente no existe
     */
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

    /**
     * Obtiene todos los pedidos del sistema.
     * 
     * @return Lista de todos los pedidos con estado 200 OK
     */
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    /**
     * Obtiene un pedido específico por su ID.
     * 
     * @param id ID del pedido a buscar
     * @return Pedido encontrado con estado 200 OK
     * @throws ResourceNotFoundException si el pedido no existe
     */
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        return ResponseEntity.ok(order);
    }

    /**
     * Obtiene todos los pedidos de un cliente específico.
     * 
     * @param customerId ID del cliente
     * @return Lista de pedidos del cliente con estado 200 OK
     * @throws ResourceNotFoundException si el cliente no existe
     */
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Order>> getOrdersByCustomerId(@PathVariable Long customerId) {
        List<Order> orders = orderService.getOrdersByCustomerId(customerId);
        return ResponseEntity.ok(orders);
    }

    /**
     * Elimina un pedido existente.
     * 
     * @param id ID del pedido a eliminar
     * @return 204 No Content si se eliminó correctamente
     * @throws ResourceNotFoundException si el pedido no existe
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}
