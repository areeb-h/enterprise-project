package com.example.enterpriseproject.service;

import com.example.enterpriseproject.model.Order;
import com.example.enterpriseproject.model.User;
import com.example.enterpriseproject.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//UserService will be implemented using this
@Service
public class OrderServiceImplementation implements OrderService {

    // access the userRepository methods to communicate with db
    @Autowired
    OrderRepository orderRepository;

    public void save(Order order) {
        order.setPickupAddress(order.getPickupAddress());
        order.setDestinationAddress(order.getDestinationAddress());
        order.setTime(order.getTime());
        order.setCost(order.getCost());
        order.setOrderStatus(order.getOrderStatus());
        orderRepository.save(order);
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }
}
