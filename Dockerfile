# Étape 1 : Build Maven
FROM maven:3.9.4-eclipse-temurin-17 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

# Exécutez le build Maven, sautez les tests pour accélérer le processus de build du conteneur
RUN mvn clean package -DskipTests

# Étape 2 : Image finale, runtime OpenJDK
FROM openjdk:17-jdk-slim

WORKDIR /app

# Copiez le fichier JAR construit depuis l'étape de build vers l'image finale
COPY --from=build /app/target/HRproject-0.0.1-SNAPSHOT.jar app.jar

# Exposez le port sur lequel l'application Spring Boot écoute (par défaut 8080)
EXPOSE 8080

# Commande pour exécuter l'application Spring Boot
CMD ["java", "-jar", "app.jar"]