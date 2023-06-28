package com.ezra.elevatorapi.kafka;

import com.ezra.elevatorapi.entity.ElevatorLogs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    private final KafkaTemplate<String, ElevatorLogs> kafkaTemplateLogs;

    @Autowired
    public KafkaProducerService( KafkaTemplate<String, ElevatorLogs> kafkaTemplateLogs) {
        this.kafkaTemplateLogs = kafkaTemplateLogs;
    }


    public void publishElevatorStatus(String topic, ElevatorLogs elevatorLogs) {
        kafkaTemplateLogs.send(topic, elevatorLogs);
    }
}
