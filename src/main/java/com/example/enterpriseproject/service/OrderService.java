package com.example.enterpriseproject.service;

import com.example.enterpriseproject.model.Customer;
import com.example.enterpriseproject.model.Order;
import com.example.enterpriseproject.model.User;
import com.example.enterpriseproject.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

//interface for userService
public interface OrderService {
    public void save(Order order);

    List<Order> findOrdersByCustomerAndOrderStatus(Customer customer, boolean status);

    List<Order> findAll();
}
