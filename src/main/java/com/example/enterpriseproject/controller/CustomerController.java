package com.example.enterpriseproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

// WILL HANDLE THE MAPPINGS FOR ONLY WHAT THE CUSTOMER CAN ACCESS

@Controller
public class CustomerController {

    @RequestMapping(value = { "/customer/dashboard" }, method = RequestMethod.GET)
    public String customerHome() {
        return "customer/dashboard";
    }
}
