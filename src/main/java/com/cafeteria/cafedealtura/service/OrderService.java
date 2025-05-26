package com.cafeteria.cafedealtura.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cafeteria.cafedealtura.exception.ResourceNotFoundException;
import com.cafeteria.cafedealtura.model.Customer;
import com.cafeteria.cafedealtura.model.Order;
import com.cafeteria.cafedealtura.model.OrderItem;
import com.cafeteria.cafedealtura.model.Cafe;
import com.cafeteria.cafedealtura.repository.OrderRepository;
import com.cafeteria.cafedealtura.repository.CustomerRepository;
import com.cafeteria.cafedealtura.repository.CafeRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final CafeRepository cafeRepository;

    public OrderService(OrderRepository orderRepository,
            CustomerRepository customerRepository,
            CafeRepository cafeRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.cafeRepository = cafeRepository;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Orden", "id", id));
    }

    @Transactional
    public Order createOrder(Order order) {
        // Establecer la relación bidireccional entre Order y OrderItems
        if (order.getItems() != null) {
            order.getItems().forEach(item -> {
                item.setOrder(order);
                // Calcular el subtotal si no está calculado
                if (item.getSubtotal() == 0) {
                    item.setSubtotal(item.getPrecio() * item.getCantidad());
                }
            });
        }

        // Calcular el total si no está calculado
        if (order.getTotal() == 0) {
            calculateOrderTotal(order);
        }

        return orderRepository.save(order);
    }

    private void calculateOrderTotal(Order order) {
        double total = 0;
        if (order.getItems() != null) {
            for (OrderItem item : order.getItems()) {
                total += item.getSubtotal();
            }
        }
        order.setTotal(total);
    }

    @Transactional
    public Order createSampleOrder(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente", "id", customerId));

        Order order = new Order();
        order.setCustomer(customer);
        order.setFecha(LocalDateTime.now());

        List<OrderItem> items = new ArrayList<>();

        Cafe cafe1 = cafeRepository.findById(1L)
                .orElseThrow(() -> new ResourceNotFoundException("Café", "id", 1L));

        OrderItem item1 = new OrderItem(cafe1, cafe1.getNombre(), cafe1.getPrecio(), 2);
        item1.setOrder(order);
        items.add(item1);

        order.setItems(items);
        calculateOrderTotal(order);

        return orderRepository.save(order);
    }

    @Transactional
    public void deleteOrder(Long id) {
        Order order = getOrderById(id);
        orderRepository.delete(order);
    }

    public List<Order> getOrdersByCustomerId(Long customerId) {
        return orderRepository.findByCustomerId(customerId);
    }
}
