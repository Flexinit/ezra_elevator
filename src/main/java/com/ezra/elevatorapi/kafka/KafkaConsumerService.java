package com.ezra.elevatorapi.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    @KafkaListener(topics = "ezra_world", groupId = "groupId")
    public void receiveMessage(String message) {
        System.out.println("Received message: " + message);
        // Process the received message as needed
    }
}
