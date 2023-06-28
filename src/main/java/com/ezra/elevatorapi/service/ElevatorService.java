package com.ezra.elevatorapi.service;

import com.ezra.elevatorapi.entity.Elevator;
import com.ezra.elevatorapi.entity.ElevatorLogs;
import com.ezra.elevatorapi.entity.ElevatorRequests;
import com.ezra.elevatorapi.kafka.KafkaProducerService;
import com.ezra.elevatorapi.repository.ElevatorRepository;
import com.ezra.elevatorapi.repository.ElevatorRequestsRepository;
import com.ezra.elevatorapi.utils.APIUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static com.ezra.elevatorapi.utils.APIUtils.KAFKA_TOPIC;

@Slf4j
@Service
public class ElevatorService {
    @Autowired
    private ConfigurableApplicationContext applicationContext;

    @Autowired
    private  ElevatorRepository elevatorRepository;

    @Autowired
    private  KafkaTemplate<String, ElevatorLogs> kafkaTemplate;

    @Autowired
    private KafkaProducerService kafkaProducerService;
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
    public Optional<ResponseEntity<ElevatorRequests>> requestForElevator(ElevatorRequests elevatorRequestsEntity) {

        //Log the request
        elevatorRequestsEntity.setRequestedBy(APIUtils.getLoggedInUser.get());

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
    elevatorLogs.setDirection(elevatorDirection);
    elevatorLogs.setCurrentFloorNumber(elevatorRequestsEntity.getCurrentFloorNumber());
    elevatorLogs.setDestinationFloorNumber(elevatorRequestsEntity.getDestinationFloorNumber());
    elevatorLogs.setBuildingId(elevatorRequestsEntity.getBuildingId());

    initiateElevator.accept(elevatorLogs);

    return Optional.of(new ResponseEntity<>(elevatorRequestsEntity, HttpStatus.OK));
    }


    public Consumer<ElevatorLogs> initiateElevator = elevatorLog -> {
        CompletableFuture<Void> future = executeElevatorOperations(elevatorLog);

        // Handle the completion of the CompletableFuture
        future.whenComplete((result, exception) -> {
            if (exception != null) {
                // Handle any exceptions that occurred during execution
                exception.printStackTrace();
            } else {
                // Execution completed successfully
                System.out.println("Elevator operations completed successfully");
            }
        });
        try {
            // Wait for the CompletableFuture to complete
            future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    };




    //This method runs asynchronously to handle elevator operations
    @Async
    public CompletableFuture<Void> executeElevatorOperations(ElevatorLogs elevatorLog) {
        return CompletableFuture.runAsync(() -> {
            int currentFloorNumber =  elevatorLog.getCurrentFloorNumber();

            //open elevator Door
            elevatorLog.setStatus("OpenDoor");
            log.info("Elevator {} opening Door", elevatorLog.getElevatorId());
            sleep.accept(2, TimeUnit.SECONDS);

            //Publish to Kafka
            postElevatorStatusToApacheKafka.accept(elevatorLog);


            //Close elevator Door
            log.info("Elevator {} Closing Door", elevatorLog.getElevatorId());
            sleep.accept(2, TimeUnit.SECONDS);
            elevatorLog.setStatus("CloseDoor");
            //Publish to Kafka
            postElevatorStatusToApacheKafka.accept(elevatorLog);

            //Check direction of elevator and Navigate floors appropriately
            if(elevatorLog.getDestinationFloorNumber() > elevatorLog.getCurrentFloorNumber()){
                while(elevatorLog.getDestinationFloorNumber() > elevatorLog.getCurrentFloorNumber()){

                    sleep.accept(5, TimeUnit.SECONDS);
                    currentFloorNumber += 1;
                    //Publish to Kafka
                    postElevatorStatusToApacheKafka.accept(elevatorLog);
                    log.info("Elevator {} At floor Number {}", elevatorLog.getElevatorId(), currentFloorNumber);
                }

            }else if(elevatorLog.getDestinationFloorNumber() < elevatorLog.getCurrentFloorNumber()){

                while(elevatorLog.getDestinationFloorNumber() > elevatorLog.getCurrentFloorNumber()){

                    sleep.accept(5, TimeUnit.SECONDS);
                    currentFloorNumber -= 1;
                    log.info("Elevator {} At floor Number {}", elevatorLog.getElevatorId(), currentFloorNumber);

                    //Publish to Kafka
                    postElevatorStatusToApacheKafka.accept(elevatorLog);
                }
            }else{

                //Publish to Kafka
                postElevatorStatusToApacheKafka.accept(elevatorLog);
                log.info("Elevator {} Has arrived at the destination floor number", elevatorLog.getElevatorId());

            }

        });
    }




    private BiConsumer<Integer,TimeUnit> sleep  = (duration, unit)-> {
        try {
            unit.sleep(duration);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    };

    public Consumer<ElevatorLogs> postElevatorStatusToApacheKafka = logs ->{
        //KafkaProducerService producerService = applicationContext.getBean(KafkaProducerService.class);
        kafkaProducerService.publishElevatorStatus(KAFKA_TOPIC, logs);
        applicationContext.close();
    };

}
