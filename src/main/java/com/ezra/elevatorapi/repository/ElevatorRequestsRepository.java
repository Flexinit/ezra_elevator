package com.ezra.elevatorapi.repository;

import com.ezra.elevatorapi.entity.ElevatorRequests;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ElevatorRequestsRepository extends JpaRepository<ElevatorRequests, Long> {
}