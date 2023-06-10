package com.example.enterpriseproject.model;

public enum OrderStatus {
    UNASSIGNED("Unassigned"),
    ASSIGNED("Assigned"),
    IN_PROGRESS("In Progress"),
    COMPLETED("Completed"),
    CANCELLED("Cancelled");
private final String value;
private OrderStatus(String value) {
    this.value = value;
}

public String getValue() {
    return value;
}

}

