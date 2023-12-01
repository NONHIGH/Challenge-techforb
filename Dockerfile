# Fase de construcción
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Contenedor final
FROM openjdk:17.0.1-jdk-slim
WORKDIR /app
COPY --from=build /app/target/techforb-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
