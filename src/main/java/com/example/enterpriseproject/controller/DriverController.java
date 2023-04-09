package com.example.enterpriseproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

// WILL HANDLE THE MAPPINGS FOR ONLY WHAT THE DRIVER CAN ACCESS

@Controller
public class DriverController {
    
    @RequestMapping(value = {"/driver/dashboard"}, method = RequestMethod.GET)
    public String driverHome(){
        return "driver/dashboard";
    }
}
