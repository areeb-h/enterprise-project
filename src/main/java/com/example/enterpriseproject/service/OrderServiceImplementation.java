package com.example.enterpriseproject.service;

import com.example.enterpriseproject.model.Customer;
import com.example.enterpriseproject.model.Driver;
import com.example.enterpriseproject.model.Order;
import com.example.enterpriseproject.model.OrderStatus;
import com.example.enterpriseproject.model.Vehicle;
import com.example.enterpriseproject.model.VehicleType;
import com.example.enterpriseproject.repository.DriverRepository;
import com.example.enterpriseproject.repository.OrderRepository;
import com.example.enterpriseproject.repository.VehicleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    public void save(Order order) {
        List<Vehicle> vehicles = vehicleRepository.findAllByVehicleType(order.getVehicleType());

        List<Driver> availableDrivers = new ArrayList<Driver>();

        for (Vehicle vehicle : vehicles) {
            if (vehicle.getDriver().getAvailabilityStatus()) {
                availableDrivers.add(vehicle.getDriver());
            }
        }

        Collections.sort(availableDrivers, (d1, d2) -> {
            if (d1.getLastCompletedOrderTime() == null && d2.getLastCompletedOrderTime() == null) {
                return 0;
            } else if (d1.getLastCompletedOrderTime() == null) {
                return -1;
            } else if (d2.getLastCompletedOrderTime() == null) {
                return 1;
            } else {
                return d1.getLastCompletedOrderTime().compareTo(d2.getLastCompletedOrderTime());
            }
        });

        Driver driver = availableDrivers.get(0);

        order.setDriver(driver);
        order.setVehicle(driver.getVehicle());

        orderRepository.save(order);

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

                Driver driver = order.getDriver();

                order.setStatus(OrderStatus.COMPLETED);
                driver.setLastCompletedOrderTime(LocalDateTime.now());

                orderRepository.save(order);

                return "Order completed";
            } else {
                return "Order not found";
            }
        } catch (Exception e) {
            throw e;
        }
    }

}
