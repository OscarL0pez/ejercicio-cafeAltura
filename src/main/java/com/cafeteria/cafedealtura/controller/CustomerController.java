package com.cafeteria.cafedealtura.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cafeteria.cafedealtura.model.Customer;
import com.cafeteria.cafedealtura.service.CustomerService;

import jakarta.validation.Valid;

/**
 * Controlador REST para la gestión de clientes.
 * Proporciona endpoints para realizar operaciones CRUD sobre los clientes.
 * 
 * Endpoints disponibles:
 * - POST /api/customers - Crear un nuevo cliente
 * - GET /api/customers - Listar todos los clientes
 * - GET /api/customers/{id} - Obtener un cliente por ID
 * - PUT /api/customers/{id} - Actualizar un cliente
 * - DELETE /api/customers/{id} - Eliminar un cliente
 */
@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * Crea un nuevo cliente.
     * 
     * @param customer Datos del cliente a crear (validados con @Valid)
     * @return Cliente creado con estado 200 OK
     */
    @PostMapping
    public ResponseEntity<Customer> createCustomer(@Valid @RequestBody Customer customer) {
        Customer savedCustomer = customerService.createCustomer(customer);
        return ResponseEntity.ok(savedCustomer);
    }

    /**
     * Obtiene un cliente específico por su ID.
     * 
     * @param id ID del cliente a buscar
     * @return Cliente encontrado con estado 200 OK
     * @throws ResourceNotFoundException si el cliente no existe
     */
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        Customer customer = customerService.getCustomerById(id);
        return ResponseEntity.ok(customer);
    }

    /**
     * Actualiza los datos de un cliente existente.
     * 
     * @param id              ID del cliente a actualizar
     * @param customerDetails Nuevos datos del cliente (validados con @Valid)
     * @return Cliente actualizado con estado 200 OK
     * @throws ResourceNotFoundException si el cliente no existe
     */
    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(
            @PathVariable Long id,
            @Valid @RequestBody Customer customerDetails) {
        Customer updatedCustomer = customerService.updateCustomer(id, customerDetails);
        return ResponseEntity.ok(updatedCustomer);
    }

    /**
     * Obtiene todos los clientes del sistema.
     * 
     * @return Lista de todos los clientes con estado 200 OK
     */
    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    /**
     * Elimina un cliente existente.
     * 
     * @param id ID del cliente a eliminar
     * @return 204 No Content si se eliminó correctamente
     * @throws ResourceNotFoundException si el cliente no existe
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
}
