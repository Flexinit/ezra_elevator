package com.ezra.elevatorapi.controller;

import com.ezra.elevatorapi.entity.ElevatorRequests;
import com.ezra.elevatorapi.entity.QueryLog;
import com.ezra.elevatorapi.service.QueryLogsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/logs")
public record LogsController(QueryLogsService queryLogsService) {

    @GetMapping
    public Optional<List<QueryLog>> getDatabaseQueryLogs(){
        return queryLogsService.getDatabaseQueryLogs();
    }

    @GetMapping("/elevatorRequests")
    public Optional<List<ElevatorRequests>> getElevatorRequests(){
        return queryLogsService.getElevatorRequests();
    }
}
