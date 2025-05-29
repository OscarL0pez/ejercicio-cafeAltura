package com.cafeteria.cafedealtura.domain.order.service;

import com.cafeteria.cafedealtura.common.exceptions.BadRequestException;
import com.cafeteria.cafedealtura.common.exceptions.ResourceNotFoundException;
import com.cafeteria.cafedealtura.domain.coffee.model.Coffee;
import com.cafeteria.cafedealtura.domain.coffee.repository.CoffeeRepository;
import com.cafeteria.cafedealtura.domain.order.dto.request.CreateOrderRequestDTO;
import com.cafeteria.cafedealtura.domain.order.dto.request.UpdateOrderRequestDTO;
import com.cafeteria.cafedealtura.domain.order.dto.response.OrderResponseDTO;
import com.cafeteria.cafedealtura.domain.order.model.Order;
import com.cafeteria.cafedealtura.domain.order.model.OrderItem;
import com.cafeteria.cafedealtura.domain.order.model.OrderStatus;
import com.cafeteria.cafedealtura.domain.order.repository.OrderRepository;
import com.cafeteria.cafedealtura.domain.user.model.User;
import com.cafeteria.cafedealtura.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Servicio para la gestión de pedidos.
 * Implementa la lógica de negocio relacionada con los pedidos.
 */
@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CoffeeRepository coffeeRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository,
            UserRepository userRepository,
            CoffeeRepository coffeeRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.coffeeRepository = coffeeRepository;
    }

    /**
     * Obtiene todos los pedidos con paginación.
     * 
     * @param pageable Configuración de paginación y ordenamiento
     * @return Lista de pedidos
     */
    public List<OrderResponseDTO> findAll(Pageable pageable) {
        Page<Order> page = orderRepository.findAll(pageable);
        return page.getContent().stream()
                .map(OrderResponseDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Busca un pedido por su ID.
     * 
     * @param id ID del pedido
     * @return Pedido encontrado
     * @throws ResourceNotFoundException si el pedido no existe
     */
    public OrderResponseDTO findById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido", "id", id));
        return new OrderResponseDTO(order);
    }

    /**
     * Crea un nuevo pedido.
     * 
     * @param createDTO Datos del pedido a crear
     * @return Pedido creado
     * @throws ResourceNotFoundException si el usuario o algún café no existe
     * @throws BadRequestException       si hay algún error en los datos
     */
    @Transactional
    public OrderResponseDTO create(CreateOrderRequestDTO createDTO) {
        // Validar usuario
        User user = userRepository.findById(createDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", createDTO.getUserId()));

        // Crear pedido
        Order order = new Order(user);

        // Agregar items
        createDTO.getItems().forEach(itemDTO -> {
            Coffee coffee = coffeeRepository.findById(itemDTO.getCoffeeId())
                    .orElseThrow(() -> new ResourceNotFoundException("Café", "id", itemDTO.getCoffeeId()));
            OrderItem item = new OrderItem(coffee, itemDTO.getQuantity());
            order.addItem(item);
        });

        // Guardar pedido
        return new OrderResponseDTO(orderRepository.save(order));
    }

    /**
     * Actualiza un pedido existente.
     * 
     * @param id        ID del pedido a actualizar
     * @param updateDTO Nuevos datos del pedido
     * @return Pedido actualizado
     * @throws ResourceNotFoundException si el pedido, usuario o algún café no
     *                                   existe
     * @throws BadRequestException       si el pedido no puede ser modificado
     */
    @Transactional
    public OrderResponseDTO update(Long id, UpdateOrderRequestDTO updateDTO) {
        // Buscar pedido
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido", "id", id));

        // Validar que el pedido pueda ser modificado
        if (!order.canBeModified()) {
            throw new BadRequestException("El pedido no puede ser modificado en su estado actual");
        }

        // Validar usuario
        User user = userRepository.findById(updateDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", updateDTO.getUserId()));
        order.setUser(user);

        // Actualizar estado
        try {
            OrderStatus newStatus = OrderStatus.valueOf(updateDTO.getStatus());
            order.setStatus(newStatus);
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Estado de pedido inválido: " + updateDTO.getStatus());
        }

        // Limpiar items actuales
        order.getItems().clear();

        // Agregar nuevos items
        updateDTO.getItems().forEach(itemDTO -> {
            Coffee coffee = coffeeRepository.findById(itemDTO.getCoffeeId())
                    .orElseThrow(() -> new ResourceNotFoundException("Café", "id", itemDTO.getCoffeeId()));
            OrderItem item = new OrderItem(coffee, itemDTO.getQuantity());
            order.addItem(item);
        });

        // Guardar pedido actualizado
        return new OrderResponseDTO(orderRepository.save(order));
    }

    /**
     * Elimina un pedido.
     * 
     * @param id ID del pedido a eliminar
     * @throws ResourceNotFoundException si el pedido no existe
     * @throws BadRequestException       si el pedido no puede ser eliminado
     */
    @Transactional
    public void delete(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido", "id", id));

        if (!order.canBeCancelled()) {
            throw new BadRequestException("El pedido no puede ser eliminado en su estado actual");
        }

        orderRepository.delete(order);
    }

    /**
     * Busca pedidos por usuario.
     * 
     * @param userId ID del usuario
     * @return Lista de pedidos del usuario
     */
    public List<OrderResponseDTO> findByUserId(Long userId) {
        return orderRepository.findByUserId(userId).stream()
                .map(OrderResponseDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Busca pedidos por estado.
     * 
     * @param status Estado del pedido
     * @return Lista de pedidos con ese estado
     */
    public List<OrderResponseDTO> findByStatus(String status) {
        try {
            OrderStatus orderStatus = OrderStatus.valueOf(status);
            return orderRepository.findByStatus(orderStatus).stream()
                    .map(OrderResponseDTO::new)
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Estado de pedido inválido: " + status);
        }
    }

    /**
     * Busca pedidos por rango de fechas.
     * 
     * @param startDate Fecha inicial (inclusive)
     * @param endDate   Fecha final (inclusive)
     * @return Lista de pedidos dentro del rango de fechas
     */
    public List<OrderResponseDTO> findByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return orderRepository.findByDateBetween(startDate, endDate).stream()
                .map(OrderResponseDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene estadísticas de los pedidos.
     * 
     * @return Mapa con estadísticas (total, por estado, etc.)
     */
    public Map<String, Object> getStats() {
        Map<String, Object> stats = new HashMap<>();
        List<Order> orders = orderRepository.findAll();

        // Total de pedidos
        stats.put("totalPedidos", orders.size());

        // Total de ventas
        stats.put("totalVentas", orders.stream()
                .mapToDouble(Order::getTotal)
                .sum());

        // Pedidos por estado
        stats.put("pedidosPorEstado", orderRepository.getOrderStatsByStatus().stream()
                .collect(Collectors.toMap(
                        row -> ((OrderStatus) row[0]).getDescription(),
                        row -> (Long) row[1])));

        // Promedio de ventas por pedido
        stats.put("promedioVentas", orders.stream()
                .mapToDouble(Order::getTotal)
                .average()
                .orElse(0.0));

        return stats;
    }
}