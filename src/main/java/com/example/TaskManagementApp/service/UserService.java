package com.example.TaskManagementApp.service;

import com.example.TaskManagementApp.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User saveUser(User user);
    Optional<User> findByUsername(String username);
    Optional<User> findByUsernameAndPassword(String username, String password);
    Optional<User> findById(Long id);

    List<User> findAll();
}
