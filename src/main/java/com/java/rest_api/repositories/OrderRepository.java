package com.java.rest_api.repositories;

import com.java.rest_api.models.db.DBOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<DBOrder, Long> {
    List<DBOrder> findByCustomerId(Long customerId);
    List<DBOrder> findByProductId(Long productId);
}
