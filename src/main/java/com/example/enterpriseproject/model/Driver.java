package com.example.enterpriseproject.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "drivers")
public class Driver {

    //TABLE COLUMNS
    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //RELATIONSHIPS

    //foreign key driver_id references id in users table
    @OneToOne
    @JoinColumn(name = "driver_id", referencedColumnName = "id")
    private User user;

    //primary key id references driver_id in vehicles table
    @OneToOne(mappedBy = "vehicles")
    private Vehicle vehicle;

    @OneToMany(mappedBy = "orders")
    private List<Order> order;

    public Driver() {
    }

    //GETTERS AND SETTERS
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
