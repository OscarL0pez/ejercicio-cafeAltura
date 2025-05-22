package com.cafeteria.cafedealtura.service;

import com.cafeteria.cafedealtura.model.*;
import com.cafeteria.cafedealtura.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepo;
    private final CustomerRepository customerRepo;
    private final CafeRepository cafeRepo;

    public OrderService(OrderRepository orderRepo, CustomerRepository customerRepo, CafeRepository cafeRepo) {
        this.orderRepo = orderRepo;
        this.customerRepo = customerRepo;
        this.cafeRepo = cafeRepo;
    }

    public Optional<Order> findById(Long id) {
        return orderRepo.findById(id);
    }

    public List<Order> findAll() {
        return orderRepo.findAll();
    }

    public List<Order> findByCustomerId(Long customerId) {
        return orderRepo.findByCustomerId(customerId);
    }

    public Object create(Long customerId, List<OrderItem> items) {
        // Validar cliente
        Optional<Customer> clienteOpt = customerRepo.findById(customerId);
        if (clienteOpt.isEmpty()) {
            return "Cliente no encontrado.";
        }

        if (items == null || items.isEmpty()) {
            return "El pedido no puede estar vacío.";
        }

        List<OrderItem> itemsCompletos = new ArrayList<>();
        double total = 0.0;

        for (OrderItem item : items) {
            if (item.getCantidad() <= 0) {
                return "La cantidad debe ser mayor que 0.";
            }

            Optional<Cafe> cafeOpt = cafeRepo.findById(item.getIdCafe());
            if (cafeOpt.isEmpty()) {
                return "Café con ID " + item.getIdCafe() + " no encontrado.";
            }

            Cafe cafe = cafeOpt.get();
            OrderItem completo = new OrderItem(
                    cafe.getId(),
                    cafe.getNombre(),
                    cafe.getPrecio(),
                    item.getCantidad()
            );
            completo.setSubtotal(cafe.getPrecio() * item.getCantidad());
            itemsCompletos.add(completo);
            total += completo.getSubtotal();
        }

        Order pedido = new Order();
        pedido.setIdCliente(customerId);
        pedido.setNombreCliente(clienteOpt.get().getNombre());
        pedido.setFecha(LocalDateTime.now());
        pedido.setItems(itemsCompletos);
        pedido.setTotal(total);

        return orderRepo.save(pedido);
    }
}
