package com.example.enterpriseproject.service;

import com.example.enterpriseproject.model.User;
import com.example.enterpriseproject.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//interface for userService
public interface UserService {
    public void save(User user);

    void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                 Authentication authentication) throws IOException, ServletException;

    public List<Object> isUserPresent(User user);

    UserRepository userRepository = null;

    public default List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }

    // to find both active and inactive users
    List<User> findAllCustomers(boolean enabled);
    List<User> findAllDrivers(boolean enabled);

    List<User> findAll();
}
