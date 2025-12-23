package com.java.rest_api.repositories;

import com.java.rest_api.models.db.DBCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface CustomerRepository extends JpaRepository<DBCustomer,Long> {
    DBCustomer findByEmail(@Param("email") String email);
}
