package com.example.enterpriseproject.service;

import com.example.enterpriseproject.model.Customer;
import com.example.enterpriseproject.model.Driver;
import com.example.enterpriseproject.model.Order;
import com.example.enterpriseproject.model.OrderStatus;

import java.util.List;

//interface for userService
public interface OrderService {
    public void save(Order order);

    List<Order> findOrdersByCustomerAndOrderStatus(Customer customer, OrderStatus status);

    List<Order> findOrdersByDriverAndOrderStatus(Driver drvier, OrderStatus status);

    List<Order> findAll();

    List<Order> findOrderByOrderStatus(OrderStatus status);

    Order findOrderById(Long id);

    String assignOrder(Long orderId, Long driverId);

    String cancelOrder(Long orderId);

    String completeOrder(Long orderId);

    String rejectOrder(Order order);

}

//