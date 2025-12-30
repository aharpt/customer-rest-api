package com.java.rest_api.repositories;

import com.java.rest_api.models.db.DBUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<DBUser,Long> {
}
