package com.ezra.elevatorapi.repository;

import com.ezra.elevatorapi.entity.Floor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface FloorRepository extends JpaRepository<Floor, Long> {
}