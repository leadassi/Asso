# Image à utiliser pour la construction
FROM maven:3.9.6 AS builder

# Change le répertoire de travail à /app
WORKDIR /app

# Copie tous les fichiers du répertoire courant vers le répertoire /app du Docker
COPY . .

# Création d'un fichier JAR exécutable dans le répertoire target
RUN mvn clean package -DskipTests=true

# Image pour l'exécution
FROM openjdk:21-oracle
WORKDIR /app

# Copie l'artefact JAR depuis l'étape de construction
COPY --from=builder /app/target/*.jar app.jar

# Expose le port 8080
EXPOSE 8080

# Commande à exécuter au démarrage du conteneur
CMD ["java", "-jar", "app.jar"]