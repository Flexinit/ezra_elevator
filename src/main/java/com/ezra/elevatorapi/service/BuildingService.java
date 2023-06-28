package com.ezra.elevatorapi.service;

import com.ezra.elevatorapi.entity.Building;
import com.ezra.elevatorapi.repository.BuildingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BuildingService {

    @Autowired
    private BuildingRepository buildingRepository;
    public Optional<Building> createNewBuilding(Building building) {
        return Optional.of(buildingRepository.save(building));
    }

    public Optional<List<Building>> getBuildings() {
        return Optional.of(buildingRepository.findAll());
    }
}
