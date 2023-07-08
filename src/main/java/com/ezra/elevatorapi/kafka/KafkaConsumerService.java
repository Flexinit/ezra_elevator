package com.ezra.elevatorapi.kafka;

import com.ezra.elevatorapi.entity.ElevatorLogs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import static com.ezra.elevatorapi.utils.APIUtils.KAFKA_GROUP_ID;
import static com.ezra.elevatorapi.utils.APIUtils.KAFKA_TOPIC;

@Slf4j
@Service
public class KafkaConsumerService {

    @KafkaListener(topics = KAFKA_TOPIC, groupId = KAFKA_GROUP_ID)
    public void receiveMessage(String message) {
        log.info("ELEVATOR STATUS message: " + message);
    }
}
