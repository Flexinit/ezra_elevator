# ezra_elevator

ELEVATOR API
--------------------


TECHNOLOGY USED
------------------
The Elevator API is implemented with world class frameworks and libraries to enhance its performance.

1. Apache Kafka
-This tool came in very handy to take care of the realtime data publishing and consuming by simply subscribing 
to a topic(in this case "ezra_world") to get realtime data on the status of the lifts
- Apache Kafka is also fault-tolerant.
2. Java Spring boot Framework
-The choice of the above framework was irresistible since Spring Boot
comes without of the box dependency management and it takes care of both creating and destroying an object.
-This reduces boilerplate code hence maintaing a clean, well organized easily scalable code base.
-Java Spring boot is also one of the best frameworks that enable quick development and shipping of enterprise APIs to 
the market

2. Lombok
-This is a tool that is very much important in reducing boilerplate code hence reducing the Turnaround time
of development through annotations that do the heavy lifting behind the scenes. e.g @Getter, @Setter, @ToString
Lombok is also very important in logging.

3. Java Persistence API
-This library is the go between the business logic and the database layer. It takes care of database queries;

4. Postgresql
-This was the  DBMS chosen due to its scalability and ease of use

5. Java 8 and above features
- CompletableFuture API - this was necesary to perform parallel operations on different threads and receive a 
response once a thread is done. That is, multithreading.
-Use of Record instead of a class was handy especially to reduce boilerplate code through creating constructors
- Java Streams API - Declarative approach of handling lists 
-Functional Programming For proper code presentation
- Lambda expressions 


SET UP INSTRUCTIONS
-------------------
1. Apache Kafka message broker - set up using Confluence Cloud Kafka broker. 
-This was to avoid the overhead that comes with maintaining a local Kafka message broker as 
it requires a lot of resources
 - Configs set up in the application.properties file

2. Run the application and restore the database within the root folder of the project.

3. Go to http://localhost:8085/elevator/swagger-ui.html to test the endpoints

