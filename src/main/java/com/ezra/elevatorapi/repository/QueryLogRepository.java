package com.ezra.elevatorapi.repository;

import com.ezra.elevatorapi.entity.Building;
import com.ezra.elevatorapi.entity.DatabaseLogsSaver;
import jakarta.persistence.Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface QueryLogRepository extends JpaRepository<DatabaseLogsSaver, UUID> {
}