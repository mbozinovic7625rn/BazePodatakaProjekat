-- ============================================================
-- laboratorija_db - schema
-- ============================================================

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `sesija_resurs`;
DROP TABLE IF EXISTS `sesija_alat`;
DROP TABLE IF EXISTS `sesija`;
DROP TABLE IF EXISTS `izvodjenje_izvodjac`;
DROP TABLE IF EXISTS `izvodjenje`;
DROP TABLE IF EXISTS `eksperiment_tip_alata`;
DROP TABLE IF EXISTS `eksperiment_resurs`;
DROP TABLE IF EXISTS `eksperiment_dizajner`;
DROP TABLE IF EXISTS `eksperiment`;
DROP TABLE IF EXISTS `dizajner_teorija`;
DROP TABLE IF EXISTS `teorijski_okvir`;
DROP TABLE IF EXISTS `inventar_resursa`;
DROP TABLE IF EXISTS `resurs`;
DROP TABLE IF EXISTS `alat`;
DROP TABLE IF EXISTS `tip_alata`;
DROP TABLE IF EXISTS `izvodjac`;
DROP TABLE IF EXISTS `dizajner`;
DROP TABLE IF EXISTS `istrazivac`;
DROP TABLE IF EXISTS `laboratorija`;

SET FOREIGN_KEY_CHECKS = 1;

-- ------------------------------------------------------------
-- laboratorija
-- ------------------------------------------------------------
CREATE TABLE `laboratorija` (
  `id_lab` int NOT NULL AUTO_INCREMENT,
  `naziv` varchar(100) NOT NULL,
  `opis_lokacije` text,
  `BSL_nivo` int DEFAULT NULL,
  `broj_komora` int DEFAULT NULL,
  `broj_inkubatora` int DEFAULT NULL,
  PRIMARY KEY (`id_lab`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ------------------------------------------------------------
-- istrazivac
-- ------------------------------------------------------------
CREATE TABLE `istrazivac` (
  `id_istrazivac` int NOT NULL AUTO_INCREMENT,
  `ime` varchar(50) NOT NULL,
  `prezime` varchar(50) NOT NULL,
  `kontakt` varchar(100) DEFAULT NULL,
  `datum_zaposlenja` date DEFAULT NULL,
  `naucno_zvanje` varchar(50) DEFAULT NULL,
  `oblast_specijalizacije` varchar(100) DEFAULT NULL,
  `BSL_sertifikat` tinyint(1) DEFAULT NULL,
  `id_lab` int DEFAULT NULL,
  PRIMARY KEY (`id_istrazivac`),
  KEY `id_lab` (`id_lab`),
  CONSTRAINT `istrazivac_ibfk_1` FOREIGN KEY (`id_lab`) REFERENCES `laboratorija` (`id_lab`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ------------------------------------------------------------
-- dizajner
-- ------------------------------------------------------------
CREATE TABLE `dizajner` (
  `id_istrazivac` int NOT NULL,
  PRIMARY KEY (`id_istrazivac`),
  CONSTRAINT `dizajner_ibfk_1` FOREIGN KEY (`id_istrazivac`) REFERENCES `istrazivac` (`id_istrazivac`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ------------------------------------------------------------
-- izvodjac
-- ------------------------------------------------------------
CREATE TABLE `izvodjac` (
  `id_istrazivac` int NOT NULL,
  PRIMARY KEY (`id_istrazivac`),
  CONSTRAINT `izvodjac_ibfk_1` FOREIGN KEY (`id_istrazivac`) REFERENCES `istrazivac` (`id_istrazivac`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ------------------------------------------------------------
-- tip_alata
-- ------------------------------------------------------------
CREATE TABLE `tip_alata` (
  `id_tip_alata` int NOT NULL AUTO_INCREMENT,
  `naziv` varchar(100) NOT NULL,
  `opis` text,
  PRIMARY KEY (`id_tip_alata`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ------------------------------------------------------------
-- alat
-- ------------------------------------------------------------
CREATE TABLE `alat` (
  `id_alat` int NOT NULL AUTO_INCREMENT,
  `identifikacioni_broj` varchar(50) DEFAULT NULL,
  `datum_nabavke` date DEFAULT NULL,
  `datum_proizvodnje` date DEFAULT NULL,
  `datum_kalibracije` date DEFAULT NULL,
  `id_lab` int DEFAULT NULL,
  `id_tip_alata` int DEFAULT NULL,
  PRIMARY KEY (`id_alat`),
  UNIQUE KEY `identifikacioni_broj` (`identifikacioni_broj`),
  KEY `id_lab` (`id_lab`),
  KEY `id_tip_alata` (`id_tip_alata`),
  CONSTRAINT `alat_ibfk_1` FOREIGN KEY (`id_lab`) REFERENCES `laboratorija` (`id_lab`),
  CONSTRAINT `alat_ibfk_2` FOREIGN KEY (`id_tip_alata`) REFERENCES `tip_alata` (`id_tip_alata`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ------------------------------------------------------------
-- resurs
-- ------------------------------------------------------------
CREATE TABLE `resurs` (
  `id_resurs` int NOT NULL AUTO_INCREMENT,
  `naziv` varchar(100) NOT NULL,
  `tip_resursa` varchar(50) DEFAULT NULL,
  `dobavljac` varchar(100) DEFAULT NULL,
  `barkod` varchar(50) DEFAULT NULL,
  `uslovi_cuvanja` text,
  `opis` text,
  PRIMARY KEY (`id_resurs`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ------------------------------------------------------------
-- inventar_resursa
-- ------------------------------------------------------------
CREATE TABLE `inventar_resursa` (
  `id_lab` int NOT NULL,
  `id_resurs` int NOT NULL,
  `kolicina` decimal(10,2) DEFAULT NULL,
  `merna_jedinica` varchar(20) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id_lab`,`id_resurs`),
  KEY `id_resurs` (`id_resurs`),
  CONSTRAINT `inventar_resursa_ibfk_1` FOREIGN KEY (`id_lab`) REFERENCES `laboratorija` (`id_lab`),
  CONSTRAINT `inventar_resursa_ibfk_2` FOREIGN KEY (`id_resurs`) REFERENCES `resurs` (`id_resurs`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ------------------------------------------------------------
-- teorijski_okvir
-- ------------------------------------------------------------
CREATE TABLE `teorijski_okvir` (
  `id_teorija` int NOT NULL AUTO_INCREMENT,
  `naziv` varchar(100) NOT NULL,
  `oblast` varchar(100) DEFAULT NULL,
  `opis` text,
  PRIMARY KEY (`id_teorija`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ------------------------------------------------------------
-- dizajner_teorija
-- ------------------------------------------------------------
CREATE TABLE `dizajner_teorija` (
  `id_istrazivac` int NOT NULL,
  `id_teorija` int NOT NULL,
  PRIMARY KEY (`id_istrazivac`,`id_teorija`),
  KEY `id_teorija` (`id_teorija`),
  CONSTRAINT `dizajner_teorija_ibfk_1` FOREIGN KEY (`id_istrazivac`) REFERENCES `dizajner` (`id_istrazivac`),
  CONSTRAINT `dizajner_teorija_ibfk_2` FOREIGN KEY (`id_teorija`) REFERENCES `teorijski_okvir` (`id_teorija`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ------------------------------------------------------------
-- eksperiment
-- ------------------------------------------------------------
CREATE TABLE `eksperiment` (
  `id_eksperiment` int NOT NULL AUTO_INCREMENT,
  `naziv` varchar(100) NOT NULL,
  `tip` varchar(50) DEFAULT NULL,
  `hipoteza` text,
  `trajanje_h` decimal(10,2) DEFAULT NULL,
  `ciljni_parametar` varchar(100) DEFAULT NULL,
  `temperatura` decimal(5,2) DEFAULT NULL,
  `pH` decimal(4,2) DEFAULT NULL,
  `vlaznost` decimal(5,2) DEFAULT NULL,
  `parc_pritisak_O2` decimal(5,2) DEFAULT NULL,
  `vremenski_interval_min` int DEFAULT NULL,
  `id_teorija` int DEFAULT NULL,
  PRIMARY KEY (`id_eksperiment`),
  KEY `id_teorija` (`id_teorija`),
  CONSTRAINT `eksperiment_ibfk_1` FOREIGN KEY (`id_teorija`) REFERENCES `teorijski_okvir` (`id_teorija`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ------------------------------------------------------------
-- eksperiment_dizajner
-- ------------------------------------------------------------
CREATE TABLE `eksperiment_dizajner` (
  `id_eksperiment` int NOT NULL,
  `id_istrazivac` int NOT NULL,
  PRIMARY KEY (`id_eksperiment`,`id_istrazivac`),
  KEY `id_istrazivac` (`id_istrazivac`),
  CONSTRAINT `eksperiment_dizajner_ibfk_1` FOREIGN KEY (`id_eksperiment`) REFERENCES `eksperiment` (`id_eksperiment`),
  CONSTRAINT `eksperiment_dizajner_ibfk_2` FOREIGN KEY (`id_istrazivac`) REFERENCES `dizajner` (`id_istrazivac`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ------------------------------------------------------------
-- eksperiment_resurs
-- ------------------------------------------------------------
CREATE TABLE `eksperiment_resurs` (
  `id_eksperiment` int NOT NULL,
  `id_resurs` int NOT NULL,
  `potrebna_kolicina` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`id_eksperiment`,`id_resurs`),
  KEY `id_resurs` (`id_resurs`),
  CONSTRAINT `eksperiment_resurs_ibfk_1` FOREIGN KEY (`id_eksperiment`) REFERENCES `eksperiment` (`id_eksperiment`),
  CONSTRAINT `eksperiment_resurs_ibfk_2` FOREIGN KEY (`id_resurs`) REFERENCES `resurs` (`id_resurs`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ------------------------------------------------------------
-- eksperiment_tip_alata
-- ------------------------------------------------------------
CREATE TABLE `eksperiment_tip_alata` (
  `id_eksperiment` int NOT NULL,
  `id_tip_alata` int NOT NULL,
  PRIMARY KEY (`id_eksperiment`,`id_tip_alata`),
  KEY `id_tip_alata` (`id_tip_alata`),
  CONSTRAINT `eksperiment_tip_alata_ibfk_1` FOREIGN KEY (`id_eksperiment`) REFERENCES `eksperiment` (`id_eksperiment`),
  CONSTRAINT `eksperiment_tip_alata_ibfk_2` FOREIGN KEY (`id_tip_alata`) REFERENCES `tip_alata` (`id_tip_alata`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ------------------------------------------------------------
-- izvodjenje
-- ------------------------------------------------------------
CREATE TABLE `izvodjenje` (
  `id_izvodjenje` int NOT NULL AUTO_INCREMENT,
  `datum` date DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  `id_eksperiment` int DEFAULT NULL,
  `id_lab` int DEFAULT NULL,
  PRIMARY KEY (`id_izvodjenje`),
  KEY `id_eksperiment` (`id_eksperiment`),
  KEY `id_lab` (`id_lab`),
  CONSTRAINT `izvodjenje_ibfk_1` FOREIGN KEY (`id_eksperiment`) REFERENCES `eksperiment` (`id_eksperiment`),
  CONSTRAINT `izvodjenje_ibfk_2` FOREIGN KEY (`id_lab`) REFERENCES `laboratorija` (`id_lab`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ------------------------------------------------------------
-- izvodjenje_izvodjac
-- ------------------------------------------------------------
CREATE TABLE `izvodjenje_izvodjac` (
  `id_izvodjenje` int NOT NULL,
  `id_istrazivac` int NOT NULL,
  `uloga` varchar(100) DEFAULT NULL,
  `putanja_beleski` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_izvodjenje`,`id_istrazivac`),
  KEY `id_istrazivac` (`id_istrazivac`),
  CONSTRAINT `izvodjenje_izvodjac_ibfk_1` FOREIGN KEY (`id_izvodjenje`) REFERENCES `izvodjenje` (`id_izvodjenje`),
  CONSTRAINT `izvodjenje_izvodjac_ibfk_2` FOREIGN KEY (`id_istrazivac`) REFERENCES `izvodjac` (`id_istrazivac`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ------------------------------------------------------------
-- sesija
-- ------------------------------------------------------------
CREATE TABLE `sesija` (
  `id_sesija` int NOT NULL AUTO_INCREMENT,
  `datum` date DEFAULT NULL,
  `vreme_pocetka` time DEFAULT NULL,
  `vreme_zavrsetka` time DEFAULT NULL,
  `id_izvodjenje` int DEFAULT NULL,
  PRIMARY KEY (`id_sesija`),
  KEY `id_izvodjenje` (`id_izvodjenje`),
  CONSTRAINT `sesija_ibfk_1` FOREIGN KEY (`id_izvodjenje`) REFERENCES `izvodjenje` (`id_izvodjenje`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ------------------------------------------------------------
-- sesija_alat
-- ------------------------------------------------------------
CREATE TABLE `sesija_alat` (
  `id_sesija` int NOT NULL,
  `id_alat` int NOT NULL,
  PRIMARY KEY (`id_sesija`,`id_alat`),
  KEY `id_alat` (`id_alat`),
  CONSTRAINT `sesija_alat_ibfk_1` FOREIGN KEY (`id_sesija`) REFERENCES `sesija` (`id_sesija`),
  CONSTRAINT `sesija_alat_ibfk_2` FOREIGN KEY (`id_alat`) REFERENCES `alat` (`id_alat`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ------------------------------------------------------------
-- sesija_resurs
-- ------------------------------------------------------------
CREATE TABLE `sesija_resurs` (
  `id_sesija` int NOT NULL,
  `id_resurs` int NOT NULL,
  `iskoriscena_kolicina` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`id_sesija`,`id_resurs`),
  KEY `id_resurs` (`id_resurs`),
  CONSTRAINT `sesija_resurs_ibfk_1` FOREIGN KEY (`id_sesija`) REFERENCES `sesija` (`id_sesija`),
  CONSTRAINT `sesija_resurs_ibfk_2` FOREIGN KEY (`id_resurs`) REFERENCES `resurs` (`id_resurs`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;