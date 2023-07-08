package com.ezra.elevatorapi.service;

import com.ezra.elevatorapi.SocketService.SocketHandler;
import com.ezra.elevatorapi.entity.Elevator;
import com.ezra.elevatorapi.entity.ElevatorLogs;
import com.ezra.elevatorapi.entity.ElevatorRequests;
import com.ezra.elevatorapi.kafka.KafkaProducerService;
import com.ezra.elevatorapi.repository.ElevatorLogsRepository;
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

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static com.ezra.elevatorapi.utils.APIUtils.KAFKA_TOPIC;

@Slf4j
@Service
public class ElevatorService {
    @Autowired
    private ElevatorLogsRepository elevatorLogsRepository;


    @Autowired
    private ConfigurableApplicationContext applicationContext;

    @Autowired
    private  ElevatorRepository elevatorRepository;

    @Autowired
    private  KafkaTemplate<String, ElevatorLogs> kafkaTemplate;

    @Autowired
    private KafkaProducerService kafkaProducerService;

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


    ElevatorLogs elevatorLogs = new ElevatorLogs();
    elevatorLogs.setElevatorId(elevatorRequestsEntity.getElevatorId());
    elevatorLogs.setDirection(elevatorDirection);
    elevatorLogs.setCurrentFloorNumber(elevatorRequestsEntity.getCurrentFloorNumber());
    elevatorLogs.setDestinationFloorNumber(elevatorRequestsEntity.getDestinationFloorNumber());
    elevatorLogs.setBuildingId(elevatorRequestsEntity.getBuildingId());

    //initiateElevator.accept(elevatorLogs);

        //new Thread(() -> runElevatorProcess(elevatorLogs)).start();
        runElevatorProcess(elevatorLogs);



    return Optional.of(new ResponseEntity<>(elevatorRequestsEntity, HttpStatus.OK));
    }


   /* public Consumer<ElevatorLogs> initiateElevator = elevatorLog -> {
        CompletableFuture<Void> future = executeElevatorOperations(elevatorLog);

        // Handle the completion of the CompletableFuture
        future.whenComplete((result, exception) -> {
            if (exception != null) {
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
    };*/




    //This method runs asynchronously to handle elevator operations
    /*@Async
    public CompletableFuture<Void> executeElevatorOperations(ElevatorLogs elevatorLog) {
        return CompletableFuture.runAsync(() -> {
            try {


                int currentFloorNumber = elevatorLog.getCurrentFloorNumber();

                //open elevator Door
                elevatorLog.setStatus("OpenDoor");
                elevatorLogsRepository.save(elevatorLog);
                log.info("Elevator {} opening Door", elevatorLog.getElevatorId());
                sleep.accept(2, TimeUnit.SECONDS);

                //Publish to Kafka
                //postElevatorStatusToApacheKafka.accept(elevatorLog);

                socketHandler.sendMessage(elevatorLog.toString());

                //Close elevator Door
                log.info("Elevator {} Closing Door", elevatorLog.getElevatorId());
                sleep.accept(2, TimeUnit.SECONDS);
                elevatorLog.setStatus("CloseDoor");
                elevatorLogsRepository.save(elevatorLog);

                //Publish to Kafka
                //postElevatorStatusToApacheKafka.accept(elevatorLog);
                socketHandler.sendMessage(elevatorLog.toString());

                //Check direction of elevator and Navigate floors appropriately
                if (elevatorLog.getDestinationFloorNumber() > elevatorLog.getCurrentFloorNumber()) {
                    while (elevatorLog.getDestinationFloorNumber() > elevatorLog.getCurrentFloorNumber()) {

                        sleep.accept(5, TimeUnit.SECONDS);
                        currentFloorNumber += 1;
                        elevatorLog.setCurrentFloorNumber(currentFloorNumber += 1);
                        elevatorLogsRepository.save(elevatorLog);

                        //Publish to Kafka
                        // postElevatorStatusToApacheKafka.accept(elevatorLog);
                        socketHandler.sendMessage(elevatorLog.toString());

                        log.info("Elevator {} At floor Number {}", elevatorLog.getElevatorId(), currentFloorNumber);
                    }

                } else if (elevatorLog.getDestinationFloorNumber() < elevatorLog.getCurrentFloorNumber()) {

                    while (elevatorLog.getDestinationFloorNumber() > elevatorLog.getCurrentFloorNumber()) {

                        sleep.accept(5, TimeUnit.SECONDS);
                        elevatorLog.setCurrentFloorNumber(currentFloorNumber -= 1);
                        log.info("Elevator {} At floor Number {}", elevatorLog.getElevatorId(), currentFloorNumber);


                        elevatorLogsRepository.save(elevatorLog);
                        //Publish to Kafka
                        // postElevatorStatusToApacheKafka.accept(elevatorLog);
                        socketHandler.sendMessage(elevatorLog.toString());
                    }
                } else {

                    //Publish to Kafka
                    elevatorLogsRepository.save(elevatorLog);
                    //postElevatorStatusToApacheKafka.accept(elevatorLog);
                    socketHandler.sendMessage(elevatorLog.toString());
                    log.info("Elevator {} Has arrived at the destination floor number", elevatorLog.getElevatorId());

                }
            }catch (Exception ex){
                ex.printStackTrace();
            }
        });
    }
*/

    @Async
    //@Transactional
    public void runElevatorProcess(ElevatorLogs elevatorLog){
        try{
        //socketHandler.start();
        int currentFloorNumber = elevatorLog.getCurrentFloorNumber();

        //open elevator Door
        elevatorLog.setStatus("OpenDoor");
        elevatorLog.setId(ThreadLocalRandom.current().nextLong(100));
        elevatorLogsRepository.save(elevatorLog);
        log.info("Elevator {} opening Door", elevatorLog.getElevatorId());
        sleep.accept(2, TimeUnit.SECONDS);

        //Publish to Kafka
        postElevatorStatusToApacheKafka.accept(elevatorLog.toString());

        //socketHandler.sendMessage(elevatorLog.toString());

        //Close elevator Door
        log.info("Elevator {} Closing Door", elevatorLog.getElevatorId());
        sleep.accept(2, TimeUnit.SECONDS);
        elevatorLog.setStatus("CloseDoor");

        elevatorLog.setId(ThreadLocalRandom.current().nextLong(100));
        elevatorLogsRepository.save(elevatorLog);

        //Publish to Kafka
        postElevatorStatusToApacheKafka.accept(elevatorLog.toString());
       // socketHandler.sendMessage(elevatorLog.toString());

        //Check direction of elevator and Navigate floors appropriately
        if (elevatorLog.getDestinationFloorNumber() > elevatorLog.getCurrentFloorNumber()) {
            while (elevatorLog.getDestinationFloorNumber() >= elevatorLog.getCurrentFloorNumber()) {

                sleep.accept(5, TimeUnit.SECONDS);
                currentFloorNumber += 1;

                elevatorLog.setId(ThreadLocalRandom.current().nextLong(100));
                elevatorLog.setCurrentFloorNumber(currentFloorNumber += 1);
                elevatorLogsRepository.save(elevatorLog);

                //Publish to Kafka
                 postElevatorStatusToApacheKafka.accept(elevatorLog.toString());
                //socketHandler.sendMessage(elevatorLog.toString());

                log.info("Elevator {} At floor Number {}", elevatorLog.getElevatorId(), currentFloorNumber);
            }

        } else if (elevatorLog.getDestinationFloorNumber() <= elevatorLog.getCurrentFloorNumber()) {

            while (elevatorLog.getDestinationFloorNumber() > elevatorLog.getCurrentFloorNumber()) {

                sleep.accept(5, TimeUnit.SECONDS);

                elevatorLog.setId(ThreadLocalRandom.current().nextLong(100));
                elevatorLog.setCurrentFloorNumber(currentFloorNumber -= 1);
                log.info("Elevator {} At floor Number {}", elevatorLog.getElevatorId(), currentFloorNumber);


                elevatorLogsRepository.save(elevatorLog);
                //Publish to Kafka
                 postElevatorStatusToApacheKafka.accept(elevatorLog.toString());
                //socketHandler.sendMessage(elevatorLog.toString());
            }
        } else {

            //Publish to Kafka

            elevatorLog.setId(ThreadLocalRandom.current().nextLong(100));
            elevatorLogsRepository.save(elevatorLog);
            postElevatorStatusToApacheKafka.accept(elevatorLog.toString());
            //socketHandler.sendMessage(elevatorLog.toString());
            log.info("Elevator {} Has arrived at the destination floor number", elevatorLog.getElevatorId());

        }
    }catch (Exception ex){
        ex.printStackTrace();
        log.error("ELEVATOR PROCESS ERROR: {}",ex.getMessage());
    }
    }


    private BiConsumer<Integer,TimeUnit> sleep  = (duration, unit)-> {
        try {
            unit.sleep(duration);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    };

    public Consumer<String> postElevatorStatusToApacheKafka = logs ->{
        kafkaProducerService.publishElevatorMessage(KAFKA_TOPIC, logs);
    };

    public Optional<List<ElevatorLogs>> getElevatorStatusLogs() {
      return Optional.of(elevatorLogsRepository.findAll());
    }
}
