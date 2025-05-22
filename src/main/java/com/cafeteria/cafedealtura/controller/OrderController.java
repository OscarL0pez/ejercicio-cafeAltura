package com.cafeteria.cafedealtura.controller;

import com.cafeteria.cafedealtura.model.Order;
import com.cafeteria.cafedealtura.model.OrderItem;
import com.cafeteria.cafedealtura.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    // POST /orders → Crear pedido
    @PostMapping
    public ResponseEntity<?> crearPedido(
            @RequestParam Long customerId,
            @RequestBody List<OrderItem> items) {

        Object resultado = service.create(customerId, items);

        if (resultado instanceof String) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resultado);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
    }

    // GET /orders → Todos los pedidos
    @GetMapping
    public ResponseEntity<List<Order>> obtenerTodos() {
        return ResponseEntity.ok(service.findAll());
    }

    // GET /orders/{id} → Pedido por ID
    @GetMapping("/{id}")
    public ResponseEntity<Order> obtenerPorId(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido no encontrado."));
    }

    // GET /orders/customer/{customerId} → Pedidos de un cliente
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Order>> obtenerPorCliente(@PathVariable Long customerId) {
        return ResponseEntity.ok(service.findByCustomerId(customerId));
    }
}
