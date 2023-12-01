FROM maven:3.8.5-openjdk-17 AS build
COPY . .
run mvn clean package -DskipTest

FROM openjdk:17.0.1-jdk-slim
COPY --from=build /target/techforb-0.0.1-SNAPSHOT.jar techforb.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/techforb.jar"]