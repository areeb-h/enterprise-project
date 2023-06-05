package com.example.enterpriseproject.controller;

import com.example.enterpriseproject.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.ui.Model;

import java.util.List;

// WILL HANDLE THE MAPPINGS FOR THE CUSTOMER

@Controller
public class CustomerController {

    @RequestMapping(value = { "/customer/dashboard" }, method = RequestMethod.GET)
    public String customerHome(Model model) {
        model.addAttribute("title", "dashboard");
        return "customer/dashboard";
    }

    @GetMapping("/customer/book")
    public String book(Model model) {
        model.addAttribute("title", "book");
        return "customer/book";
    }
}
