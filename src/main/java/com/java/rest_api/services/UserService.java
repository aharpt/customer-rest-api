package com.java.rest_api.services;

import com.java.rest_api.models.User;
import com.java.rest_api.models.db.DBUser;
import com.java.rest_api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> findAllUsers() {
        List<User> users = new ArrayList<>();
        List<DBUser> dbUsers = userRepository.findAll();

        for (DBUser dbUser : dbUsers) {
            User user = new User();
            user.setId(dbUser.getId());
            user.setUsername(dbUser.getUsername());
            user.setPassword(dbUser.getPassword());
            user.setRole(dbUser.getRole());

            users.add(user);
        }

        return users;
    }
}
