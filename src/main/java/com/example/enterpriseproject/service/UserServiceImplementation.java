package com.example.enterpriseproject.service;

import com.example.enterpriseproject.model.*;
import com.example.enterpriseproject.repository.CustomerRepository;
import com.example.enterpriseproject.repository.DriverRepository;
import com.example.enterpriseproject.repository.UserRepository;
import com.example.enterpriseproject.repository.VehicleRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

//UserService will be implemented using this
@Service
public class UserServiceImplementation implements UserService, UserDetailsService {

    // access the userRepository methods to communicate with db
    @Autowired
    UserRepository userRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    DriverRepository driverRepository;
    @Autowired
    VehicleRepository vehicleRepository;

    // access bcryptpassword methods to encrypt password
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    // save user into db after encrypting password
    public void save(User user) {
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }

    public void saveCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    public void saveDriver(Driver driver) {
        driverRepository.save(driver);
    }

    public void saveVehicle(Vehicle vehicle, Driver driver) {
        vehicle.setDriver(driver);
        vehicleRepository.save(vehicle);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        User user = (User) authentication.getPrincipal();
        save(user);
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof User) {
            return (User) authentication.getPrincipal();
        }
        throw new IllegalStateException("No authenticated user found");
    }

    // check if username or email exists in db
    @Override
    public List<Object> isUserPresent(User user) {
        boolean userExists = false;
        String message = null;

        Optional<User> existingUsername = userRepository.findByUsername(user.getUsername());
        Optional<User> existingEmail = userRepository.findByEmail(user.getEmail());
        if (existingUsername.isPresent()) {
            userExists = true;
            message = "This username is not available";
        }
        if (existingEmail.isPresent()) {
            userExists = true;
            message = "This email is not available";
        }
        System.out.println("existingEmail.isPresent() - " + existingEmail.isPresent()
                + "exisitingUsername.isPresent() - " + existingUsername.isPresent());
        return Arrays.asList(userExists, message);
    }

    // get a user by username from db
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(
                () -> new UsernameNotFoundException(
                        String.format("USER_NOT_FOUND", username)));
    }

    // to find both active and inactive users
    @Override
    public List<User> findAllCustomers(boolean enabled) {
        return userRepository.findAllByRoleAndEnabled(Role.CUSTOMER, enabled);
    }

    public List<User> findAllDrivers(boolean enabled) {
        return userRepository.findAllByRoleAndEnabled(Role.DRIVER, enabled);
    }

    public List<User> findAllUsersByEnabled(boolean enabled) {
        return userRepository.findAllByEnabled(enabled);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void enableUser(String id, boolean enable) {
        Optional<User> optionalUser = userRepository.findById(Long.parseLong(id));
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setEnabled(enable);
            userRepository.save(user);
        } else {
            throw new IllegalArgumentException("User not found with email: " + id);
        }
    }

    public void lockUser(String id, boolean lock) {
        Optional<User> optionalUser = userRepository.findById(Long.parseLong(id));
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setLocked(true);
            userRepository.save(user);
        } else {
            throw new IllegalArgumentException("User not found with email: " + id);
        }
    }
}
