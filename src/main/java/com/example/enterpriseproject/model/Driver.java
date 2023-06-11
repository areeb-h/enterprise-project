package com.example.enterpriseproject.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "drivers")
public class Driver {

    // TABLE COLUMNS
    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // RELATIONSHIPS

    // foreign key driver_id references id in users table
    @OneToOne
    @JoinColumn(name = "driver_id")
    private User user;

    // primary key id references driver_id in vehicles table
    @OneToOne(mappedBy = "driver")
    private Vehicle vehicle;

    @OneToMany(mappedBy = "driver")
    private List<Order> order;

    @Column(name = "availability_status")
    private boolean availabilityStatus;

    // GETTERS AND SETTERS

    public List<Order> getOrders() {
        return order;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public List<Order> getOrder() {
        return order;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAvailabilityStatus(boolean availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    }

    public boolean getAvailabilityStatus() {
        return availabilityStatus;
    }

}
