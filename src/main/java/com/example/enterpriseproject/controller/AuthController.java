package com.example.enterpriseproject.controller;

import com.example.enterpriseproject.model.Role;
import com.example.enterpriseproject.model.User;
import com.example.enterpriseproject.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AuthController {

    // access the methods of userservice
    @Autowired
    UserService userService;

    // handle get call to login route
    @RequestMapping(value = {"/login"}, method = RequestMethod.GET)
    public String login() {
        return "auth/login";
    }

    // handle get call to register route
    @RequestMapping(value = {"/register"}, method = RequestMethod.GET)
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "auth/register";
    }

    // handle post call to register route
    @RequestMapping(value = {"/register"}, method = RequestMethod.POST)
    public String addUser(Model model, @Valid User user, BindingResult bindingResult) {
        // if any validation error occurs
        if(bindingResult.hasErrors()){
            model.addAttribute("successMessage", "Something went wrong");
            model.addAttribute("bindingResult", bindingResult);
            return "auth/register";
        }

        // check if user is present
        List<Object> userPresentObj = userService.isUserPresent(user);
        if((Boolean) userPresentObj.get(0)){
            model.addAttribute("successMessage", userPresentObj.get(1));
            return "auth/register";
        }

        // set role and set enabled to false if the role is "CUSTOMER"
        user.setRole(user.getRole());

        // save user to db
        userService.save(user);
        if (!(user.getRole() == Role.ADMIN)) {
            model.addAttribute("successMessage", user.getRole().toString()+" registered successfully! Please wait for an admin to activate your account");
        }

        return "auth/login";
    }
}
