package com.example.enterpriseproject.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

import com.example.enterpriseproject.audit.Auditable;

@Entity
@Table(name = "orders")
public class Order extends Auditable<String> {

    // TABLE COLUMNS
    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cost", nullable = false, length = 45)
    private double cost;

    @Column(name = "destination_address", nullable = false, length = 150)
    private String destinationAddress;

    @Column(name = "pickup_address", nullable = false, length = 150)
    private String pickupAddress;

    @Column(name = "time", nullable = false, length = 45)
    private LocalDateTime time;

    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.UNASSIGNED;

    // RELATIIONSHIPS

    // foreign key customer_id references id in customers table
    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    // foreign key driver_id references id in drivers table
    @ManyToOne
    @JoinColumn(name = "driver_id", referencedColumnName = "id")
    private Driver driver;

    // foreign key vehicle_id references id in vehicles table
    @ManyToOne
    @JoinColumn(name = "vehicle_id", referencedColumnName = "id")
    private Vehicle vehicle;

    // GETTERS AND SETTERS
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDestinationAddress() {
        return destinationAddress;
    }

    public void setDestinationAddress(String destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    public String getPickupAddress() {
        return pickupAddress;
    }

    public void setPickupAddress(String pickupAddress) {
        this.pickupAddress = pickupAddress;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public OrderStatus getStatus() {
        return this.status;
    }

    public void setStatus(OrderStatus string) {
        this.status = string;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    // get and set customers
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public void setOrderStatus(OrderStatus assigned) {
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
}
