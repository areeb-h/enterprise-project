package com.example.enterpriseproject.service;

import com.example.enterpriseproject.model.Customer;
import com.example.enterpriseproject.model.Order;
import com.example.enterpriseproject.model.OrderStatus;

import java.util.List;

//interface for userService
public interface OrderService {
    public void save(Order order);

    List<Order> findOrdersByCustomerAndOrderStatus(Customer customer, OrderStatus status);

    List<Order> findAll();

    String assignOrder(Long orderId, Long driverId);

    String cancelOrder(Long orderId);

    String completeOrder(Long orderId);

}

//