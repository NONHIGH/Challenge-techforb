FROM maven:3.8.5-openjdk-17 AS build
FROM openjdk:17.0.1-jdk-slim
EXPOSE 8080
COPY target/techforb-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
