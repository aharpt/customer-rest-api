package com.java.rest_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<com.java.rest_api.models.db.Customer,String> {
}
