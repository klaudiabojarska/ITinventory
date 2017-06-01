-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: webprog2
-- ------------------------------------------------------
-- Server version	5.7.17-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `consumables`
--

DROP TABLE IF EXISTS `consumables`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `consumables` (
  `id` int(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `stock` int(3) DEFAULT NULL,
  `device_id` int(20) unsigned DEFAULT NULL,
  `time_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `device_id` (`device_id`),
  CONSTRAINT `consumables_ibfk_1` FOREIGN KEY (`device_id`) REFERENCES `devices` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `consumables`
--

LOCK TABLES `consumables` WRITE;
/*!40000 ALTER TABLE `consumables` DISABLE KEYS */;
INSERT INTO `consumables` VALUES (1,'blue ink',100,NULL,'2017-01-10 21:12:10'),(2,'green ink',81,NULL,'2017-01-10 21:12:10');
/*!40000 ALTER TABLE `consumables` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customer` (
  `CUSTOMER_ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `NAME` varchar(45) NOT NULL,
  `ADDRESS` varchar(255) NOT NULL,
  `CREATED_DATE` datetime NOT NULL,
  PRIMARY KEY (`CUSTOMER_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES (1,'mkyong1','address1','2017-01-07 14:37:06'),(2,'mkyong2','address2','2017-01-07 14:37:47'),(3,'mkyong3','address3','2017-01-07 14:37:50'),(4,'mkyong4','address4','2017-01-07 14:37:51'),(5,'mkyong5','address5','2017-01-07 14:37:52');
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `devices`
--

DROP TABLE IF EXISTS `devices`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `devices` (
  `id` int(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `type` enum('desktop','laptop','tablet','mobile phone','printer','other') DEFAULT NULL,
  `mac` varchar(17) DEFAULT NULL,
  `user_id` int(20) unsigned DEFAULT NULL,
  `notes` varchar(255) DEFAULT NULL,
  `producer` varchar(45) DEFAULT NULL,
  `time_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `devices_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `devices`
--

LOCK TABLES `devices` WRITE;
/*!40000 ALTER TABLE `devices` DISABLE KEYS */;
INSERT INTO `devices` VALUES (8,'MSI GE60','laptop','27-63-5D-48-B2-F1',28,'','MSI','2017-01-15 23:09:47'),(9,'DELL 790 i5 2400','desktop','2D-DD-0A-67-38-DA',28,'very fast','DELL','2017-01-15 23:12:07'),(10,'Iphone 6S','mobile phone','FF-51-95-F2-E6-BD',27,'','Apple','2017-01-15 23:12:47');
/*!40000 ALTER TABLE `devices` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `groups`
--

DROP TABLE IF EXISTS `groups`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `groups` (
  `id` int(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `time_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `canEditUser` tinyint(1) NOT NULL DEFAULT '0',
  `canReadUser` tinyint(1) NOT NULL DEFAULT '0',
  `canEditConsumables` tinyint(1) NOT NULL DEFAULT '0',
  `canEditRepair` tinyint(1) NOT NULL DEFAULT '0',
  `canEditGroup` tinyint(1) NOT NULL DEFAULT '0',
  `canEditDevice` tinyint(1) NOT NULL DEFAULT '0',
  `canReadDevice` tinyint(1) NOT NULL DEFAULT '0',
  `canReadRepair` tinyint(1) NOT NULL DEFAULT '0',
  `canReadGroup` tinyint(1) NOT NULL DEFAULT '0',
  `canReadConsumables` tinyint(1) NOT NULL DEFAULT '0',
  `canCreateConsumables` tinyint(1) NOT NULL DEFAULT '0',
  `canCreateRepair` tinyint(1) NOT NULL DEFAULT '0',
  `canCreateDevice` tinyint(1) NOT NULL DEFAULT '0',
  `canCreateGroup` tinyint(1) NOT NULL DEFAULT '0',
  `canCreateUser` tinyint(1) NOT NULL DEFAULT '0',
  `OwnedByUser` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `groups`
--

LOCK TABLES `groups` WRITE;
/*!40000 ALTER TABLE `groups` DISABLE KEYS */;
INSERT INTO `groups` VALUES (1,'normal users','2017-01-10 22:42:30',0,0,0,0,0,0,1,1,0,1,0,0,0,0,0,1),(2,'It users','2017-01-15 02:21:59',0,0,1,1,0,1,1,1,0,1,1,1,1,0,0,0),(3,'administrator','2017-01-10 22:42:30',1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0);
/*!40000 ALTER TABLE `groups` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `repairs`
--

DROP TABLE IF EXISTS `repairs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `repairs` (
  `id` int(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `device_id` int(20) unsigned NOT NULL,
  `repairer_id` int(20) unsigned NOT NULL,
  `repair_date` datetime DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `time_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `device_id` (`device_id`),
  CONSTRAINT `repairs_ibfk_1` FOREIGN KEY (`device_id`) REFERENCES `devices` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `repairs`
--

LOCK TABLES `repairs` WRITE;
/*!40000 ALTER TABLE `repairs` DISABLE KEYS */;
INSERT INTO `repairs` VALUES (2,' cleaning cpu fan ',8,31,NULL,' was very dirty ','2017-01-15 23:33:52');
/*!40000 ALTER TABLE `repairs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `id` int(20) unsigned NOT NULL AUTO_INCREMENT,
  `login` varchar(45) NOT NULL,
  `first_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `time_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `group_id` int(20) unsigned NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `FK_users_1` (`group_id`),
  CONSTRAINT `FK_users_1` FOREIGN KEY (`group_id`) REFERENCES `groups` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (27,'klaudia','klaudia','bojarska','esch','klaudia','2017-01-10 14:56:15',1),(28,' mateusz ',' mateusz ',' koldowski ',' wroclaw ',' mateusz ','2017-01-10 14:58:32',2),(29,'admin','admin','admin','admin','adminadmin','2017-01-15 02:17:41',3),(30,'user','user','user','user','test','2017-01-15 06:51:20',1),(31,'ITuser','ITuser','ITuser','ITuser','testit','2017-01-15 06:57:35',2);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-01-15 23:38:21
