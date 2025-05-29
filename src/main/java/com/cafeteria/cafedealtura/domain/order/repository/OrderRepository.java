package com.cafeteria.cafedealtura.domain.order.repository;

import com.cafeteria.cafedealtura.domain.order.model.Order;
import com.cafeteria.cafedealtura.domain.order.model.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repositorio para la entidad Order.
 * Proporciona métodos para acceder y manipular los datos de pedidos.
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    /**
     * Busca pedidos por ID de usuario.
     * 
     * @param userId ID del usuario
     * @return Lista de pedidos del usuario
     */
    List<Order> findByUserId(Long userId);

    /**
     * Busca pedidos por estado.
     * 
     * @param status Estado del pedido
     * @return Lista de pedidos con ese estado
     */
    List<Order> findByStatus(OrderStatus status);

    /**
     * Busca pedidos por rango de fechas.
     * 
     * @param startDate Fecha inicial (inclusive)
     * @param endDate   Fecha final (inclusive)
     * @return Lista de pedidos dentro del rango de fechas
     */
    List<Order> findByDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Busca pedidos por usuario y estado.
     * 
     * @param userId ID del usuario
     * @param status Estado del pedido
     * @return Lista de pedidos del usuario con ese estado
     */
    List<Order> findByUserIdAndStatus(Long userId, OrderStatus status);

    /**
     * Obtiene estadísticas de pedidos por estado.
     * 
     * @return Lista de objetos con el estado y la cantidad de pedidos
     */
    @Query("SELECT o.status as status, COUNT(o) as count FROM Order o GROUP BY o.status")
    List<Object[]> getOrderStatsByStatus();

    /**
     * Busca pedidos con paginación y ordenamiento.
     * 
     * @param pageable Configuración de paginación y ordenamiento
     * @return Página de pedidos
     */
    Page<Order> findAll(Pageable pageable);
}