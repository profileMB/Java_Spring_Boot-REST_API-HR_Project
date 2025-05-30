# Étape 1 : Build Maven
FROM maven:3.9.4-eclipse-temurin-17 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

# Étape 2 : Image finale, runtime OpenJDK
FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=build /app/target/HRproject-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080