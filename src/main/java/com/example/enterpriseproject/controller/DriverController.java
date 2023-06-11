package com.example.enterpriseproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.enterpriseproject.model.Customer;
import com.example.enterpriseproject.model.Driver;
import com.example.enterpriseproject.model.Order;
import com.example.enterpriseproject.model.OrderStatus;
import com.example.enterpriseproject.model.Vehicle;
import com.example.enterpriseproject.service.OrderServiceImplementation;
import com.example.enterpriseproject.service.UserServiceImplementation;

// WILL HANDLE THE MAPPINGS FOR ONLY WHAT THE DRIVER CAN ACCESS

@Controller
public class DriverController {

    @Autowired
    OrderServiceImplementation orderServiceImpementation;

    @Autowired
    UserServiceImplementation userServiceImplementation;

    @RequestMapping(value = { "/driver/dashboard" }, method = RequestMethod.GET)
    public String driverHome(Model model) {

        return "driver/dashboard";
    }

    @GetMapping("/driver/orders")
    public String book(Model model) {
        // Driver driver = userServiceImplementation.getCurrentUser().getDriver();

        List<Order> pending_orders = orderServiceImpementation.findOrderByOrderStatus(
                OrderStatus.UNASSIGNED);
        List<Order> accepted_orders = orderServiceImpementation.findOrderByOrderStatus(
                OrderStatus.ASSIGNED);
        List<Order> completed_orders = orderServiceImpementation.findOrderByOrderStatus(
                OrderStatus.COMPLETED);

        model.addAttribute("title", "dashboard");
        model.addAttribute("pending_orders", pending_orders);
        model.addAttribute("accepted_orders", accepted_orders);
        model.addAttribute("completed_orders", completed_orders);

        return "driver/orders";
    }

    @GetMapping("/driver/orders/accept/{id}")
    public String acceptOrder(@PathVariable("id") Long id) {
        Order order = orderServiceImpementation.findOrderById(id);
        Driver driver = userServiceImplementation.getCurrentUser().getDriver();
        Vehicle vehicle = driver.getVehicle();

        order.setDriver(driver);
        order.setVehicle(vehicle);
        order.setOrderStatus(OrderStatus.ASSIGNED);

        orderServiceImpementation.assignOrder(id, driver.getId());

        return "redirect:/driver/orders";
    }

    @GetMapping("/driver/orders/complete/{id}")
    public String completeOrder(@PathVariable("id") Long id) {
        Order order = orderServiceImpementation.findOrderById(id);

        order.setOrderStatus(OrderStatus.COMPLETED);

        orderServiceImpementation.completeOrder(id);

        return "redirect:/driver/orders";
    }

}
