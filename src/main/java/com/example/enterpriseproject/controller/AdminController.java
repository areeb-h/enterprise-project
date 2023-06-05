package com.example.enterpriseproject.controller;

import com.example.enterpriseproject.model.User;
import com.example.enterpriseproject.service.UserService;
import com.example.enterpriseproject.service.UserServiceImplementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

//WILL HANDLE ALL THE MAPPINGS FOR WHAT THE ADMIN CAN ACCESS

@Controller
public class AdminController {

    // private final UserService userService;
    private final UserServiceImplementation userServiceImplementation;

    // @Autowired
    public AdminController(UserServiceImplementation userServiceImplementation) {
        this.userServiceImplementation = userServiceImplementation;
    }

    @RequestMapping(value = { "/admin/dashboard" }, method = RequestMethod.GET)
    public String adminHome() {
        return "admin/dashboard";
    }

    @GetMapping("/admin/dashboard/customers")
    public String getCustomers(Model model) {
        List<User> inactive_customers = userServiceImplementation.findAllCustomers(false);
        List<User> active_customers = userServiceImplementation.findAllCustomers(true);
        model.addAttribute("inactive_customers", inactive_customers);
        model.addAttribute("active_customers", active_customers);
        return "admin/customers";
    }

    @GetMapping("/admin/dashboard/customers/enable/{id}")
    public String enableCustomer(@PathVariable("id") String id) {
        userServiceImplementation.enableUser(id, true);
        return "redirect:/admin/dashboard/customers";
    }

    @GetMapping("/admin/dashboard/customers/disable/{id}")
    public String disableCustomer(@PathVariable("id") String id) {
        userServiceImplementation.enableUser(id, false);
        return "redirect:/admin/dashboard/customers";
    }

    @GetMapping("/admin/dashboard/drivers")
    public String getDrivers(Model model) {
        List<User> inactive_drivers = userServiceImplementation.findAllDrivers(false);
        List<User> active_drivers = userServiceImplementation.findAllDrivers(true);
        model.addAttribute("inactive_drivers", inactive_drivers);
        model.addAttribute("active_drivers", active_drivers);
        return "admin/drivers";
    }

    @GetMapping("/admin/dashboard/drivers/enable/{id}")
    public String enableDriver(@PathVariable("id") String id) {
        userServiceImplementation.enableUser(id, true);
        return "redirect:/admin/dashboard/drivers";
    }

    @GetMapping("/admin/dashboard/drivers/disable/{id}")
    public String disableDriver(@PathVariable("id") String id) {
        userServiceImplementation.enableUser(id, false);
        return "redirect:/admin/dashboard/drivers";
    }
}
