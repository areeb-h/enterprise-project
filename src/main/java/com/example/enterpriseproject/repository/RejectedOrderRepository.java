package com.example.enterpriseproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.enterpriseproject.model.RejectedOrder;

@Repository
public interface RejectedOrderRepository extends JpaRepository<RejectedOrder, Long> {

    // RejectedOrder save(Long orderId, Long driverId);

    List<RejectedOrder> findByOrderId(Long orderId);

}
