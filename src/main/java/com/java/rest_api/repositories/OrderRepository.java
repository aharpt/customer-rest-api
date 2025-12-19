package com.java.rest_api.repositories;

import com.java.rest_api.models.db.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
