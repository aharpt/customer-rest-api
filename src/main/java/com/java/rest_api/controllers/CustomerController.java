package com.java.rest_api.controllers;

import com.java.rest_api.models.Customer;
import com.java.rest_api.models.db.DBCustomer;
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
            DBCustomer savedCustomer = customerService.save(customer);
            return ResponseEntity.ok("Customer " + savedCustomer.getFirstName() + " " + savedCustomer.getLastName() + " has been saved successfully");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Customer record could not be saved due to: " + e.getMessage());
        }
    }

    @GetMapping("/getCustomers")
    public ResponseEntity<List<Customer>> getCustomers() {
        return ResponseEntity.ok(customerService.getCustomers());
    }

    @GetMapping("/getActiveCustomers")
    public ResponseEntity<List<Customer>> getActiveCustomers() {
        return ResponseEntity.ok(customerService.getActiveCustomers());
    }

    @GetMapping("/getCustomerByEmail")
    public ResponseEntity<Customer> getCustomerByEmail(@RequestParam String email) {
        Customer customer = customerService.getCustomerByEmail(email);

        if (customer == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(customer);
        }
    }

    @PostMapping("/deleteCustomerByEmail")
    public ResponseEntity<DBCustomer> deleteCustomerByEmail(@RequestParam String email) {
        try {
            DBCustomer customer = customerService.findByEmail(email);

            if (customer == null) {
                return ResponseEntity.notFound().build();
            }
            customer.setDeleted(true);

            DBCustomer savedCustomer = customerService.delete(customer);
            return ResponseEntity.ok(savedCustomer);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
