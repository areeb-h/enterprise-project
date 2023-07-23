package com.example.enterpriseproject.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "rejected_orders")
public class RejectedOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long orderId;

    private Long driverId;

    public RejectedOrder() {
    }

    public RejectedOrder(Long orderId, Long driverId) {
        this.orderId = orderId;
        this.driverId = driverId;
    }

    // getters and setters

    public Long getId() {
        return id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public Long getDriverId() {
        return driverId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }

    @Override

    public String toString() {
        return "RejectedOrder [driverId=" + driverId + ", id=" + id + ", orderId=" + orderId + "]";
    }

}
