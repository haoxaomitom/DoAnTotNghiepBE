package com.example.doantotnghiepbe.service.impl;

import com.example.doantotnghiepbe.entity.User;
import com.example.doantotnghiepbe.repository.UserRepository;
import com.example.doantotnghiepbe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    // Create
    @Override
    public User createUser(User user) {
        return userRepository.save(user); // Save new user to the database
    }

    // Read all users
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Read user by username
    @Override
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findById(username);
    }

    // Update user
    @Override
    public User updateUser(String username, User userDetails) {
        Optional<User> userOptional = userRepository.findById(username);
        if (userOptional.isPresent()) {
            User existingUser = userOptional.get();
            // Update fields
            existingUser.setFirstName(userDetails.getFirstName());
            existingUser.setLastName(userDetails.getLastName());
            existingUser.setEmail(userDetails.getEmail());
            existingUser.setPhoneNumber(userDetails.getPhoneNumber());
            existingUser.setStreet(userDetails.getStreet());
            existingUser.setWardName(userDetails.getWardName());
            existingUser.setDistrictName(userDetails.getDistrictName());
            existingUser.setProvinceName(userDetails.getProvinceName());
            existingUser.setPassword(userDetails.getPassword());
            existingUser.setAvatar(userDetails.getAvatar());
            existingUser.setRole(userDetails.getRole()); // Assuming Role is an object
            return userRepository.save(existingUser); // Save updated user
        } else {
            return null; // Handle case where user is not found
        }
    }

    // Delete user
    @Override
    public boolean deleteUser(String username) {
        try {
            userRepository.deleteById(username);
            return true;
        } catch (Exception e) {
            return false; // Handle failure
        }
    }
}
