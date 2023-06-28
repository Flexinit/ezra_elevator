package com.ezra.elevatorapi.controller;

import com.ezra.elevatorapi.entity.Elevator;
import com.ezra.elevatorapi.entity.ElevatorRequests;
import com.ezra.elevatorapi.service.ElevatorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/elevator")
public record ElevatorController(ElevatorService elevatorService) {

    @PostMapping
    public Optional<Elevator> addNewElevator(@RequestBody Elevator elevator){
        return elevatorService.addNewElevator(elevator);
    }

    @PostMapping("/request")
    public Optional<ResponseEntity<ElevatorRequests>> requestForElevator(@RequestBody ElevatorRequests elevatorRequestsEntity){
        return elevatorService.requestForElevator(elevatorRequestsEntity);
    }

    @GetMapping
    public Optional<List<Elevator>> getElevators(){
        return elevatorService.getElevators();
    }
}
