package com.cafeteria.cafedealtura.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cafeteria.cafedealtura.exception.ResourceNotFoundException;
import com.cafeteria.cafedealtura.model.Customer;
import com.cafeteria.cafedealtura.repository.CustomerRepository;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente", "id", id));
    }

    @Transactional
    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Transactional
    public Customer updateCustomer(Long id, Customer customerDetails) {
        Customer customer = getCustomerById(id);
        if (customerDetails.getNombre() != null) {
            customer.setNombre(customerDetails.getNombre());
        }
        return customerRepository.save(customer);
    }

    @Transactional
    public void deleteCustomer(Long id) {
        Customer customer = getCustomerById(id);
        customerRepository.delete(customer);
    }

    // Método de ejemplo para crear un cliente de prueba
    @Transactional
    public Customer createSampleCustomer() {
        Customer customer = new Customer();
        customer.setNombre("Juan Pérez");
        return customerRepository.save(customer);
    }
}
