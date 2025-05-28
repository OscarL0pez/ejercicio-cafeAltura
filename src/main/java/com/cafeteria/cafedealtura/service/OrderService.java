package com.cafeteria.cafedealtura.service;

import com.cafeteria.cafedealtura.dto.OrderDTO;
import com.cafeteria.cafedealtura.dto.OrderResponseDTO;
import com.cafeteria.cafedealtura.exception.ResourceNotFoundException;
import com.cafeteria.cafedealtura.model.Cafe;
import com.cafeteria.cafedealtura.model.Order;
import com.cafeteria.cafedealtura.model.OrderItem;
import com.cafeteria.cafedealtura.model.User;
import com.cafeteria.cafedealtura.repository.CafeRepository;
import com.cafeteria.cafedealtura.repository.OrderRepository;
import com.cafeteria.cafedealtura.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CafeRepository cafeRepository;

    public OrderService(OrderRepository orderRepository, UserRepository userRepository,
            CafeRepository cafeRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.cafeRepository = cafeRepository;
    }

    public List<OrderResponseDTO> getAll(Pageable pageable) {
        Page<Order> page = orderRepository.findAll(pageable);
        return page.getContent().stream().map(OrderResponseDTO::new).collect(Collectors.toList());
    }

    public OrderResponseDTO getById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order", "id", id));
        return new OrderResponseDTO(order);
    }

    @Transactional
    public OrderResponseDTO create(OrderDTO orderDTO) {
        User user = userRepository.findById(orderDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", orderDTO.getUserId()));
        Order order = new Order();
        order.setUser(user);
        order.setDate(LocalDateTime.now());
        List<OrderItem> items = orderDTO.getItems().stream().map(itemDTO -> {
            Cafe cafe = cafeRepository.findById(itemDTO.getCafeId())
                    .orElseThrow(() -> new ResourceNotFoundException("Cafe", "id", itemDTO.getCafeId()));
            OrderItem item = new OrderItem(cafe, cafe.getName(), cafe.getPrice(), itemDTO.getQuantity());
            item.setOrder(order);
            return item;
        }).collect(Collectors.toList());
        order.setItems(items);
        double total = items.stream().mapToDouble(OrderItem::getSubtotal).sum();
        order.setTotal(total);
        Order saved = orderRepository.save(order);
        return new OrderResponseDTO(saved);
    }

    @Transactional
    public OrderResponseDTO update(Long id, OrderDTO orderDTO) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order", "id", id));
        User user = userRepository.findById(orderDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", orderDTO.getUserId()));
        order.setUser(user);
        List<OrderItem> items = orderDTO.getItems().stream().map(itemDTO -> {
            Cafe cafe = cafeRepository.findById(itemDTO.getCafeId())
                    .orElseThrow(() -> new ResourceNotFoundException("Cafe", "id", itemDTO.getCafeId()));
            OrderItem item = new OrderItem(cafe, cafe.getName(), cafe.getPrice(), itemDTO.getQuantity());
            item.setOrder(order);
            return item;
        }).collect(Collectors.toList());
        order.setItems(items);
        double total = items.stream().mapToDouble(OrderItem::getSubtotal).sum();
        order.setTotal(total);
        Order updated = orderRepository.save(order);
        return new OrderResponseDTO(updated);
    }

    @Transactional
    public void delete(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order", "id", id));
        orderRepository.delete(order);
    }

}
