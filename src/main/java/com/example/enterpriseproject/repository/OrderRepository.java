package com.example.enterpriseproject.repository;

import com.example.enterpriseproject.model.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//repository to communicate with db
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByCustomerAndStatus(Customer customer, OrderStatus status);

    Optional<Order> findOrderByDriver(Driver driver);

    List<Order> findAllByDriverAndStatus(Driver driver, OrderStatus status);

    List<Order> findByStatus(OrderStatus status);

    @NotNull
    Optional<Order> findById(@NotNull Long id);

    Optional<Order> findByIdAndStatus(Long id, OrderStatus orderStatus);
}
