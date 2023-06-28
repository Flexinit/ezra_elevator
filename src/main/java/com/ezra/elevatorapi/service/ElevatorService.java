package com.ezra.elevatorapi.service;

import com.ezra.elevatorapi.entity.Elevator;
import com.ezra.elevatorapi.entity.ElevatorLogs;
import com.ezra.elevatorapi.entity.ElevatorRequests;
import com.ezra.elevatorapi.repository.ElevatorRepository;
import com.ezra.elevatorapi.repository.ElevatorRequestsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class ElevatorService {
    @Autowired
    private  ElevatorRepository elevatorRepository;

    @Autowired
    private ElevatorLogs elevatorLogs;
    @Autowired
    private ElevatorRequestsRepository elevatorRequestsRepository;

    public Optional<Elevator> addNewElevator(Elevator elevator) {
        return Optional.of(elevatorRepository.save(elevator));
    }

    public Optional<List<Elevator>> getElevators() {
        return Optional.of(elevatorRepository.findAll());
    }

    @Async
    public Optional<ResponseEntity<Elevator>> requestForElevator(ElevatorRequests elevatorRequestsEntity) {

        //Log the request
        elevatorRequestsRepository.save(elevatorRequestsEntity);
      String elevatorDirection;

    if(elevatorRequestsEntity.getDestinationFloorNumber() > elevatorRequestsEntity.getCurrentFloorNumber()){
        elevatorDirection = "Upwards";
    }else if (elevatorRequestsEntity.getDestinationFloorNumber() < elevatorRequestsEntity.getCurrentFloorNumber()){
        elevatorDirection = "Downwards";
    }else{
        elevatorDirection = "Stationary";
    }


    elevatorLogs.setElevatorId(elevatorRequestsEntity.getElevatorId());
    elevatorLogs.setStatus("Door Open");
    elevatorLogs.setDirection(elevatorDirection);
    elevatorLogs.setCurrentFloorNumber(elevatorRequestsEntity.getCurrentFloorNumber());
    elevatorLogs.setDestinationFloorNumber(elevatorRequestsEntity.getDestinationFloorNumber());
    }

    @Async //This method runs asynchronously to handle elevator operations
    public CompletableFuture<Void> executeElevatorOperations(ElevatorLogs elevatorLog) {
        return CompletableFuture.runAsync(() -> {
            int currentFloorNumber =  elevatorLog.getCurrentFloorNumber();

            //open elevator Door
            elevatorLog.setStatus("OpenDoor");
            log.info("Elevator {} opening Door", elevatorLog.getElevatorId());
            sleep(2, TimeUnit.SECONDS);


            //Close elevator Door
            log.info("Elevator {} Closing Door", elevatorLog.getElevatorId());
            sleep(2, TimeUnit.SECONDS);
            elevatorLog.setStatus("CloseDoor");



            //Check direction of elevator and Navigate floors appropriately
            if(elevatorLog.getDestinationFloorNumber() > elevatorLog.getCurrentFloorNumber()){
                while(elevatorLog.getDestinationFloorNumber() > elevatorLog.getCurrentFloorNumber()){

                    sleep(5, TimeUnit.SECONDS);
                    currentFloorNumber += 1;

                    log.info("Elevator {} At floor Number {}", elevatorLog.getElevatorId(), currentFloorNumber);
                }

            }else if(elevatorLog.getDestinationFloorNumber() < elevatorLog.getCurrentFloorNumber()){

                while(elevatorLog.getDestinationFloorNumber() > elevatorLog.getCurrentFloorNumber()){

                    sleep(5, TimeUnit.SECONDS);
                    currentFloorNumber -= 1;
                    log.info("Elevator {} At floor Number {}", elevatorLog.getElevatorId(), currentFloorNumber);

                }
            }else{

                log.info("Elevator {} Has arrived at the destination floor number", elevatorLog.getElevatorId());

            }




            // Other code logic
        });
    }

    private void sleep(long duration, TimeUnit unit) {
        try {
            unit.sleep(duration);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}
