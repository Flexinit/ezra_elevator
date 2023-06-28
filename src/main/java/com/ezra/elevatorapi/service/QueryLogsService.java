package com.ezra.elevatorapi.service;

import com.ezra.elevatorapi.entity.ElevatorRequests;
import com.ezra.elevatorapi.entity.QueryLog;
import com.ezra.elevatorapi.repository.ElevatorRequestsRepository;
import com.ezra.elevatorapi.repository.QueryLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QueryLogsService {

    @Autowired
    private  QueryLogRepository queryLogRepository;
    @Autowired
    private ElevatorRequestsRepository elevatorRequestsRepository;

    public Optional<List<QueryLog>> getDatabaseQueryLogs() {
        return Optional.of(queryLogRepository.findAll());
    }

    public Optional<List<ElevatorRequests>> getElevatorRequests() {
        return Optional.of(elevatorRequestsRepository.findAll());
    }
}
