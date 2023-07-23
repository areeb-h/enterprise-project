package com.example.enterpriseproject.controller;

import com.example.enterpriseproject.model.*;
import com.example.enterpriseproject.service.OrderServiceImplementation;
import com.example.enterpriseproject.service.UserServiceImplementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

//WILL HANDLE ALL THE MAPPINGS FOR WHAT THE ADMIN CAN ACCESS

@Controller
public class AdminController {

    // private final UserService userService;
    @Autowired
    UserServiceImplementation userServiceImplementation;

    @Autowired
    OrderServiceImplementation orderServiceImpementation;

    // @Autowired
    public AdminController(UserServiceImplementation userServiceImplementation) {
        this.userServiceImplementation = userServiceImplementation;
    }

    @RequestMapping(value = { "/admin/dashboard" }, method = RequestMethod.GET)
    public String adminHome(Model model) {
        model.addAttribute("title", "dashboard");
        return "admin/dashboard";
    }

    @GetMapping("/admin/dashboard/driver-orders")
    public String getDriverOrders(Model model) {

        List<Order> orders = orderServiceImpementation.findOrderByOrderStatus(OrderStatus.COMPLETED);
        List<User> drivers = userServiceImplementation.findAllDrivers(true);

        model.addAttribute("title", "driver orders");
        model.addAttribute("orders", orders);
        model.addAttribute("drivers", drivers);

        return "admin/driver-orders";
    }

    @GetMapping("/admin/dashboard/customer-orders")
    public String getCustomerOrders(Model model) {

        List<Order> orders = orderServiceImpementation.findOrderByOrderStatus(OrderStatus.COMPLETED);
        List<User> customers = userServiceImplementation.findAllCustomers(true);

        model.addAttribute("title", "customer orders");
        model.addAttribute("orders", orders);
        model.addAttribute("customers", customers);

        return "admin/customer-orders";
    }

    @GetMapping("/admin/dashboard/users")
    public String getUsers(Model model) {
        List<User> pending_users = userServiceImplementation.findAllUsersByEnabled(false);
        List<User> active_users = userServiceImplementation.findAllUsersByLocked(false);
        List<User> inactive_users = userServiceImplementation.findAllUsersByLocked(true);

        // Remove admin users from the lists
        pending_users.removeIf(user -> user.getRole().equals(Role.ADMIN));
        inactive_users.removeIf(user -> user.getRole().equals(Role.ADMIN));
        active_users.removeIf(user -> user.getRole().equals(Role.ADMIN));

        model.addAttribute("pending_users", pending_users);
        model.addAttribute("inactive_users", inactive_users);
        model.addAttribute("active_users", active_users);
        model.addAttribute("title", "users");
        return "admin/users";
    }

    @GetMapping("/admin/dashboard/customers")
    public String getCustomers(Model model) {
        List<User> pending_customers = userServiceImplementation.findAllCustomers(false);
        List<User> active_customers = userServiceImplementation.findAllCustomersByLocked(false);
        List<User> inactive_customers = userServiceImplementation.findAllCustomersByLocked(true);

        model.addAttribute("pending_customers", pending_customers);
        model.addAttribute("inactive_customers", inactive_customers);
        model.addAttribute("active_customers", active_customers);
        model.addAttribute("title", "customers");
        return "admin/customers";
    }

    @GetMapping("/admin/dashboard/drivers")
    public String getDrivers(Model model) {
        List<User> pending_drivers = userServiceImplementation.findAllDrivers(false);
        List<User> active_drivers = userServiceImplementation.findAllDriversByLocked(false);
        List<User> inactive_drivers = userServiceImplementation.findAllDriversByLocked(true);

        model.addAttribute("pending_drivers", pending_drivers);
        model.addAttribute("inactive_drivers", inactive_drivers);
        model.addAttribute("active_drivers", active_drivers);
        model.addAttribute("title", "drivers");
        return "admin/drivers";
    }

    // could get the role parameter and combine all following three into one function, but that would cause conflicts when dealing with other pages in admin
    // Enabling, Locking and Unlocking users by getting the required action and the user id from parameter
    @GetMapping("/admin/dashboard/users/{action}/{id}")
    public String updateUserStatus(@PathVariable("action") String action, @PathVariable("id") String id) {
        switch (action) {
            case "enable" -> userServiceImplementation.enableUser(id, true);
            case "lock" -> userServiceImplementation.lockUser(id, true);
            case "unlock" -> userServiceImplementation.lockUser(id, false);
        }
        return "redirect:/admin/dashboard/users";
    }

    @GetMapping("/admin/dashboard/customers/{action}/{id}")
    public String updateCustomerStatus(@PathVariable("action") String action, @PathVariable("id") String id) {
        switch (action) {
            case "enable" -> userServiceImplementation.enableUser(id, true);
            case "lock" -> userServiceImplementation.lockUser(id, true);
            case "unlock" -> userServiceImplementation.lockUser(id, false);
        }
        return "redirect:/admin/dashboard/customers";
    }

    @GetMapping("/admin/dashboard/drivers/{action}/{id}")
    public String updateDriverStatus(@PathVariable("action") String action, @PathVariable("id") String id) {
        switch (action) {
            case "enable" -> userServiceImplementation.enableUser(id, true);
            case "lock" -> userServiceImplementation.lockUser(id, true);
            case "unlock" -> userServiceImplementation.lockUser(id, false);
            default -> {}
        }
        return "redirect:/admin/dashboard/drivers";
    }

}
