package com.ezra.elevatorapi.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import static com.ezra.elevatorapi.utils.APIUtils.KAFKA_GROUP_ID;
import static com.ezra.elevatorapi.utils.APIUtils.KAFKA_TOPIC;

@Service
public class KafkaConsumerService {

    @KafkaListener(topics = KAFKA_TOPIC, groupId = KAFKA_GROUP_ID)
    public void receiveMessage(String message) {
        System.out.println("Received message: " + message);
    }
}
