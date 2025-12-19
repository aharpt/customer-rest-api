package com.java.rest_api.repositories;

import com.java.rest_api.models.db.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface CustomerRepository extends JpaRepository<com.java.rest_api.models.db.Customer,Long> {
    Customer findByEmail(@Param("email") String email);
}
