package com.ezra.elevatorapi.repository;

import com.ezra.elevatorapi.entity.QueryLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface QueryLogRepository extends JpaRepository<QueryLog, Long> {
}