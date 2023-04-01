package com.example.enterpriseproject.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//TODO:ALL THE MAPPINGS FOR WHAT THE CUSTOMER CAN ACCESS
public class CustomerController {

    @RequestMapping(value = {"/customer/dashboard"}, method = RequestMethod.GET)
    public String adminHome(){
        return "customer/dashboard";
    }
}
