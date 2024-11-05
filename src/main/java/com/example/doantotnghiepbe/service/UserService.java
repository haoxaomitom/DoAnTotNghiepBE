package com.example.doantotnghiepbe.service;

import com.example.doantotnghiepbe.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    // Create
    User createUser(User user); // Method to create a new user

    // Read
    List<User> getAllUsers(); // Method to get all users
    Optional<User> getUserByUsername(String username); // Method to get a specific user by username

    // Update
    User updateUser(String username, User user); // Method to update an existing user

    // Delete
    boolean deleteUser(String username); // Method to delete a user by username
}

