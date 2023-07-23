package com.example.enterpriseproject.model;

import java.time.LocalDate;

import com.example.enterpriseproject.audit.Auditable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

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
    private LocalDate licenseExpiryDate;

    @Column(name = "license_issue_date", nullable = false, length = 45)
    private LocalDate licenseIssueDate;

    // initialized an enum column for vehicle type
    @Enumerated(EnumType.STRING)
    private VehicleType vehicleType;

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

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public VehicleType getVehicleType() {
        return this.vehicleType;
    }

    public void setVehicleType(VehicleType string) {
        this.vehicleType = string;
    }
}
