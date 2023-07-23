package com.example.enterpriseproject.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.enterpriseproject.model.Driver;
import com.example.enterpriseproject.model.Order;
import com.example.enterpriseproject.model.OrderStatus;
import com.example.enterpriseproject.model.Vehicle;
import com.example.enterpriseproject.service.OrderServiceImplementation;
import com.example.enterpriseproject.service.UserServiceImplementation;
import org.springframework.web.client.RestTemplate;

// WILL HANDLE THE MAPPINGS FOR ONLY WHAT THE DRIVER CAN ACCESS

@Controller
public class DriverController {

    @Autowired
    OrderServiceImplementation orderServiceImpementation;

    @Autowired
    UserServiceImplementation userServiceImplementation;

    @RequestMapping(value = { "/driver/dashboard" }, method = RequestMethod.GET)
    public String driverHome(Model model) {
        model.addAttribute("title", "dashboard");
        return "driver/dashboard";
    }

    @GetMapping("/driver/orders")
    public String book(Model model) {
        // Driver driver = userServiceImplementation.getCurrentUser().getDriver();

        List<Order> pending_orders = orderServiceImpementation.findOrdersByDriverAndOrderStatus(
                userServiceImplementation.getCurrentUser().getDriver(),
                OrderStatus.UNASSIGNED);
        List<Order> accepted_orders = orderServiceImpementation.findOrdersByDriverAndOrderStatus(
                userServiceImplementation.getCurrentUser().getDriver(),
                OrderStatus.ASSIGNED);
        List<Order> completed_orders = orderServiceImpementation.findOrdersByDriverAndOrderStatus(
                userServiceImplementation.getCurrentUser().getDriver(),
                OrderStatus.COMPLETED);

        model.addAttribute("title", "orders");
        model.addAttribute("pending_orders", pending_orders);
        model.addAttribute("accepted_orders", accepted_orders);
        model.addAttribute("completed_orders", completed_orders);

        return "driver/orders";
    }

    // For drivers to accept orders
    @GetMapping("/driver/orders/accept/{id}")
    public String acceptOrder(@PathVariable("id") Long id) {
        Order order = orderServiceImpementation.findOrderById(id);
        Driver driver = userServiceImplementation.getCurrentUser().getDriver();
        Vehicle vehicle = driver.getVehicle();

        order.setDriver(driver);
        order.setVehicle(vehicle);
        order.setOrderStatus(OrderStatus.ASSIGNED);

        orderServiceImpementation.assignOrder(id, driver.getId());

        return "redirect:/driver/orders";
    }

    // For drivers to reject orders
    @GetMapping("/driver/orders/reject/{id}")
    public String rejectOrder(@PathVariable("id") Long id) {
        Order order = orderServiceImpementation.findOrderById(id);
        orderServiceImpementation.rejectOrder(order);
        return "redirect:/driver/orders";
    }

    // For drivers to mark completed orders
    @GetMapping("/driver/orders/complete/{id}")
    public ResponseEntity<byte[]> completeOrder(@PathVariable("id") Long id) throws IOException {
        Order order = orderServiceImpementation.findOrderById(id);

        order.setOrderStatus(OrderStatus.COMPLETED);

        byte[] pdf = generateInvoice(order).getBody();
        order.setInvoice(pdf);

        orderServiceImpementation.completeOrder(id);
        //save pdf to byte variable
        return generateInvoice(order);
    }


    //generate invoice by calling api
    public ResponseEntity<byte[]> generateInvoice(Order order) throws IOException {

        //init variables
        Long orderId = order.getId();
        String customerName = order.getCustomer().getUser().getFirstName() + " " + order.getCustomer().getUser().getFirstName();
        String pickupAddress = order.getPickupAddress();
        double distance = order.getDistance();
        String dropOffAddress = order.getDestinationAddress();
        String driverName = order.getDriver().getUser().getFirstName() + " " + order.getDriver().getUser().getLastName();
        String vehicleType = String.valueOf(order.getVehicle().getVehicleType());
        String vehicleColor = order.getVehicle().getCarColor();
        String licenseNumber = order.getVehicle().getLicenseNumber();
        LocalDateTime dateTime = order.getTime();
        double amount = order.getTotalCost();

        //create json input
        String jsonInput = "{\n" +
                "    \"orderId\": "+orderId+",\n" +
                "    \"distance\": "+distance+",\n" +
                "    \"customerName\": \""+customerName+"\",\n" +
                "    \"pickupAddress\": \""+pickupAddress+"\",\n" +
                "    \"dropOffAddress\": \""+dropOffAddress+"\",\n" +
                "    \"driverName\": \""+driverName+"\",\n" +
                "    \"vehicleType\": \""+vehicleType+"\",\n" +
                "    \"vehicleColor\": \""+vehicleColor+"\",\n" +
                "    \"licenseNumber\": \""+licenseNumber+"\",\n" +
                "    \"dateTime\": \""+dateTime+"\",\n" +
                "    \"amount\": "+amount+"\n" +
                "}";

        //create request
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.valueOf(MediaType.APPLICATION_PDF_VALUE)));
        HttpEntity<String> request = new HttpEntity<>(jsonInput, headers);

        //send request
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<byte[]> response = restTemplate.exchange(
                "http://localhost:8080/api/generateInvoice",
                HttpMethod.POST,
                request,
                byte[].class
        );

        //get response
        if (response.getStatusCode() == HttpStatus.OK) {
            byte[] invoiceData = response.getBody();

            // Set the appropriate response headers
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.setContentType(MediaType.APPLICATION_PDF);
            responseHeaders.setContentDisposition(ContentDisposition.parse("inline; filename=\"invoice.pdf\""));

            // Return the invoice data along with the response headers
            return new ResponseEntity<>(invoiceData, responseHeaders, HttpStatus.OK);
        } else {
            System.err.println("Failed with HTTP error code: " + response.getStatusCode());
        }
        return null;
    }

    //download invoice
    @GetMapping("/orders/download/{id}")
    public ResponseEntity<byte[]> downloadInvoice(@PathVariable("id") String id) throws IOException {
        Order order = orderServiceImpementation.findOrderById(Long.parseLong(id));

        byte[] pdf = order.getInvoice();

        // Set the appropriate response headers
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_PDF);
        responseHeaders.setContentDisposition(ContentDisposition.parse("inline; filename=\"invoice.pdf\""));

        // Return the invoice data along with the response headers
        return new ResponseEntity<>(pdf, responseHeaders, HttpStatus.OK);
    }

    // For drivers to see order details
    @GetMapping("/driver/dashboard/orders/{id}")
    public String updateDriverStatus(Model model, @PathVariable("id") String id) {
        Order order = orderServiceImpementation.findOrderById(Long.parseLong((id)));

        model.addAttribute("order", order);
        return "driver/individual";
    }
}
