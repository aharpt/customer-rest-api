package com.java.rest_api.controllers;

import com.java.rest_api.models.Customer;
import com.java.rest_api.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/save")
    public void save(@RequestBody Customer customer) {
        customerService.save(customer);
    }
}
