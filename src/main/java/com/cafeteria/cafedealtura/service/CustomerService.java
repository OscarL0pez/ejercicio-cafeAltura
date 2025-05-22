package com.cafeteria.cafedealtura.service;

import com.cafeteria.cafedealtura.model.Customer;
import com.cafeteria.cafedealtura.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public Optional<Customer> findById(Long id) {
        return repository.findById(id);
    }

    public Customer create(Customer customer) {
        return repository.save(customer);
    }

    public Customer update(Long id, Customer customer) {
        customer.setId(id);
        return repository.save(customer);
    }

    public boolean delete(Long id) {
        return repository.deleteById(id);
    }

    public boolean exists(Long id) {
        return repository.existsById(id);
    }

    public List<Customer> obtenerTodos() {
        return repository.findAll();
    }


}
