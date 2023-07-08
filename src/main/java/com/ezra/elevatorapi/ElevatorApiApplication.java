package com.ezra.elevatorapi;

import com.ezra.elevatorapi.SocketService.SocketHandler;
import com.ezra.elevatorapi.entity.ElevatorLogs;
import com.ezra.elevatorapi.kafka.KafkaConsumerService;
import com.ezra.elevatorapi.kafka.KafkaProducerService;
import com.ezra.elevatorapi.utils.APIUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableAsync;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;

@SpringBootApplication
@EnableAsync
public class ElevatorApiApplication {


	public static void main(String[] args) {
		SpringApplication.run(ElevatorApiApplication.class, args);
	}


	public  void testKafka(String[] args){
		ConfigurableApplicationContext context = SpringApplication.run(ElevatorApiApplication.class, args);
		KafkaProducerService producerService = context.getBean(KafkaProducerService.class);
		ElevatorLogs elevatorLogs = new ElevatorLogs();
		elevatorLogs.setElevatorId(1267L);
		producerService.publishElevatorMessage(APIUtils.KAFKA_TOPIC, elevatorLogs.toString());
		context.close();
	}

}
