FROM openjdk:17-jdk-slim-buster
ARG JAR_FILE=*.jar
COPY target/elevator-api-0.0.1-SNAPSHOT.jar application.jar
ENTRYPOINT ["java", "-jar", "application.jar"]