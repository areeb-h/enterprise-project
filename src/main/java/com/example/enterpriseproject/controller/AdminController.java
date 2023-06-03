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

    @Autowired
    public AdminController(UserServiceImplementation userServiceImplementation) {
        this.userServiceImplementation = userServiceImplementation;
    }

    @RequestMapping(value = { "/admin/dashboard" }, method = RequestMethod.GET)
    public String adminHome() {
        return "admin/dashboard";
    }

    @GetMapping("/admin/dashboard/customers")
    public String getCustomers(Model model) {
        List<User> customers = userServiceImplementation.findAllCustomers();
        model.addAttribute("customers", customers);
        return "admin/customers";
    }

    @GetMapping("/admin/dashboard/customers/enable/{email:.+}")
    public String enableCustomer(@PathVariable("email") String email) {
        userServiceImplementation.enableUser(email);
        return "redirect:/admin/dashboard/customers";
    }

    @GetMapping("/admin/dashboard/drivers")
    public String getDrivers(Model model) {
        List<User> drivers = userServiceImplementation.findAllDrivers();
        model.addAttribute("drivers", drivers);
        return "admin/drivers";
    }

    @GetMapping("/admin/dashboard/drivers/enable/{email:.+}")
    public String enableDriver(@PathVariable("email") String email) {
        userServiceImplementation.enableUser(email);
        return "redirect:/admin/dashboard/drivers";
    }
}
