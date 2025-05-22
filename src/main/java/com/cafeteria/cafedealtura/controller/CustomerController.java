package com.cafeteria.cafedealtura.controller;

import com.cafeteria.cafedealtura.model.Customer;
import com.cafeteria.cafedealtura.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    // POST /customers
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Customer customer) {
        if (customer.getNombre() == null || customer.getNombre().trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("El nombre del cliente es obligatorio.");
        }

        Customer creado = service.create(customer);
        return ResponseEntity.status(HttpStatus.OK).body(creado);
    }

    // PUT /customers/{id}
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Customer customer) {
        if (!service.exists(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente no encontrado.");
        }

        if (customer.getNombre() == null || customer.getNombre().trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("El nombre del cliente es obligatorio.");
        }

        Customer actualizado = service.update(id, customer);
        return ResponseEntity.ok(actualizado);
    }

    // GET /customers → devuelve todos los clientes
    @GetMapping
    public ResponseEntity<?> obtenerTodos() {
        return ResponseEntity.ok(service.obtenerTodos());
    }




    // DELETE /customers/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        if (!service.exists(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente no encontrado.");
        }

        service.delete(id);
        return ResponseEntity.ok("Cliente eliminado.");
    }
}
