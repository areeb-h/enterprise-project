package com.example.enterpriseproject.controller;

import com.example.enterpriseproject.model.*;
import com.example.enterpriseproject.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class AuthController {

    // access the methods of userservice
    @Autowired
    UserService userService;

    // handle get call to login route
    @RequestMapping(value = { "/login" }, method = RequestMethod.GET)
    public String login() {
        return "auth/login";
    }

    // handle get call to register route
    @RequestMapping(value = { "/register" }, method = RequestMethod.GET)
    public String register(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("customer", new Customer());
        model.addAttribute("driver", new Driver());
        model.addAttribute("vehicle", new Vehicle());
        return "auth/register";
    }

    // handle post call to register route
    @RequestMapping(value = { "/register" }, method = RequestMethod.POST)
    public String addUser(Model model, @Valid User user, BindingResult bindingResult, Customer customer, Driver driver,
            Vehicle vehicle) {
        // if any validation error occurs
        if (bindingResult.hasErrors()) {
            model.addAttribute("successMessage", "Something went wrong");
            model.addAttribute("bindingResult", bindingResult);
            return "auth/register"; // redirect to register page
        }

        // check if user is present
        List<Object> userPresentObj = userService.isUserPresent(user);
        if ((Boolean) userPresentObj.get(0)) {
            model.addAttribute("failedMessage",
                    " EMAIL ALREADY IN USE. Please register with a different email address.");
            return "auth/register";
        }

        // set role and set enabled to false if the role is "CUSTOMER"
        user.setRole(user.getRole());

        // save user to db
        userService.save(user);

        model.addAttribute("successMessage", "User registered successfully!");
        if (!(user.getRole() == Role.ADMIN)) {
            model.addAttribute("successMessage", user.getRole().toString()
                    + " Registered successfully! Your application to Caber is under review. Your account will be activated after review");
        }

        if (user.getRole() == Role.CUSTOMER) {
            // save customer to db
            customer.setUser(user);
            userService.saveCustomer(customer);
        } else {
            // save driver to db
            driver.setUser(user);
            userService.saveDriver(driver);

            // save vehicle of driver to db
            userService.saveVehicle(vehicle, driver);
        }
        return "auth/login";
    }
}
