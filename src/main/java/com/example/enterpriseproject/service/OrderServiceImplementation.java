package com.example.enterpriseproject.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.enterpriseproject.model.Customer;
import com.example.enterpriseproject.model.Driver;
import com.example.enterpriseproject.model.Order;
import com.example.enterpriseproject.model.OrderStatus;
import com.example.enterpriseproject.model.RejectedOrder;
import com.example.enterpriseproject.model.Vehicle;
import com.example.enterpriseproject.model.VehicleType;
import com.example.enterpriseproject.repository.DriverRepository;
import com.example.enterpriseproject.repository.OrderRepository;
import com.example.enterpriseproject.repository.RejectedOrderRepository;
import com.example.enterpriseproject.repository.VehicleRepository;

//UserService will be implemented using this
@Service
public class OrderServiceImplementation implements OrderService {

    // access the userRepository methods to communicate with db
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    DriverRepository driverRepository;

    @Autowired
    VehicleRepository vehicleRepository;

    @Autowired
    RejectedOrderRepository rejectedOrderRepository;

    public String save(Order order) {
        order.setCreatedAt(LocalDateTime.now());
        calculateTotalCost(order);
        return assignDriver(order);
    }

    public Order calculateTotalCost(Order order) {
        double cost = 0;
        double vat = 0;
        double totalCost = 0;

        if (order.getVehicleType() == VehicleType.CAR) {
            cost = 25;
        } else if (order.getVehicleType() == VehicleType.CYCLE) {
            cost = 20;
        } else if (order.getVehicleType() == VehicleType.VAN) {
            cost = 40;
        } else if (order.getVehicleType() == VehicleType.LPICKUP) {
            cost = 50;
        } else if (order.getVehicleType() == VehicleType.PICKUP) {
            cost = 60;
        }

        vat = 8;
        totalCost = cost + (cost * vat / 100);

        order.setCost(cost);
        order.setVat(vat);
        order.setTotalCost(totalCost);

        return order;
    }

    public String assignDriver(Order order) {

        if (order.getStatus() != OrderStatus.UNASSIGNED) {
            return "Order already assigned";
        }

        if (order.getCreatedAt().plusMinutes(2).isBefore(LocalDateTime.now())) {
            order.setStatus(OrderStatus.CANCELLED);
            orderRepository.save(order);

            System.out.println("Order " + order.getId() + " cancelled");
            return "Cannot find a driver. Please try again later";
        }

        List<Vehicle> vehicles = vehicleRepository.findAllByVehicleType(order.getVehicleType());

        List<Driver> availableDrivers = new ArrayList<Driver>();

        for (Vehicle vehicle : vehicles) {

            availableDrivers.add(vehicle.getDriver());

        }

        if (availableDrivers.size() == 0) {
            return "No driver available";
        }

        Collections.sort(availableDrivers, (d1, d2) -> {
            if (d1.getLastAssignedTime() == null && d2.getLastAssignedTime() == null) {
                return 0;
            } else if (d1.getLastAssignedTime() == null) {
                return -1;
            } else if (d2.getLastAssignedTime() == null) {
                return 1;
            } else {
                return d1.getLastAssignedTime().compareTo(d2.getLastAssignedTime());
            }

        });

        Driver driver = availableDrivers.get(0);

        if (order.getDriver() != null && order.getDriver().getId() == driver.getId()) {
            order.setStatus(OrderStatus.CANCELLED);
            orderRepository.save(order);

            System.out.println("Order " + order.getId() + " cancelled");
            return "Cannot find a driver. Please try again later";
        }

        List<RejectedOrder> rejectedOrder = rejectedOrderRepository.findByOrderId(order.getId());

        if (rejectedOrder != null) {

            for (RejectedOrder rejected : rejectedOrder) {
                if (rejected.getDriverId() == driver.getId()) {
                    order.setStatus(OrderStatus.CANCELLED);
                    orderRepository.save(order);

                    System.out.println("Order " + order.getId() + " cancelled");
                    return "Order has been cancelled";
                }
            }

        }

        driver.setLastAssignedTime(LocalDateTime.now());
        driverRepository.save(driver);

        order.setDriver(driver);
        order.setVehicle(driver.getVehicle());

        orderRepository.save(order);

        System.out.println("Order " + order.getId() + " assigned to driver " + driver.getId());

        return "Order assigned to driver " + driver.getId();

    }

    @Async
    @Scheduled(fixedRate = 70000)
    public void assignDriverToOrder() {
        List<Order> orders = orderRepository.findByStatus(OrderStatus.UNASSIGNED);

        for (Order order : orders) {
            assignDriver(order);
        }
    }

    @Override
    public List<Order> findOrdersByCustomerAndOrderStatus(Customer customer, OrderStatus status) {
        return orderRepository.findAllByCustomerAndStatus(customer, status);
    }

    @Override
    public Order findOrderById(Long id) {
        return orderRepository.findById(id).get();
    }

    @Override
    public List<Order> findOrdersByDriverAndOrderStatus(Driver driver, OrderStatus status) {
        return orderRepository.findAllByDriverAndStatus(driver, status);
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public List<Order> findOrderByOrderStatus(OrderStatus status) {
        return orderRepository.findByStatus(status);
    }

    @Override
    public String assignOrder(Long orderId, Long driverId) {

        // todo: check if driver is available

        try {
            final var existingOrder = orderRepository.findByIdAndStatus(orderId, OrderStatus.UNASSIGNED);

            if (existingOrder.isPresent()) {
                Order order = existingOrder.get();

                Driver driver = driverRepository.findById(driverId).get();

                order.setDriver(driver);

                order.setStatus(OrderStatus.ASSIGNED);

                orderRepository.save(order);

                return "Driver assigned";
            } else {
                return "Already assigned";
            }
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public String cancelOrder(Long orderId) {
        try {
            final var existingOrder = orderRepository.findByIdAndStatus(orderId, OrderStatus.ASSIGNED);

            if (existingOrder.isPresent()) {
                Order order = existingOrder.get();

                order.setStatus(OrderStatus.CANCELLED);

                orderRepository.save(order);

                return "Order cancelled";
            } else {
                return "Order not found";
            }
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public String completeOrder(Long orderId) {
        try {
            final var existingOrder = orderRepository.findByIdAndStatus(orderId, OrderStatus.ASSIGNED);

            if (existingOrder.isPresent()) {
                Order order = existingOrder.get();

                order.setStatus(OrderStatus.COMPLETED);

                orderRepository.save(order);

                return "Order completed";
            } else {
                return "Order not found";
            }
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public String rejectOrder(Order order) {

        RejectedOrder rejectedOrder = new RejectedOrder(order.getId(), order.getDriver().getId());

        rejectedOrderRepository.save(rejectedOrder);

        assignDriver(order);

        return "Order rejected";

    }

}
