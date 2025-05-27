package com.cafeteria.cafedealtura.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cafeteria.cafedealtura.model.Customer;
import com.cafeteria.cafedealtura.model.Order;
import com.cafeteria.cafedealtura.service.CustomerService;
import com.cafeteria.cafedealtura.service.OrderService;

import jakarta.validation.Valid;

/**
 * Controlador de ejemplo para la gestión de clientes y pedidos.
 * 
 * Proporciona endpoints REST para crear, consultar, actualizar y eliminar
 * clientes,
 * así como para crear un cliente de ejemplo. Utiliza los servicios
 * CustomerService
 * y OrderService para realizar las operaciones de negocio.
 * 
 * Endpoints disponibles:
 * - POST /api/examples/customer : Crea un cliente de ejemplo
 * - POST /api/examples/customers : Crea un nuevo cliente con los datos
 * proporcionados
 * - GET /api/examples/customers : Obtiene la lista de todos los clientes
 * - GET /api/examples/customers/{id} : Obtiene un cliente por su ID
 * - PUT /api/examples/customers/{id} : Actualiza los datos de un cliente
 * existente
 * - DELETE /api/examples/customers/{id} : Elimina un cliente por su ID
 * 
 * Este controlador es útil para pruebas y ejemplos de integración con los
 * servicios
 * de clientes y pedidos.
 */

@RestController
@RequestMapping("/api/examples")
public class ExampleController {

    private final CustomerService customerService;
    private final OrderService orderService;

    public ExampleController(CustomerService customerService, OrderService orderService) {
        this.customerService = customerService;
        this.orderService = orderService;
    }

    @PostMapping("/customer")
    public ResponseEntity<Customer> createSampleCustomer() {
        Customer customer = customerService.createSampleCustomer();
        return ResponseEntity.ok(customer);
    }

    @PostMapping("/customers")
    public ResponseEntity<Customer> createCustomer(@Valid @RequestBody Customer customer) {
        Customer savedCustomer = customerService.createCustomer(customer);
        return ResponseEntity.ok(savedCustomer);
    }

    @GetMapping("/customers")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/customers/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        Customer customer = customerService.getCustomerById(id);
        return ResponseEntity.ok(customer);
    }

    @PutMapping("/customers/{id}")
    public ResponseEntity<Customer> updateCustomer(
            @PathVariable Long id,
            @Valid @RequestBody Customer customerDetails) {
        Customer updatedCustomer = customerService.updateCustomer(id, customerDetails);
        return ResponseEntity.ok(updatedCustomer);
    }

    @DeleteMapping("/customers/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/order/{customerId}")
    public ResponseEntity<Order> createSampleOrder(@PathVariable Long customerId) {
        Order order = orderService.createSampleOrder(customerId);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }
}