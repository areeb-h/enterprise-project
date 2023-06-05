package com.example.enterpriseproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

// WILL HANDLE THE MAPPINGS FOR ONLY WHAT THE DRIVER CAN ACCESS

@Controller
public class DriverController {
    
    @RequestMapping(value = {"/driver/dashboard"}, method = RequestMethod.GET)
    public String driverHome(Model model){
        model.addAttribute("title", "dashboard");
        return "driver/dashboard";
    }

    @GetMapping("/driver/orders")
    public String book(Model model) {
        model.addAttribute("title", "orders");
        return "driver/orders";
    }
}
