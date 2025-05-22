package com.cafeteria.cafedealtura.repository;

import com.cafeteria.cafedealtura.model.Order;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class OrderRepository {

    private final Map<Long, Order> pedidos = new HashMap<>();
    private Long nextId = 1L;

    public Order save(Order pedido) {
        if (pedido.getId() == null) {
            pedido.setId(nextId++);
        }
        pedidos.put(pedido.getId(), pedido);
        return pedido;
    }

    public List<Order> findAll() {
        return pedidos.values().stream()
                .sorted(Comparator.comparing(Order::getFecha).reversed())
                .collect(Collectors.toList());
    }

    public Optional<Order> findById(Long id) {
        return Optional.ofNullable(pedidos.get(id));
    }

    public List<Order> findByCustomerId(Long customerId) {
        return pedidos.values().stream()
                .filter(p -> p.getIdCliente().equals(customerId))
                .sorted(Comparator.comparing(Order::getFecha).reversed())
                .collect(Collectors.toList());
    }
}
