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
    List<Order> findAllByCustomerAndOrderStatus(Customer customer, Boolean orderStatus);

    Optional<Order> findOrderByDriver(Driver driver);
    @NotNull Optional<Order> findById(@NotNull Long id);
}
