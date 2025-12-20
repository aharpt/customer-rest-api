package com.java.rest_api.controllers;

import com.java.rest_api.models.Order;
import com.java.rest_api.models.db.Customer;
import com.java.rest_api.services.CustomerService;
import com.java.rest_api.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CustomerService customerService;

    @GetMapping("/getOrders")
    public ResponseEntity<List<Order>> getOrders() {
        try {
            return ResponseEntity.ok(orderService.getOrders());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/getOrdersByCustomer")
    public ResponseEntity<List<Order>> getOrdersByCustomer(@RequestParam String email) {
        try {
            Customer customer = customerService.findByEmail(email);
            if (customer == null) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(orderService.getOrdersByCustomerId(customer.getId()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Order order) {
        try {
            com.java.rest_api.models.db.Order savedOrder = orderService.save(order);
            return ResponseEntity.ok("Order of " + savedOrder.getProductName() + " has been saved successfully");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Order could not be saved due to: " + e.getMessage());
        }
    }
}
