package com.example.enterpriseproject.model;

public enum VehicleType {
    //define enums
    CAR("Car"),
    CYCLE("Cycle"),
    VAN("Van"),
    PICKUP("Pickup"),
    LPICKUP("Large Pickup");

    private final String value;

    //setter for role
    private VehicleType(String value) {
        this.value = value;
    }

    //getter for value of enum
    public String getValue() {
        return value;
    }

}
