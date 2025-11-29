# Asso - Plateforme de e-commerce

Bienvenue sur **Asso**, une plateforme innovante de vente en ligne. Ce projet offre une solution complète pour gérer un site e-commerce avec des fonctionnalités avancées telles que la gestion des produits, des commandes, des utilisateurs, des paiements sécurisés, des livraisons et un système de recommandation basé sur les préférences des utilisateurs.

---

## 📋 Fonctionnalités principales

- **Gestion des utilisateurs** :
  - Création de comptes, authentification sécurisée et gestion des profils.
  
- **Catalogue de produits** :
  - Affichage, ajout, modification et suppression de produits.

- **Panier d'achat** :
  - Ajout, modification et suppression d'articles dans le panier en temps réel.

- **Commandes** :
  - Validation et suivi de l'état des commandes jusqu'à la livraison.

- **Paiement sécurisé** :
  - Intégration de l'API [CinetPay](https://cinetpay.com) pour traiter les transactions de manière fiable et sécurisée.

- **Livraisons** :
  - Calcul des frais de livraison basés sur la distance entre le magasin et le lieu de livraison.
  - Création d'un code QR à scanner et valider par le client pour attester de la réception de sa commande.

- **Recommandations** :
  - Propositions de produits en fonction des préférences et votes des utilisateurs.

---

## 🛠️ Prérequis

Assurez-vous d'avoir les éléments suivants installés sur votre machine :

- **Docker** et **Docker Compose**
- **Java** (pour Spring Boot)
- **Python** (pour Django)
- **Node.js** et **npm** (pour React.js)

---

## 🚀 Installation et Lancement

### Pré-requis
Assurez-vous d'avoir installé Docker et Docker Compose sur votre machine.

---

### Construction et Exécution avec Dockerfile

1. **Démarrez les conteneurs Docker** :
    ```bash
    docker-compose up -d
    ```

2. **Charger le dump dans le conteneur MySQL** pour initialiser le catalogue de produits :
    - **Copier le dump dans le conteneur Docker** :
      Utilisez le fichier `init.sql` fourni à la racine du dossier et exécutez la commande suivante :
      ```bash
      docker cp init.sql MysqlContainer:/chemin_vers_fichier/init.sql
      ```

    - **Chargez le dump dans MySQL** :
      Ensuite, exécutez la commande suivante pour charger le fichier `init.sql` dans la base de données MySQL :
      ```bash
      docker exec -i MysqlContainer mysql -u sonia -p'caramel2' produits_management < /chemin_vers_fichier/init.sql
      ```

3. **Vérifiez que tous les conteneurs sont en cours d'exécution** :
    ```bash
    docker ps
    ```

4. **Accédez à la plateforme** (par défaut) :
    - Frontend React : `http://localhost:3000`

---

---
# IMAGES sur Docker Hub

## Qu'est-ce qu'une image sur Docker Hub ?

Une **image Docker** est un modèle immuable contenant tout ce qu'il faut pour exécuter une application, y compris le code, les bibliothèques, les dépendances et les fichiers système nécessaires. Ces images sont utilisées pour créer des conteneurs Docker. 

**Docker Hub** est une plateforme centrale pour partager et trouver ces images. Les utilisateurs peuvent uploader leurs images, consulter celles d'autres développeurs ou entreprises, et utiliser des images officielles pour simplifier le déploiement de leurs projets.

### Avantages des images Docker:

- **Standardisation** : Garantissent un environnement cohérent pour l'exécution de vos applications.
- **Portabilité** : Peuvent être exécutées sur n'importe quel système prenant en charge Docker.
- **Communauté large** : Une grande variété d'images est disponible gratuitement sur Docker Hub.

---

## Liens vers des dépôts publics sur Docker Hub pour des microservices dockerisés:

1. **[Spring Boot](https://hub.docker.com/repository/docker/leajustine/microservice_utilisateur)**
   - Services Gestions des utilisateurs

2. **[Spring Boot](https://hub.docker.com/r/jessicacarole/produitmicros_produit-micros)**
   - Services de Gestions des produits

3. **[Django Rest](https://hub.docker.com/repository/docker/dossivil/gestion_livraison/)**
   - Services de Gestion des Livraisons

4. **[[Spring Boot](https://hub.docker.com/repository/docker/sonianjumbe/paiement_paiement-service)**
   - Services de Gestions des Paiement

5. **[Spring Boot](https://hub.docker.com/repository/docker/sonianjumbe/recommandationsystem_recommandation-service)**
   - système de recommandation
  
6. **[Spring Boot](https://hub.docker.com/r/imeldaktis/microservice_commande)**
   - Services Gestions des commandes

7. **[Spring Boot](https://hub.docker.com/repository/docker/leajustine/fournisseur_service)**
   - Services Gestions des Fourniseurs
  
8. **[REact js](https://hub.docker.com/repository/docker/leajustine/asso-front)**
   - Frontend de L'application Web
  
---


## 🧰 Technologies utilisées

- **Backend** :
  - Spring Boot (Java)
  - Django (Python)

- **Frontend** :
  - React.js

- **Bases de données** :
  - PostgreSQL
  - MySQL
  - SQLite (pour le développement rapide)

- **Paiement** :
  - Intégration avec [CinetPay](https://cinetpay.com)

---

## 📩 Support et Contact

Pour toute question ou suggestion, contactez-nous à :  
📧 **[assoaddkn@gmail.com](mailto:assoaddkn@gmail.com)**

---

Merci de contribuer à **Asso** et de faire partie de cette aventure e-commerce ! 🎉
