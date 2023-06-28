package com.ezra.elevatorapi.controller;

import com.ezra.elevatorapi.entity.Building;
import com.ezra.elevatorapi.service.BuildingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/building")
public record BuildingController(BuildingService buildingService) {

    @PostMapping
    public Optional<Building> createNewBuilding(@RequestBody Building building){
       return buildingService.createNewBuilding(building);
    }

    @GetMapping
    public Optional<List<Building>> getBuildings(){
        return buildingService.getBuildings();
    }
}
