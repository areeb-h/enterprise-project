package com.example.enterpriseproject.service;

import com.example.enterpriseproject.model.Customer;
import com.example.enterpriseproject.model.Order;
import com.example.enterpriseproject.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//UserService will be implemented using this
@Service
public class OrderServiceImplementation implements OrderService {

    // access the userRepository methods to communicate with db
    @Autowired
    OrderRepository orderRepository;

    public void save(Order order) {
        orderRepository.save(order);
    }

    @Override
    public List<Order> findOrdersByCustomerAndOrderStatus(Customer customer, boolean status) {
        return orderRepository.findAllByCustomerAndOrderStatus(customer, status);
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }
}
