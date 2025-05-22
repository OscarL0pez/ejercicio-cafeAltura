package com.cafeteria.cafedealtura.repository;

import com.cafeteria.cafedealtura.model.Customer;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class CustomerRepository {

    private Map<Long, Customer> customers = new HashMap<>();
    private Long nextId = 1L;

    public List<Customer> findAll() {
        return new ArrayList<>(customers.values());
    }

    public Optional<Customer> findById(Long id) {
        return Optional.ofNullable(customers.get(id));
    }

    public Customer save(Customer customer) {
        if (customer.getId() == null) {
            customer.setId(nextId++);
        }
        customers.put(customer.getId(), customer);
        return customer;
    }

    public boolean existsById(Long id) {
        return customers.containsKey(id);
    }

    public boolean deleteById(Long id) {
        return customers.remove(id) != null;
    }
}
