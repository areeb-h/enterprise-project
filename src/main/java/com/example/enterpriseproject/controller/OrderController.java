package com.example.enterpriseproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.enterpriseproject.model.Order;
import com.example.enterpriseproject.model.OrderStatus;
import com.example.enterpriseproject.service.OrderServiceImplementation;

@Controller
public class OrderController {

    @Autowired
    OrderServiceImplementation orderServiceImpementation;

    @RequestMapping(value = "orders", method = RequestMethod.GET)
    public List<Order> getAllOrders(@RequestParam(required = false) Long driverId,
            @RequestParam(required = false) Long id, @RequestParam(required = false) OrderStatus status) {
        return orderServiceImpementation.findAll();
    }
}
