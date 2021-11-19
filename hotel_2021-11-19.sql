# ************************************************************
# Sequel Ace SQL dump
# Version 20016
#
# https://sequel-ace.com/
# https://github.com/Sequel-Ace/Sequel-Ace
#
# Host: localhost (MySQL 5.6.31)
# Database: hotel
# Generation Time: 2021-11-19 12:24:21 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
SET NAMES utf8mb4;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE='NO_AUTO_VALUE_ON_ZERO', SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table Reservation
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Reservation`;

CREATE TABLE `Reservation` (
  `Type` enum('0','1','2','3','4') DEFAULT NULL,
  `Name` varchar(40) DEFAULT NULL,
  `Cost` enum('55,000','75,000','80,000','150,000','230,000') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

LOCK TABLES `Reservation` WRITE;
/*!40000 ALTER TABLE `Reservation` DISABLE KEYS */;

INSERT INTO `Reservation` (`Type`, `Name`, `Cost`)
VALUES
	('0','marv234','55,000'),
	('1','hix','75,000'),
	('3','diana','150,000'),
	('3','isaac','150,000'),
	('4','baka','230,000');

/*!40000 ALTER TABLE `Reservation` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table Rooms
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Rooms`;

CREATE TABLE `Rooms` (
  `Type` enum('0','1','2','3','4') DEFAULT NULL,
  `Capacity` enum('10','20','5','3','2') DEFAULT NULL,
  `Size` enum('Single','Double','Twin','Tripple','Quad') DEFAULT NULL,
  `Cost` enum('55,000','75,000','80,000','150,000','230,000') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

LOCK TABLES `Rooms` WRITE;
/*!40000 ALTER TABLE `Rooms` DISABLE KEYS */;

INSERT INTO `Rooms` (`Type`, `Capacity`, `Size`, `Cost`)
VALUES
	('0','10','Single','55,000'),
	('1','20','Double','75,000'),
	('2','5','Twin','80,000'),
	('3','3','Tripple','150,000'),
	('4','2','Quad','230,000');

/*!40000 ALTER TABLE `Rooms` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
