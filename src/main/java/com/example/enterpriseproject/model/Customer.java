package com.example.enterpriseproject.model;

import jakarta.persistence.*;

import java.util.List;

import com.example.enterpriseproject.audit.Auditable;

@Entity
@Table(name = "customers")
public class Customer extends Auditable<String> {

    // TABLE COLUMNS
    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // RELATIONSHIPS
    // foreign key references id in users table
    @OneToOne
    @JoinColumn(name = "user_id", insertable = true, updatable = false)
    private User user;

    @OneToMany(mappedBy = "customer")
    private List<Order> order;

    @Column(name = "address", length = 45)
    private String address;

    // GETTERS AND SETTERS

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // getter for user
    public User getUser() {
        return user;
    }

    // setter for user
    public void setUser(User user) {
        System.out.println(user.getId());
        this.user = user;
    }

    // getter for address

    public String getAddress() {
        return address;
    }

    // setter for address

    public void setAddress(String address) {
        this.address = address;
    }

    // getter for order
    public List<Order> getOrder() {
        return order;
    }

}
