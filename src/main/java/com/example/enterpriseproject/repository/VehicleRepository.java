package com.example.enterpriseproject.repository;

import com.example.enterpriseproject.model.Vehicle;
import com.example.enterpriseproject.model.VehicleType;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    List<Vehicle> findAllByVehicleType(VehicleType vehicleType);

}
