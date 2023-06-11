package com.example.enterpriseproject.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

import com.example.enterpriseproject.audit.Auditable;

@Entity
@Table(name = "vehicles")
public class Vehicle extends Auditable<String> {

    // TABLE COLUMNS
    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "car_name", nullable = false, length = 45)
    private String carName;

    @Column(name = "car_color", nullable = false, length = 45)
    private String carColor;

    @Column(name = "license_number", nullable = false, length = 45)
    private String licenseNumber;

    @Column(name = "license_expiry_date", nullable = false, length = 45)
    private LocalDateTime licenseExpiryDate;

    @Column(name = "license_issue_date", nullable = false, length = 45)
    private LocalDateTime licenseIssueDate;

    // RELATIONSHIPS

    // foreign key driver_id references id in drivers table
    @OneToOne
    @JoinColumn(name = "driver_id", referencedColumnName = "id")
    private Driver driver;

    /*
     * @OneToMany(mappedBy = "orders")
     * private List<Order> order;
     */

    // GETTERS AND SETTERS

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getCarColor() {
        return carColor;
    }

    public void setCarColor(String carColor) {
        this.carColor = carColor;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public LocalDateTime getLicenseExpiryDate() {
        return licenseExpiryDate;
    }

    public void setLicenseExpiryDate(LocalDateTime licenseExpiryDate) {
        this.licenseExpiryDate = licenseExpiryDate;
    }

    public LocalDateTime getLicenseIssueDate() {
        return licenseIssueDate;
    }

    public void setLicenseIssueDate(LocalDateTime licenseIssueDate) {
        this.licenseIssueDate = licenseIssueDate;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }
}
