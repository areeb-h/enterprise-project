package com.example.enterpriseproject.controller;

import com.example.enterpriseproject.model.User;
import com.example.enterpriseproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//WILL HANDLE ALL THE MAPPINGS FOR WHAT THE ADMIN CAN ACCESS

@Controller
public class AdminController {

    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = {"/admin/dashboard"}, method = RequestMethod.GET)
    public String adminHome(){
        return "admin/dashboard";
    }

    @GetMapping("/admin/dashboard/customers")
    public String getCustomers(Model model) {
        List<User> customers = userService.findAllCustomers();
        model.addAttribute("customers", customers);
        return "admin/customers";
    }

    @GetMapping("/admin/dashboard/customers/enable/{email}")
    public String enableCustomer(@PathVariable String email) {
        userService.enableUser(email);
        return "redirect:/admin/customers";
    }
}
