package com.cafeteria.cafedealtura.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cafeteria.cafedealtura.model.Order;
import com.cafeteria.cafedealtura.model.OrderItem;
import com.cafeteria.cafedealtura.model.Customer;
import com.cafeteria.cafedealtura.service.OrderService;
import com.cafeteria.cafedealtura.service.CustomerService;
import com.cafeteria.cafedealtura.exception.ResourceNotFoundException;
import com.cafeteria.cafedealtura.dto.PaginatedResponse;
import com.cafeteria.cafedealtura.dto.OrderItemDTO;
import com.cafeteria.cafedealtura.dto.OrderResponseDTO;
import com.cafeteria.cafedealtura.service.CafeService;
import com.cafeteria.cafedealtura.model.Cafe;

import jakarta.validation.Valid;

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
    private final CustomerService customerService;
    private final CafeService cafeService;

    public OrderController(OrderService orderService, CustomerService customerService, CafeService cafeService) {
        this.orderService = orderService;
        this.customerService = customerService;
        this.cafeService = cafeService;
    }

    /**
     * Crea un nuevo pedido para un cliente específico.
     * El pedido incluye una lista de items (cafés) y calcula automáticamente el
     * total.
     * 
     * @param customerId ID del cliente que realiza el pedido
     * @param itemDTOs   Lista de items (cafés) en el pedido
     * @return Pedido creado con estado 200 OK
     * @throws ResourceNotFoundException si el cliente no existe
     */
    @PostMapping("/{customerId}")
    public ResponseEntity<Order> createOrder(
            @PathVariable Long customerId,
            @Valid @RequestBody List<OrderItemDTO> itemDTOs) {
        // Obtener el cliente
        Customer customer = customerService.getCustomerById(customerId);

        // Crear la orden con todos los datos necesarios
        Order order = new Order();
        order.setCustomer(customer);
        order.setFecha(LocalDateTime.now());
        List<OrderItem> items = new ArrayList<>();
        for (OrderItemDTO dto : itemDTOs) {
            Cafe cafe = cafeService.getCafeById(dto.getCafeId());
            OrderItem item = new OrderItem(cafe, (dto.getNombre() != null ? dto.getNombre() : cafe.getNombre()),
                    (dto.getPrecio() > 0 ? dto.getPrecio() : cafe.getPrecio()), dto.getCantidad());
            item.setOrder(order);
            items.add(item);
        }
        order.setItems(items);

        // Calcular el total de la orden
        double total = items.stream()
                .mapToDouble(item -> item.getSubtotal())
                .sum();
        order.setTotal(total);

        // Guardar la orden
        Order savedOrder = orderService.createOrder(order);
        return ResponseEntity.ok(savedOrder);
    }

    /**
     * Obtiene todos los pedidos con paginación.
     * 
     * @param pageable Parámetros de paginación (page, size)
     *                 - page: número de página (0-based, default: 0)
     *                 - size: tamaño de página (default: 10)
     * @return ResponseEntity con PaginatedResponse que contiene:
     *         - Lista de pedidos en la página actual
     *         - Metadatos de paginación (currentPage, totalPages, etc.)
     * 
     *         Ejemplo de uso:
     *         GET /api/orders?page=0&size=5
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
    public ResponseEntity<PaginatedResponse<OrderResponseDTO>> getAllOrders(
            @PageableDefault(size = 10) Pageable pageable) {
        var page = orderService.getAllOrders(pageable);
        var dtoPage = page.map(OrderResponseDTO::new);
        return ResponseEntity.ok(new PaginatedResponse<>(dtoPage));
    }

    /**
     * Obtiene un pedido específico por su ID.
     * 
     * @param id ID del pedido a buscar
     * @return Pedido encontrado con estado 200 OK
     * @throws ResourceNotFoundException si el pedido no existe
     */
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> getOrderById(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        return ResponseEntity.ok(new OrderResponseDTO(order));
    }

    /**
     * Obtiene todos los pedidos de un cliente específico.
     * 
     * @param customerId ID del cliente
     * @return Lista de pedidos del cliente con estado 200 OK
     * @throws ResourceNotFoundException si el cliente no existe
     */
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<OrderResponseDTO>> getOrdersByCustomerId(@PathVariable Long customerId) {
        List<Order> orders = orderService.getOrdersByCustomerId(customerId);
        List<OrderResponseDTO> dtoOrders = orders.stream()
                .map(OrderResponseDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtoOrders);
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
