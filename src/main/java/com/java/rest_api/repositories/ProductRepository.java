package com.java.rest_api.repositories;

import com.java.rest_api.models.db.DBProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<DBProduct,Long> {
    DBProduct findProductByProductName(@Param("productName") String productName);
}
