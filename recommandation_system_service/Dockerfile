# Utilisation d'une image contenant le JDK 17 (ou une version compatible)
FROM openjdk:17-jdk-slim

# Ajout d'un label pour l'auteur
LABEL authors="sonia"

# Définir un répertoire de travail
WORKDIR /app

COPY . .

# Copier le fichier JAR généré dans le conteneur
COPY target/*.jar /app/app.jar

# Exposer le port de l'application Spring Boot
EXPOSE 8082

# Définir la commande de démarrage pour exécuter l'application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
