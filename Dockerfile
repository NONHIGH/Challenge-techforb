FROM openjdk:17-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY target/techforb-0.0.1-SNAPSHOT.jar techforb-app.jar
ENTRYPOINT ["java","-jar","/techforb-app.jar"]