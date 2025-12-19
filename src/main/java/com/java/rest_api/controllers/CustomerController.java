package com.java.rest_api.controllers;

import com.java.rest_api.models.Customer;
import com.java.rest_api.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
            return new ResponseEntity<>("Customer " + customer.getFirstName() + " " + customer.getLastName() + " has been saved successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Customer record could not be saved due to: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getCustomers")
    public ResponseEntity<List<Customer>> getCustomers() {
        return new ResponseEntity<>(customerService.getCustomers(), HttpStatus.OK);
    }

    @GetMapping("/getCustomer")
    public ResponseEntity<Customer> getCustomerByEmail(@RequestParam String email) {
        Customer customer = customerService.getCustomerByEmail(email);

        if (customer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(customer, HttpStatus.OK);
        }
    }
}
