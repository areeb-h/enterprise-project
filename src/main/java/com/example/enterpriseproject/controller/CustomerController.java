package com.example.enterpriseproject.controller;

import com.example.enterpriseproject.model.Customer;
import com.example.enterpriseproject.model.Order;
import com.example.enterpriseproject.model.OrderStatus;
import com.example.enterpriseproject.service.OrderServiceImplementation;
import com.example.enterpriseproject.service.UserServiceImplementation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.ui.Model;

import java.security.Principal;
import java.util.List;

// WILL HANDLE THE MAPPINGS FOR THE CUSTOMER

@Controller
public class CustomerController {

    @Autowired
    OrderServiceImplementation orderServiceImpementation;

    @Autowired
    UserServiceImplementation userServiceImplementation;

    @Qualifier("userDetailsService")
    @Autowired
    private UserDetailsService userDetailsService;

    @RequestMapping(value = { "/customer/dashboard" }, method = RequestMethod.GET)
    public String customerHome(Model model) {
        // Get the currently logged-in user
        Customer customer = userServiceImplementation.getCurrentUser().getCustomer();

        List<Order> pending_orders = orderServiceImpementation.findOrdersByCustomerAndOrderStatus(customer,
                OrderStatus.UNASSIGNED);
        List<Order> accepted_orders = orderServiceImpementation.findOrdersByCustomerAndOrderStatus(customer,
                OrderStatus.ASSIGNED);
        List<Order> cancelled_Orders = orderServiceImpementation.findOrdersByCustomerAndOrderStatus(customer,
                OrderStatus.CANCELLED);
        List<Order> completed_orders = orderServiceImpementation.findOrdersByCustomerAndOrderStatus(customer,
                OrderStatus.COMPLETED);

        model.addAttribute("title", "dashboard");
        model.addAttribute("cancelled_orders", cancelled_Orders);
        model.addAttribute("pending_orders", pending_orders);
        model.addAttribute("accepted_orders", accepted_orders);
        model.addAttribute("completed_orders", completed_orders);
        return "customer/dashboard";
    }

    @RequestMapping(value = { "/customer/book" }, method = RequestMethod.GET)
    public String book(Model model) {
        model.addAttribute("title", "book");
        model.addAttribute("order", new Order());
        return "customer/book";
    }

    @RequestMapping(value = { "/customer/book" }, method = RequestMethod.POST)
    public String addOrder(Model model, @Valid Order order, BindingResult bindingResult, Principal principal) {

        // if any validation error occurs
        model.addAttribute("title", "book");

        if (bindingResult.hasErrors()) {
            model.addAttribute("errorMessage", "Something went wrong");
            model.addAttribute("bindingResult", bindingResult);
            return "customer/book";
        }

        // Get the currently logged-in user
        Customer customer = userServiceImplementation.getCurrentUser().getCustomer();

        order.setStatus(OrderStatus.UNASSIGNED);
        order.setCost(0);
        order.setCustomer(customer);

        // save order to db
        String error = orderServiceImpementation.save(order);

        if (error == "Cannot find a driver. Please try again later" || error == "No driver available") {
            model.addAttribute("errorMessage", error);
        } else
            model.addAttribute("successMessage", "Your order has been placed!");

        // Clear the submitted order from the model
        model.addAttribute("order", new Order());

        return "customer/book";
    }

    // For customers to see order details
    @GetMapping("/customer/dashboard/orders/{id}")
    public String updateDriverStatus(Model model, @PathVariable("id") String id) {
        Order order = orderServiceImpementation.findOrderById(Long.parseLong((id)));

        model.addAttribute("order", order);
        return "customer/individual";
    }

    // For Customers to cancel accepted orders
    @GetMapping("/Customer/dashboard/orders/cancel/{id}")
    public String cancelOrder(@PathVariable("id") Long id) {
        Order order = orderServiceImpementation.findOrderById(id);

        order.setOrderStatus(OrderStatus.CANCELLED);

        orderServiceImpementation.cancelOrder(id);

        return "redirect:/customer/dashboard";
    }

    
}
