package com.example.enterpriseproject.service;

import com.example.enterpriseproject.model.Role;
import com.example.enterpriseproject.model.User;
import com.example.enterpriseproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

//UserService will be implemented using this
@Service
public class UserServiceImplementation implements UserService, UserDetailsService {

    // access the userRepository methods to communicate with db
    @Autowired
    UserRepository userRepository;

    // access bcryptpassword methods to encrypt password
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    // save user into db after encrypting password
    public void save(User user) {
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
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

    @Override
    public List<User> findAllCustomers() {
        return userRepository.findAllByRoleAndEnabled(Role.CUSTOMER, false);
    }

    @Override
    public List<User> findAllDrivers() {
        return userRepository.findAllByRoleAndEnabled(Role.DRIVER, false);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void enableUser(String email) {
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
