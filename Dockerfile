FROM openjdk:17-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY target/techforb-0.0.1-SNAPSHOT.jar techforb-app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/techforb-app.jar"]