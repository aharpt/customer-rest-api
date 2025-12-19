package com.java.rest_api.controllers;

import com.java.rest_api.models.Order;
import com.java.rest_api.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/getOrders")
    public ResponseEntity<List<Order>> getOrders() {
        try {
            return ResponseEntity.ok(orderService.getOrders());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
