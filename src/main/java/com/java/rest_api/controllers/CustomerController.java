package com.java.rest_api.controllers;

import com.java.rest_api.models.Customer;
import com.java.rest_api.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Customer customer) {
        try {
            customerService.save(customer);
            return ResponseEntity.ok("Customer " + customer.getFirstName() + " " + customer.getLastName() + " has been saved successfully");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Customer record could not be saved due to: " + e.getMessage());
        }
    }

    @GetMapping("/getCustomers")
    public ResponseEntity<List<Customer>> getCustomers() {
        return ResponseEntity.ok(customerService.getCustomers());
    }

    @GetMapping("/getCustomer")
    public ResponseEntity<Customer> getCustomerByEmail(@RequestParam String email) {
        Customer customer = customerService.getCustomerByEmail(email);

        if (customer == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(customer);
        }
    }
}
