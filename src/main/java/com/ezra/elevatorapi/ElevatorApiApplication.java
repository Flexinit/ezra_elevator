package com.ezra.elevatorapi;

import com.ezra.elevatorapi.kafka.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ElevatorApiApplication {



	public static void main(String[] args) {
		SpringApplication.run(ElevatorApiApplication.class, args);


	}

	public  void testKafka(String[] args){
		// Get an instance of KafkaProducerService from the application context
		ConfigurableApplicationContext context = SpringApplication.run(ElevatorApiApplication.class, args);
		// Get an instance of KafkaProducerService from the application context
		KafkaProducerService producerService = context.getBean(KafkaProducerService.class);
		// Call the sendMessage method
		producerService.sendMessage("ezra_world", "I just joined ezra world");
		// Close the application context
		context.close();
	}

}
