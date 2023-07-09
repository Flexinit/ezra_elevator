package com.ezra.elevatorapi.kafka;

import com.ezra.elevatorapi.entity.ElevatorLogs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    @Autowired
    private  KafkaTemplate<String, String> kafkaTemplateString;


    public void publishElevatorMessage(String topic, String elevatorLogs) {
        kafkaTemplateString.send(topic, elevatorLogs);
    }
}
