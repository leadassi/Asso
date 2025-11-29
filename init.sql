-- MySQL dump 10.13  Distrib 9.0.1, for Linux (x86_64)
--
-- Host: localhost    Database: produits_management
-- ------------------------------------------------------
-- Server version	9.0.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `produit`
--

DROP TABLE IF EXISTS `produit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `produit` (
  `id` int NOT NULL AUTO_INCREMENT,
  `category` tinyint DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `id_fournisseur` int NOT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `price` float NOT NULL,
  `quantity` int NOT NULL,
  `sub_category` tinyint DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `produit_chk_1` CHECK ((`category` between 0 and 3)),
  CONSTRAINT `produit_chk_2` CHECK ((`sub_category` between 0 and 12))
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `produit`
--

LOCK TABLES `produit` WRITE;
/*!40000 ALTER TABLE `produit` DISABLE KEYS */;
INSERT INTO `produit` VALUES (5,0,'Fruit ngrosh ngrosh',2,'https://github.com/DOSSIVIL/IMAGES/blob/main/Aliment/papaye.jpeg?raw=true','papaye',450,1000,10),(6,0,'Fruit tropical',2,'https://github.com/DOSSIVIL/IMAGES/blob/main/Aliment/corrossol.jpeg?raw=true','corossol',750,1000,10),(8,2,'\'Les Nike Air Monarch IV sont des chaussures polyvalentes conues pour offrir un confort optimal tout au long de la journee.\n',1,'https://m.media-amazon.com/images/I/6125yAfsJKL._AC_UX575_.jpg','Nike',15000,1000,5),(13,0,'Huile  de palme plein de vertues.',2,'https://github.com/DOSSIVIL/IMAGES/blob/main/Aliment/huile%20rouge.jpeg?raw=true','Huile rouge',1850,95,9),(14,0,'Les abeilles y ont mis tout leur amour.',2,'https://github.com/DOSSIVIL/IMAGES/blob/main/Aliment/miel.jpeg?raw=true','Miel',850,100,10),(16,2,'Parfum aux essences douces.',4,'https://github.com/DOSSIVIL/IMAGES/blob/main/cosmetique/c.jpg?raw=true','Eau de parfum Gabrielle channel',25000,100,7),(17,1,'Vetement soigneux confectionne a partir d\'un tissu en coton.',4,'https://github.com/DOSSIVIL/IMAGES/blob/main/vetements/1.jpg?raw=true','t_shirt bleu marrine',3500,100,1),(18,1,'Vetement soigneux confectionne a partir d\'un tissu en coton.',4,'https://github.com/DOSSIVIL/IMAGES/blob/main/vetements/10.jpg?raw=true','t_shirt vert',3500,100,1),(19,1,'Veste elegante .',4,'https://github.com/DOSSIVIL/IMAGES/blob/main/vetements/11.jpg?raw=true','Veste grise',16500,100,1),(20,1,'Jacket pour ensemble decontracte .',4,'https://github.com/DOSSIVIL/IMAGES/blob/main/vetements/12.jpg?raw=true','Jacket bleue',7500,100,1),(21,1,'Style  decontracte au rendez vous.',4,'https://github.com/DOSSIVIL/IMAGES/blob/main/vetements/13.jpg?raw=true','t_shirt noir',2500,100,1),(22,1,'Besoin d\'etre responsable? Cette veste accompagne vos styles vestimentaires.',4,'https://github.com/DOSSIVIL/IMAGES/blob/main/vetements/14.jpg?raw=true','veste noire',25000,100,1),(23,1,'Une robe qui s\'adapte a toute morphologie.',4,'https://github.com/DOSSIVIL/IMAGES/blob/main/vetements/19.jpg?raw=true','Robe saumon',25000,100,2),(24,3,'Un sac a dos tout terrain.',4,'https://github.com/DOSSIVIL/IMAGES/blob/main/image/3.jpg?raw=true','Sac tout terrain',11000,100,4),(25,2,'Huile qui adouci les cheveux utile pour un soin capilaire plus soigne.',4,'https://github.com/DOSSIVIL/IMAGES/blob/main/image/4.jpeg?raw=true','Huile de jojoba',11000,100,8),(26,3,'Qualite et elegace s\'accordent.',3,'https://github.com/DOSSIVIL/IMAGES/blob/main/image/5.jpeg?raw=true','sac a main hermes',8000,1000,4),(27,3,'Un sac multi-usage.',3,'https://github.com/DOSSIVIL/IMAGES/blob/main/image/6.jpeg?raw=true','sac bandouliere',5000,1000,4),(28,3,'Un sac a dos confortable et securise.',3,'https://github.com/DOSSIVIL/IMAGES/blob/main/image/7.jpeg?raw=true','sac a dos bleu',5000,1000,4),(29,3,'sandale confortable pour une balade en famille.',5,'https://github.com/DOSSIVIL/IMAGES/blob/main/image/w2.png?raw=true','sandale violette rose',6000,1000,5),(30,3,'Facile pour un deplacement et elegant.',5,'https://github.com/DOSSIVIL/IMAGES/blob/main/image/w1.png?raw=true','Compensee marron',6000,1000,5),(31,3,'la chaussure qui respire.',5,'https://github.com/DOSSIVIL/IMAGES/blob/main/image/slider0.jpg?raw=true','adidas blanche',6000,1000,5),(32,2,'Chouchoutez votre corps, votre tete et vos sens avec nos produits luxueux.',5,'https://github.com/DOSSIVIL/IMAGES/blob/main/fruits/1-corp.jpeg?raw=true','Gamme liz',14000,100,7),(33,2,'Des parfums envotants aux soins revitalisants .',5,'https://github.com/DOSSIVIL/IMAGES/blob/main/fruits/2-corp.jpeg?raw=true','Salon line',2500,1000,7),(34,0,'Du croquant lie au mouelleux.',3,'https://github.com/DOSSIVIL/IMAGES/blob/main/fruits/2-fruit.jpeg?raw=true','Carottes',500,1000,9),(35,1,'Un style responsable et sexy.',3,'https://github.com/DOSSIVIL/IMAGES/blob/main/fruits/2-jupe.jpeg?raw=true','Jupe annees 80',5000,1000,3),(36,0,'Riches en antioxydants, vitamines et mineraux, ils sont non seulement delicieux, mais aussi benefiques pour la sante.',4,'https://github.com/DOSSIVIL/IMAGES/blob/main/fruits/3-fruit.jpeg?raw=true','Raisin',1000,1000,10),(37,1,'Son design moderne et intemporel la rend idale pour diverses occasions.',4,'https://github.com/DOSSIVIL/IMAGES/blob/main/fruits/3-jupe.jpeg?raw=true','Jupe eco16',5000,1000,3),(38,1,'Son design moderne et intemporel la rend idale pour diverses occasions.',4,'https://github.com/DOSSIVIL/IMAGES/blob/main/fruits/4-jupe.jpeg?raw=true','Jupe fleurie',5000,1000,3),(39,2,'Ses vertues permettent d\'assouplir les cheveux.',4,'https://github.com/DOSSIVIL/IMAGES/blob/main/fruits/Dabur%20Amla%20Huile%20pour%20Cheveux%20pour%20la%20Croissance%20des%20Cheveux%20Naturels%20300%20ml.jpeg?raw=true','Dabur Amla Huile',5000,1000,8),(40,2,'L\'huile de germe de bl est riche en acides gras essentiels, ce qui aide  hydrater en profondeur les cheveux secs et abms',4,'https://github.com/DOSSIVIL/IMAGES/blob/main/fruits/D%C3%A9couvrez%20les%205%20grandes%20bienfaits%20de%20l%E2%80%99huile%20de%20germe%20de%20bl%C3%A9%20pour%20les%20cheveux.jpeg?raw=true','Huile de germe de ble',3000,1000,8),(41,2,'Le beurre est riche en acides gras essentiels, ce qui aide  hydrater en profondeur le corps, les cheveux secs et abimes',4,'https://github.com/DOSSIVIL/IMAGES/blob/main/fruits/Garnier%20Ultra%20Doux%20Huile%20au%20Beurre%20de%20Cacao_Huile%20de%20Coco%20150%20ml.jpeg?raw=true','Beurre de cacao',4000,1000,8),(42,0,'Riche en nutriments essentiels pour une meilleure digestion',4,'https://github.com/DOSSIVIL/IMAGES/blob/main/Aliment/poivron.jpeg?raw=true','Poivron',50,1000,10),(43,0,'Poulet nourri avec des graines selectionnees au prealable',6,'https://github.com/DOSSIVIL/IMAGES/blob/main/Aliment/poulet.jpeg?raw=true','Poulet',4000,10,9),(44,0,'Pour une hydratation plus rapide',4,'https://github.com/DOSSIVIL/IMAGES/blob/main/fruits/Fanta%201_5L.jpeg?raw=true','Jus fanta',500,10,12),(45,0,'Pour une hydratation plus rapide',4,'https://github.com/DOSSIVIL/IMAGES/blob/main/fruits/Fizzy%20Drinks%20Fanta%20Orange%20soft%20drink%20Carbonated%20water%2C%20fanta%2C%20orange%2C%20orange%20Drink%2C%20juice%20png.jpeg?raw=true','Jus coca cola',700,10,12),(46,0,'Pour une hydratation plus rapide',4,'https://github.com/DOSSIVIL/IMAGES/blob/main/fruits/Sprite%20Fizzy%20Drinks%20Coca-Cola%20Fanta%20Beverage.jpeg?raw=true','Jus sprite',500,10,12),(47,1,'Pour un signe plus soigne',6,'https://github.com/DOSSIVIL/IMAGES/blob/main/fruits/Pantalon%20de%20costume.jpeg?raw=true','Pantalon',8500,100,0),(48,1,'Pratique et adapte a toute situation de travail champetre',6,'https://github.com/DOSSIVIL/IMAGES/blob/main/fruits/Pantalon%20de%20jardinage.jpeg?raw=true','Pantalon',8500,100,0),(49,0,'Des choux cultives sur un sol noire nourri',5,'https://github.com/DOSSIVIL/IMAGES/blob/main/fruits/Mexican%20Cabbage%20Salad.jpeg?raw=true','Choux',300,100,9),(50,0,'Des aubergines cultives sur un sol noire nourri',4,'https://github.com/DOSSIVIL/IMAGES/blob/main/fruits/L%E2%80%99aubergine.jpeg?raw=true','Aubergine',200,100,9),(51,2,'Le traitement les cheveux secs et abimes',4,'https://github.com/DOSSIVIL/IMAGES/blob/main/fruits/Skala%20Treatment.jpeg?raw=true','Skala traitement',2500,1000,8),(52,0,'Des choux de bruxelles cultives sur un sol noire nourri',4,'https://github.com/DOSSIVIL/IMAGES/blob/main/fruits/1-legumes.jpeg?raw=true','Choux de bruxelle',200,100,9),(53,1,'Sa texture est adaptee pour toute morphologie.',4,'https://github.com/DOSSIVIL/IMAGES/blob/main/fruits/Emma%20%26%20Giovanni%20-%20Jupe%20Pliss%C3%A9e%20Patineuse%20Courte%20Evas%C3%A9e%20(Taille%20S%20%C3%A0%20XL)%20-%20Femme.jpeg?raw=true','Jupe emma',5000,1000,3),(54,1,'Pour un tour de taille marque.',4,'https://github.com/DOSSIVIL/IMAGES/blob/main/fruits/Falda%20tubo%20fruncida%20en%20el%20frente%2C%20detalle%20botone%20decorativos_.jpeg?raw=true','Jupe droite',6000,1000,3),(55,1,'Pratique et adapte a toute situation de sport',4,'https://github.com/DOSSIVIL/IMAGES/blob/main/fruits/OrcaJump%20-%20Mens%20Fleece%20Joggers%20with%20Side%20Pockets%2C%20Elastic%20Waist%2C%20Navy%20Apricot_Spring_Drawstring_El%20-%20Navy%2C%20XL.jpeg?raw=true','Jogging',4000,100,0),(56,3,'Pratique et adapte a toute situation de sport',4,'https://github.com/DOSSIVIL/IMAGES/blob/main/chaussures/enfants/shoe1-1.jpg?raw=true','euperreo',24000,100,5),(57,3,'Pratique et adapte a toute situation de sport',4,'https://github.com/DOSSIVIL/IMAGES/blob/main/chaussures/hommes/12.jpg?raw=true','air jordan',30000,100,5),(58,2,'Le traitement les cheveux secs et abimes',4,'https://github.com/DOSSIVIL/IMAGES/blob/main/fruits/rHeup77r.jpeg?raw=true','Huile d\'argan',2500,1000,8),(59,3,'Un chapeau qui allie tradition et modernisme',4,'https://github.com/DOSSIVIL/IMAGES/blob/main/chapeau/1.jpg?raw=true','chapeau tradi',15000,50,12);
/*!40000 ALTER TABLE `produit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `produit_model`
--

DROP TABLE IF EXISTS `produit_model`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `produit_model` (
  `idproduit` int NOT NULL,
  `categorie` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `nom_produit` varchar(255) DEFAULT NULL,
  `prix` float NOT NULL,
  `quantite` int NOT NULL,
  `souscategorie` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idproduit`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `produit_model`
--

LOCK TABLES `produit_model` WRITE;
/*!40000 ALTER TABLE `produit_model` DISABLE KEYS */;
/*!40000 ALTER TABLE `produit_model` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `produit_model_seq`
--

DROP TABLE IF EXISTS `produit_model_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `produit_model_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `produit_model_seq`
--

LOCK TABLES `produit_model_seq` WRITE;
/*!40000 ALTER TABLE `produit_model_seq` DISABLE KEYS */;
INSERT INTO `produit_model_seq` VALUES (1);
/*!40000 ALTER TABLE `produit_model_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `produit_seq`
--

DROP TABLE IF EXISTS `produit_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `produit_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `produit_seq`
--

LOCK TABLES `produit_seq` WRITE;
/*!40000 ALTER TABLE `produit_seq` DISABLE KEYS */;
INSERT INTO `produit_seq` VALUES (301);
/*!40000 ALTER TABLE `produit_seq` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-12-30  7:10:51