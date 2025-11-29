FROM ubuntu:latest
LABEL authors="lea"

# Utiliser une image Java officielle
FROM openjdk:17-jdk-slim

# Définir le répertoire de travail dans le conteneur
WORKDIR /app

# Copier le fichier JAR généré dans le conteneur
COPY target/fournisseur_service-0.0.1-SNAPSHOT.jar fournisseur_service-0.0.1-SNAPSHOT.jar

# Exposer le port sur lequel le service écoute
EXPOSE 9092

# Commande pour exécuter le microservice
ENTRYPOINT ["java", "-jar", "fournisseur_service-0.0.1-SNAPSHOT.jar"]
