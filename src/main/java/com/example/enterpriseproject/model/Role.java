package com.example.enterpriseproject.model;

public enum Role {
    //define enums
    CUSTOMER("User"),
    ADMIN("Admin"),
    DRIVER("Driver");
    private final String value;

    //setter for role
    private Role(String value) {
        this.value = value;
    }

    //getter for value of enum
    public String getValue() {
        return value;
    }

}
