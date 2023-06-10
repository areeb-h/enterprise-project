package com.example.enterpriseproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.enterpriseproject.model.Driver;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {

    // find by id
    Driver findById(long id);

}
