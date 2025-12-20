package com.java.rest_api.services;

import com.java.rest_api.models.Order;
import com.java.rest_api.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public List<Order> getOrders() {
        List<com.java.rest_api.models.db.Order> dbOrders = orderRepository.findAll();
        List<Order> orders = new ArrayList<>();

        for (com.java.rest_api.models.db.Order dbOrder : dbOrders) {
            Order order = new Order();
            order.setCustomerId(dbOrder.getCustomerId());
            order.setProductId(dbOrder.getProductId());
            order.setQuantity(dbOrder.getQuantity());
            order.setPrice(dbOrder.getPrice());

            orders.add(order);
        }

        return orders;
    }

    public List<Order> getOrdersByCustomerId(Long customerId) {
        List<com.java.rest_api.models.db.Order> dbOrders = orderRepository.findByCustomerId(customerId);
        List<Order> orders = new ArrayList<>();

        for (com.java.rest_api.models.db.Order dbOrder : dbOrders) {
            Order order = new Order();
            order.setId(dbOrder.getId());
            order.setCustomerId(dbOrder.getCustomerId());
            order.setProductId(dbOrder.getProductId());
            order.setQuantity(dbOrder.getQuantity());
            order.setPrice(dbOrder.getPrice());

            orders.add(order);
        }

        return orders;
    }

    public List<Order> getOrdersByProductId(Long productId) {
        List<com.java.rest_api.models.db.Order> dbOrders = orderRepository.findByProductId(productId);
        List<Order> orders = new ArrayList<>();

        for (com.java.rest_api.models.db.Order dbOrder : dbOrders) {
            Order order = new Order();
            order.setId(dbOrder.getId());
            order.setCustomerId(dbOrder.getCustomerId());
            order.setProductId(dbOrder.getProductId());
            order.setProductName(dbOrder.getProductName());
            order.setQuantity(dbOrder.getQuantity());
            order.setPrice(dbOrder.getPrice());

            orders.add(order);
        }

        return orders;
    }

    public com.java.rest_api.models.db.Order save(Order order) {
        com.java.rest_api.models.db.Order dbOrder = new com.java.rest_api.models.db.Order();

        dbOrder.setCustomerId(order.getCustomerId());
        dbOrder.setProductId(order.getProductId());
        dbOrder.setProductName(order.getProductName());
        dbOrder.setQuantity(order.getQuantity());
        dbOrder.setPrice(order.getPrice());

        try {
            return orderRepository.save(dbOrder);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
