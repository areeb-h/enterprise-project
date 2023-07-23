package com.example.enterpriseproject.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.example.enterpriseproject.audit.Auditable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "drivers")
public class Driver extends Auditable<String> {

    // TABLE COLUMNS
    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // foreign key driver_id references id in users table
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "license_number", nullable = false, length = 45)
    private String licenseNumber;

    @Column(name = "license_expiry_date", nullable = false, length = 45)
    private LocalDate licenseExpiryDate;

    @Column(name = "license_issue_date", nullable = false, length = 45)
    private LocalDate licenseIssueDate;

    // RELATIONSHIPS

    // primary key id references driver_id in vehicles table
    @OneToOne(mappedBy = "driver")
    private Vehicle vehicle;

    @OneToMany(mappedBy = "driver")
    private List<Order> order;

    @Column(name = "availability_status")
    private boolean availabilityStatus;

    @Column(name = "last_assinged_time")
    private LocalDateTime lastAssignedTime;

    // GETTERS AND SETTERS

    public List<Order> getOrders() {
        return order;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public LocalDate getLicenseExpiryDate() {
        return licenseExpiryDate;
    }

    public void setLicenseExpiryDate(LocalDate licenseExpiryDate) {
        this.licenseExpiryDate = licenseExpiryDate;
    }

    public LocalDate getLicenseIssueDate() {
        return licenseIssueDate;
    }

    public void setLicenseIssueDate(LocalDate licenseIssueDate) {
        this.licenseIssueDate = licenseIssueDate;
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

    public void setLastAssignedTime(LocalDateTime lastCompletedOrderTime) {
        this.lastAssignedTime = lastCompletedOrderTime;
    }

    public LocalDateTime getLastAssignedTime() {
        return lastAssignedTime;
    }

}
