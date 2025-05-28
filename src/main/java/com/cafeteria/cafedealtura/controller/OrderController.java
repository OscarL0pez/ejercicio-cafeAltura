package com.cafeteria.cafedealtura.controller;

import com.cafeteria.cafedealtura.dto.OrderDTO;
import com.cafeteria.cafedealtura.dto.OrderResponseDTO;
import com.cafeteria.cafedealtura.service.OrderService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * Controlador REST para la gestión de pedidos.
 * Proporciona endpoints para crear, consultar y eliminar pedidos,
 * incluyendo soporte para paginación en los endpoints de listado.
 * 
 * Endpoints disponibles:
 * - GET /api/orders - Listar todos los pedidos (paginado)
 * Parámetros de paginación:
 * - page: número de página (0-based, default: 0)
 * - size: tamaño de página (default: 10)
 * Ejemplo: GET /api/orders?page=0&size=5
 * 
 * - GET /api/orders/{id} - Obtener un pedido por ID
 * - GET /api/orders/customer/{customerId} - Obtener pedidos por cliente
 * - POST /api/orders/{customerId} - Crear un nuevo pedido
 * - DELETE /api/orders/{id} - Eliminar un pedido
 * 
 * Todas las respuestas de listado (GET /api/orders) incluyen metadatos de
 * paginación:
 * - content: Lista de pedidos en la página actual
 * - currentPage: Número de página actual
 * - totalPages: Total de páginas disponibles
 * - totalElements: Total de pedidos
 * - pageSize: Tamaño de página
 * - hasNext: Indica si hay página siguiente
 * - hasPrevious: Indica si hay página anterior
 */
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public ResponseEntity<OrderResponseDTO> create(@Valid @RequestBody OrderDTO orderDTO) {
        OrderResponseDTO saved = orderService.create(orderDTO);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> getById(@PathVariable Long id) {
        OrderResponseDTO order = orderService.getById(id);
        return ResponseEntity.ok(order);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> getAll(@PageableDefault(size = 10) Pageable pageable) {
        List<OrderResponseDTO> orders = orderService.getAll(pageable);
        return ResponseEntity.ok(orders);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> update(@PathVariable Long id, @Valid @RequestBody OrderDTO orderDTO) {
        OrderResponseDTO updated = orderService.update(id, orderDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        orderService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
