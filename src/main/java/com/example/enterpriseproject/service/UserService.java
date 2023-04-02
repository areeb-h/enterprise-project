package com.example.enterpriseproject.service;

import com.example.enterpriseproject.model.User;
import com.example.enterpriseproject.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//interface for userService
public interface UserService {
    public void save(User user);
    public List<Object> isUserPresent(User user);

    UserRepository userRepository = null;
    public default List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }

    List<User> findAllCustomers();

    List<User> findAll();

    public default void enableUser(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setEnabled(true);
            userRepository.save(user);
        } else {
            throw new IllegalArgumentException("User not found with email: " + email);
        }
    }
}
