package com.cafeteria.cafedealtura.controller;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cafeteria.cafedealtura.model.Customer;
import com.cafeteria.cafedealtura.service.CustomerService;
import com.cafeteria.cafedealtura.dto.PaginatedResponse;

import jakarta.validation.Valid;

/**
 * Controlador REST para la gestión de clientes.
 * Proporciona endpoints para realizar operaciones CRUD sobre los clientes,
 * incluyendo soporte para paginación en los endpoints de listado.
 * 
 * Endpoints disponibles:
 * - GET /api/customers - Listar todos los clientes (paginado)
 * Parámetros de paginación:
 * - page: número de página (0-based, default: 0)
 * - size: tamaño de página (default: 10)
 * Ejemplo: GET /api/customers?page=0&size=5
 * 
 * - GET /api/customers/{id} - Obtener un cliente por ID
 * - POST /api/customers - Crear un nuevo cliente
 * - PUT /api/customers/{id} - Actualizar un cliente
 * - DELETE /api/customers/{id} - Eliminar un cliente
 * 
 * Todas las respuestas de listado (GET /api/customers) incluyen metadatos de
 * paginación:
 * - content: Lista de clientes en la página actual
 * - currentPage: Número de página actual
 * - totalPages: Total de páginas disponibles
 * - totalElements: Total de clientes
 * - pageSize: Tamaño de página
 * - hasNext: Indica si hay página siguiente
 * - hasPrevious: Indica si hay página anterior
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
     * Obtiene todos los clientes con paginación.
     * 
     * @param pageable Parámetros de paginación (page, size)
     *                 - page: número de página (0-based, default: 0)
     *                 - size: tamaño de página (default: 10)
     * @return ResponseEntity con PaginatedResponse que contiene:
     *         - Lista de clientes en la página actual
     *         - Metadatos de paginación (currentPage, totalPages, etc.)
     * 
     *         Ejemplo de uso:
     *         GET /api/customers?page=0&size=5
     * 
     *         Ejemplo de respuesta:
     *         {
     *         "content": [...],
     *         "currentPage": 0,
     *         "totalPages": 3,
     *         "totalElements": 12,
     *         "pageSize": 5,
     *         "hasNext": true,
     *         "hasPrevious": false
     *         }
     */
    @GetMapping
    public ResponseEntity<PaginatedResponse<Customer>> getAllCustomers(
            @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(new PaginatedResponse<>(customerService.getAllCustomers(pageable)));
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
