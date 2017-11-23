-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema CargoManager
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `CargoManager` ;

-- -----------------------------------------------------
-- Schema CargoManager
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `CargoManager` DEFAULT CHARACTER SET utf8 ;
USE `CargoManager` ;

-- -----------------------------------------------------
-- Table `Cities`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Cities` ;

CREATE TABLE IF NOT EXISTS `Cities` (
  `idCity` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `cityName` VARCHAR(45) NOT NULL,
  `cityLatitude` DECIMAL(5,3) NOT NULL DEFAULT 0,
  `cityLongitude` DECIMAL(6,3) NOT NULL DEFAULT 0,
  PRIMARY KEY (`idCity`),
  UNIQUE INDEX `cityName_UNIQUE` (`cityName` ASC))
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Users`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Users` ;

CREATE TABLE IF NOT EXISTS `Users` (
  `idUser` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `userLogin` VARCHAR(45) NOT NULL,
  `userPassword` VARCHAR(255) NOT NULL,
  `userPost` ENUM('DRIVER', 'STUFF') NOT NULL DEFAULT 'DRIVER',
  PRIMARY KEY (`idUser`),
  UNIQUE INDEX `userLogin_UNIQUE` (`userLogin` ASC))
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Cargoes`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Cargoes` ;

CREATE TABLE IF NOT EXISTS `Cargoes` (
  `idCargo` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `cargoCityId` BIGINT(20) UNSIGNED NOT NULL,
  `cargoName` VARCHAR(255) NOT NULL,
  `cargoWeightKg` DECIMAL(15,3) UNSIGNED NOT NULL DEFAULT 0,
  `cargoStatus` ENUM('READY', 'SHIPPED', 'DELIVERED') NOT NULL DEFAULT 'READY',
  PRIMARY KEY (`idCargo`),
  INDEX `cargoCity_idx` (`cargoCityId` ASC),
  CONSTRAINT `cargoCity`
  FOREIGN KEY (`cargoCityId`)
  REFERENCES `Cities` (`idCity`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Vehicles`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Vehicles` ;

CREATE TABLE IF NOT EXISTS `Vehicles` (
  `idVehicle`         BIGINT(20) UNSIGNED   NOT NULL AUTO_INCREMENT,
  `vehicleCityId`     BIGINT(20) UNSIGNED   NOT NULL,
  `vehicleRegNumber`  VARCHAR(45)           NOT NULL,
  `vehicleCapacityKg` BIGINT(20) UNSIGNED   NOT NULL DEFAULT 0,
  `vehicleStatus`     ENUM ('OK', 'BROKEN') NOT NULL DEFAULT 'OK',
  PRIMARY KEY (`idVehicle`),
  UNIQUE INDEX `vehicleRegNumber_UNIQUE` (`vehicleRegNumber` ASC),
  INDEX `vehicleCity_idx` (`vehicleCityId` ASC),
  CONSTRAINT `vehicleCity`
  FOREIGN KEY (`vehicleCityId`)
  REFERENCES `Cities` (`idCity`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Orders`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Orders` ;

CREATE TABLE IF NOT EXISTS `Orders` (
  `idOrder` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `orderVehicleId` BIGINT(20) UNSIGNED NULL,
  `orderProgress` INT UNSIGNED NOT NULL DEFAULT 0,
  `orderTotal` INT UNSIGNED NOT NULL DEFAULT 0,
  PRIMARY KEY (`idOrder`),
  INDEX `orderVehicle_idx` (`orderVehicleId` ASC),
  CONSTRAINT `orderVehicle`
  FOREIGN KEY (`orderVehicleId`)
  REFERENCES `Vehicles` (`idVehicle`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Checkpoints`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Checkpoints` ;

CREATE TABLE IF NOT EXISTS `Checkpoints` (
  `idCheckpoint` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `checkpointOrderId` BIGINT(20) UNSIGNED NOT NULL,
  `checkpointCityId` BIGINT(20) UNSIGNED NOT NULL,
  PRIMARY KEY (`idCheckpoint`),
  INDEX `checkpointOrder_idx` (`checkpointOrderId` ASC),
  INDEX `checkpointCity_idx` (`checkpointCityId` ASC),
  CONSTRAINT `checkpointOrder`
  FOREIGN KEY (`checkpointOrderId`)
  REFERENCES `Orders` (`idOrder`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `checkpointCity`
  FOREIGN KEY (`checkpointCityId`)
  REFERENCES `Cities` (`idCity`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Tasks`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Tasks` ;

CREATE TABLE IF NOT EXISTS `Tasks` (
  `idTask` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `taskCheckpointId` BIGINT(20) UNSIGNED NOT NULL,
  `taskCargoId` BIGINT(20) UNSIGNED NOT NULL,
  PRIMARY KEY (`idTask`),
  INDEX `taskCargo_idx` (`taskCargoId` ASC),
  INDEX `taskCheckpoint_idx` (`taskCheckpointId` ASC),
  CONSTRAINT `taskCargo`
  FOREIGN KEY (`taskCargoId`)
  REFERENCES `Cargoes` (`idCargo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `taskCheckpoint`
  FOREIGN KEY (`taskCheckpointId`)
  REFERENCES `Checkpoints` (`idCheckpoint`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Drivers`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Drivers` ;

CREATE TABLE IF NOT EXISTS `Drivers` (
  `idDriver` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `driverCityId` BIGINT(20) UNSIGNED NOT NULL,
  `driverVehicleId` BIGINT(20) UNSIGNED NULL,
  `driverPersNumber` VARCHAR(45) NOT NULL,
  `driverFirstName` VARCHAR(45) NOT NULL,
  `driverLastName` VARCHAR(45) NOT NULL,
  `driverStatus` ENUM('REST', 'WORK') NOT NULL DEFAULT 'REST',
  `driverLastStatusUpdate` DATETIME NOT NULL DEFAULT NOW(),
  `driverWorkedThisMonth` INT UNSIGNED NOT NULL DEFAULT 0,
  PRIMARY KEY (`idDriver`),
  UNIQUE INDEX `driverPersNumber_UNIQUE` (`driverPersNumber` ASC),
  INDEX `driverCity_idx` (`driverCityId` ASC),
  INDEX `driverVehicle_idx` (`driverVehicleId` ASC),
  CONSTRAINT `driverCity`
  FOREIGN KEY (`driverCityId`)
  REFERENCES `Cities` (`idCity`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `driverVehicle`
  FOREIGN KEY (`driverVehicleId`)
  REFERENCES `Vehicles` (`idVehicle`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `Cities`
-- -----------------------------------------------------
START TRANSACTION;
USE `CargoManager`;
INSERT INTO `Cities` (`idCity`, `cityName`, `cityLatitude`, `cityLongitude`) VALUES (DEFAULT, 'Moscow', 55.755, 37.612);
INSERT INTO `Cities` (`idCity`, `cityName`, `cityLatitude`, `cityLongitude`)
VALUES (DEFAULT, 'Saint-Petersburg', 59.952, 30.316);
INSERT INTO `Cities` (`idCity`, `cityName`, `cityLatitude`, `cityLongitude`) VALUES (DEFAULT, 'Novosibirsk', 55.012, 82.896);
INSERT INTO `Cities` (`idCity`, `cityName`, `cityLatitude`, `cityLongitude`) VALUES (DEFAULT, 'Ekaterinburg', 56.858, 60.638);
INSERT INTO `Cities` (`idCity`, `cityName`, `cityLatitude`, `cityLongitude`) VALUES (DEFAULT, 'Novgorod', 56.301, 43.895);
INSERT INTO `Cities` (`idCity`, `cityName`, `cityLatitude`, `cityLongitude`) VALUES (DEFAULT, 'Kazan', 55.835, 49.058);
INSERT INTO `Cities` (`idCity`, `cityName`, `cityLatitude`, `cityLongitude`) VALUES (DEFAULT, 'Chelyabinsk', 55.163, 61.407);
INSERT INTO `Cities` (`idCity`, `cityName`, `cityLatitude`, `cityLongitude`) VALUES (DEFAULT, 'Omsk', 54.993, 73.350);
INSERT INTO `Cities` (`idCity`, `cityName`, `cityLatitude`, `cityLongitude`) VALUES (DEFAULT, 'Samara', 53.236, 50.201);
INSERT INTO `Cities` (`idCity`, `cityName`, `cityLatitude`, `cityLongitude`) VALUES (DEFAULT, 'Rostov', 47.231, 39.676);
INSERT INTO `Cities` (`idCity`, `cityName`, `cityLatitude`, `cityLongitude`) VALUES (DEFAULT, 'Ufa', 54.734, 55.980);
INSERT INTO `Cities` (`idCity`, `cityName`, `cityLatitude`, `cityLongitude`) VALUES (DEFAULT, 'Krasnoyarsk', 56.032, 92.916);
INSERT INTO `Cities` (`idCity`, `cityName`, `cityLatitude`, `cityLongitude`) VALUES (DEFAULT, 'Perm', 58.029, 56.243);
INSERT INTO `Cities` (`idCity`, `cityName`, `cityLatitude`, `cityLongitude`) VALUES (DEFAULT, 'Voronezh', 51.669, 39.149);
INSERT INTO `Cities` (`idCity`, `cityName`, `cityLatitude`, `cityLongitude`) VALUES (DEFAULT, 'Volgograd', 48.712, 44.513);
INSERT INTO `Cities` (`idCity`, `cityName`, `cityLatitude`, `cityLongitude`) VALUES (DEFAULT, 'Orenburg', 51.765, 55.079);
INSERT INTO `Cities` (`idCity`, `cityName`, `cityLatitude`, `cityLongitude`) VALUES (DEFAULT, 'Orsk', 51.217, 58.595);

COMMIT;


-- -----------------------------------------------------
-- Data for table `Users`
-- -----------------------------------------------------
START TRANSACTION;
USE `CargoManager`;
INSERT INTO `Users` (`idUser`, `userLogin`, `userPassword`, `userPost`) VALUES (DEFAULT, 'admin', 'admin', 'STUFF');
INSERT INTO `Users` (`idUser`, `userLogin`, `userPassword`, `userPost`) VALUES (DEFAULT, 'user', 'user', 'DRIVER');

COMMIT;


-- -----------------------------------------------------
-- Data for table `Cargoes`
-- -----------------------------------------------------
START TRANSACTION;
USE `CargoManager`;
INSERT INTO `Cargoes` (`idCargo`, `cargoCityId`, `cargoName`, `cargoWeightKg`, `cargoStatus`) VALUES (DEFAULT, 6, 'Apples', 2000, DEFAULT);
INSERT INTO `Cargoes` (`idCargo`, `cargoCityId`, `cargoName`, `cargoWeightKg`, `cargoStatus`) VALUES (DEFAULT, 6, 'Oranges', 1500, DEFAULT);
INSERT INTO `Cargoes` (`idCargo`, `cargoCityId`, `cargoName`, `cargoWeightKg`, `cargoStatus`) VALUES (DEFAULT, 10, 'Watermelons', 2200, DEFAULT);
INSERT INTO `Cargoes` (`idCargo`, `cargoCityId`, `cargoName`, `cargoWeightKg`, `cargoStatus`) VALUES (DEFAULT, 2, 'Beer', 1800, DEFAULT);
INSERT INTO `Cargoes` (`idCargo`, `cargoCityId`, `cargoName`, `cargoWeightKg`, `cargoStatus`) VALUES (DEFAULT, 2, 'Wine', 1000, DEFAULT);
INSERT INTO `Cargoes` (`idCargo`, `cargoCityId`, `cargoName`, `cargoWeightKg`, `cargoStatus`) VALUES (DEFAULT, 2, 'Cognac', 400, DEFAULT);
INSERT INTO `Cargoes` (`idCargo`, `cargoCityId`, `cargoName`, `cargoWeightKg`, `cargoStatus`)
VALUES (DEFAULT, 1, 'Whiskey', 400, DEFAULT);
INSERT INTO `Cargoes` (`idCargo`, `cargoCityId`, `cargoName`, `cargoWeightKg`, `cargoStatus`)
VALUES (DEFAULT, 1, 'Water', 1900, DEFAULT);

COMMIT;


-- -----------------------------------------------------
-- Data for table `Vehicles`
-- -----------------------------------------------------
START TRANSACTION;
USE `CargoManager`;
INSERT INTO `Vehicles` (`idVehicle`, `vehicleCityId`, `vehicleRegNumber`, `vehicleCapacityKg`, `vehicleStatus`)
VALUES (DEFAULT, 1, '1234QW', 22000, DEFAULT);
INSERT INTO `Vehicles` (`idVehicle`, `vehicleCityId`, `vehicleRegNumber`, `vehicleCapacityKg`, `vehicleStatus`)
VALUES (DEFAULT, 1, '5678ER', 17000, DEFAULT);
INSERT INTO `Vehicles` (`idVehicle`, `vehicleCityId`, `vehicleRegNumber`, `vehicleCapacityKg`, `vehicleStatus`)
VALUES (DEFAULT, 1, '9012TY', 20000, DEFAULT);
INSERT INTO `Vehicles` (`idVehicle`, `vehicleCityId`, `vehicleRegNumber`, `vehicleCapacityKg`, `vehicleStatus`)
VALUES (DEFAULT, 1, '3456UI', 19000, DEFAULT);
INSERT INTO `Vehicles` (`idVehicle`, `vehicleCityId`, `vehicleRegNumber`, `vehicleCapacityKg`, `vehicleStatus`)
VALUES (DEFAULT, 2, '7890OP', 20000, DEFAULT);
INSERT INTO `Vehicles` (`idVehicle`, `vehicleCityId`, `vehicleRegNumber`, `vehicleCapacityKg`, `vehicleStatus`)
VALUES (DEFAULT, 2, '9876AS', 19000, DEFAULT);
INSERT INTO `Vehicles` (`idVehicle`, `vehicleCityId`, `vehicleRegNumber`, `vehicleCapacityKg`, `vehicleStatus`)
VALUES (DEFAULT, 2, '5432DF', 13000, DEFAULT);
INSERT INTO `Vehicles` (`idVehicle`, `vehicleCityId`, `vehicleRegNumber`, `vehicleCapacityKg`, `vehicleStatus`)
VALUES (DEFAULT, 3, '1098GH', 15000, DEFAULT);
INSERT INTO `Vehicles` (`idVehicle`, `vehicleCityId`, `vehicleRegNumber`, `vehicleCapacityKg`, `vehicleStatus`)
VALUES (DEFAULT, 3, '7654JK', 7000, 'BROKEN');
INSERT INTO `Vehicles` (`idVehicle`, `vehicleCityId`, `vehicleRegNumber`, `vehicleCapacityKg`, `vehicleStatus`)
VALUES (DEFAULT, 4, '3210LZ', 10000, DEFAULT);
INSERT INTO `Vehicles` (`idVehicle`, `vehicleCityId`, `vehicleRegNumber`, `vehicleCapacityKg`, `vehicleStatus`)
VALUES (DEFAULT, 4, '1357XC', 6000, DEFAULT);
INSERT INTO `Vehicles` (`idVehicle`, `vehicleCityId`, `vehicleRegNumber`, `vehicleCapacityKg`, `vehicleStatus`)
VALUES (DEFAULT, 5, '9135VB', 11000, DEFAULT);
INSERT INTO `Vehicles` (`idVehicle`, `vehicleCityId`, `vehicleRegNumber`, `vehicleCapacityKg`, `vehicleStatus`)
VALUES (DEFAULT, 6, '7913NM', 7000, DEFAULT);
INSERT INTO `Vehicles` (`idVehicle`, `vehicleCityId`, `vehicleRegNumber`, `vehicleCapacityKg`, `vehicleStatus`)
VALUES (DEFAULT, 7, '5791NB', 9000, DEFAULT);
INSERT INTO `Vehicles` (`idVehicle`, `vehicleCityId`, `vehicleRegNumber`, `vehicleCapacityKg`, `vehicleStatus`)
VALUES (DEFAULT, 8, '3579VC', 8000, DEFAULT);
INSERT INTO `Vehicles` (`idVehicle`, `vehicleCityId`, `vehicleRegNumber`, `vehicleCapacityKg`, `vehicleStatus`)
VALUES (DEFAULT, 9, '7531XZ', 14000, DEFAULT);
INSERT INTO `Vehicles` (`idVehicle`, `vehicleCityId`, `vehicleRegNumber`, `vehicleCapacityKg`, `vehicleStatus`)
VALUES (DEFAULT, 11, '9753LK', 10000, DEFAULT);
INSERT INTO `Vehicles` (`idVehicle`, `vehicleCityId`, `vehicleRegNumber`, `vehicleCapacityKg`, `vehicleStatus`)
VALUES (DEFAULT, 13, '1975JH', 9000, DEFAULT);
INSERT INTO `Vehicles` (`idVehicle`, `vehicleCityId`, `vehicleRegNumber`, `vehicleCapacityKg`, `vehicleStatus`)
VALUES (DEFAULT, 15, '3197GF', 5000, DEFAULT);
INSERT INTO `Vehicles` (`idVehicle`, `vehicleCityId`, `vehicleRegNumber`, `vehicleCapacityKg`, `vehicleStatus`)
VALUES (DEFAULT, 17, '5319DS', 8000, DEFAULT);

COMMIT;


-- -----------------------------------------------------
-- Data for table `Orders`
-- -----------------------------------------------------
START TRANSACTION;
USE `CargoManager`;
INSERT INTO `Orders` (`idOrder`, `orderVehicleId`, `orderProgress`, `orderTotal`) VALUES (DEFAULT, NULL, DEFAULT, 3);
INSERT INTO `Orders` (`idOrder`, `orderVehicleId`, `orderProgress`, `orderTotal`) VALUES (DEFAULT, NULL, DEFAULT, 4);

COMMIT;


-- -----------------------------------------------------
-- Data for table `Checkpoints`
-- -----------------------------------------------------
START TRANSACTION;
USE `CargoManager`;
INSERT INTO `Checkpoints` (`idCheckpoint`, `checkpointOrderId`, `checkpointCityId`) VALUES (DEFAULT, 1, 6);
INSERT INTO `Checkpoints` (`idCheckpoint`, `checkpointOrderId`, `checkpointCityId`) VALUES (DEFAULT, 1, 10);
INSERT INTO `Checkpoints` (`idCheckpoint`, `checkpointOrderId`, `checkpointCityId`) VALUES (DEFAULT, 1, 1);
INSERT INTO `Checkpoints` (`idCheckpoint`, `checkpointOrderId`, `checkpointCityId`) VALUES (DEFAULT, 2, 2);
INSERT INTO `Checkpoints` (`idCheckpoint`, `checkpointOrderId`, `checkpointCityId`) VALUES (DEFAULT, 2, 1);
INSERT INTO `Checkpoints` (`idCheckpoint`, `checkpointOrderId`, `checkpointCityId`) VALUES (DEFAULT, 2, 5);
INSERT INTO `Checkpoints` (`idCheckpoint`, `checkpointOrderId`, `checkpointCityId`) VALUES (DEFAULT, 2, 4);

COMMIT;


-- -----------------------------------------------------
-- Data for table `Tasks`
-- -----------------------------------------------------
START TRANSACTION;
USE `CargoManager`;
INSERT INTO `Tasks` (`idTask`, `taskCheckpointId`, `taskCargoId`) VALUES (DEFAULT, 1, 1);
INSERT INTO `Tasks` (`idTask`, `taskCheckpointId`, `taskCargoId`) VALUES (DEFAULT, 1, 2);
INSERT INTO `Tasks` (`idTask`, `taskCheckpointId`, `taskCargoId`) VALUES (DEFAULT, 2, 3);
INSERT INTO `Tasks` (`idTask`, `taskCheckpointId`, `taskCargoId`) VALUES (DEFAULT, 3, 1);
INSERT INTO `Tasks` (`idTask`, `taskCheckpointId`, `taskCargoId`) VALUES (DEFAULT, 3, 2);
INSERT INTO `Tasks` (`idTask`, `taskCheckpointId`, `taskCargoId`) VALUES (DEFAULT, 3, 3);
INSERT INTO `Tasks` (`idTask`, `taskCheckpointId`, `taskCargoId`) VALUES (DEFAULT, 4, 4);
INSERT INTO `Tasks` (`idTask`, `taskCheckpointId`, `taskCargoId`) VALUES (DEFAULT, 4, 5);
INSERT INTO `Tasks` (`idTask`, `taskCheckpointId`, `taskCargoId`) VALUES (DEFAULT, 4, 6);
INSERT INTO `Tasks` (`idTask`, `taskCheckpointId`, `taskCargoId`) VALUES (DEFAULT, 5, 4);
INSERT INTO `Tasks` (`idTask`, `taskCheckpointId`, `taskCargoId`) VALUES (DEFAULT, 5, 7);
INSERT INTO `Tasks` (`idTask`, `taskCheckpointId`, `taskCargoId`) VALUES (DEFAULT, 5, 8);
INSERT INTO `Tasks` (`idTask`, `taskCheckpointId`, `taskCargoId`) VALUES (DEFAULT, 6, 5);
INSERT INTO `Tasks` (`idTask`, `taskCheckpointId`, `taskCargoId`) VALUES (DEFAULT, 7, 6);
INSERT INTO `Tasks` (`idTask`, `taskCheckpointId`, `taskCargoId`) VALUES (DEFAULT, 7, 7);
INSERT INTO `Tasks` (`idTask`, `taskCheckpointId`, `taskCargoId`) VALUES (DEFAULT, 7, 8);

COMMIT;


-- -----------------------------------------------------
-- Data for table `Drivers`
-- -----------------------------------------------------
START TRANSACTION;
USE `CargoManager`;
INSERT INTO `Drivers` (`idDriver`, `driverCityId`, `driverVehicleId`, `driverPersNumber`, `driverFirstName`, `driverLastName`, `driverStatus`, `driverLastStatusUpdate`, `driverWorkedThisMonth`) VALUES (DEFAULT, 1, NULL, 'QWE123456', 'Alexandr', 'Ivanov', DEFAULT, DEFAULT, DEFAULT);
INSERT INTO `Drivers` (`idDriver`, `driverCityId`, `driverVehicleId`, `driverPersNumber`, `driverFirstName`, `driverLastName`, `driverStatus`, `driverLastStatusUpdate`, `driverWorkedThisMonth`) VALUES (DEFAULT, 1, NULL, 'RTY789012', 'Maxim', 'Smirnov', DEFAULT, DEFAULT, DEFAULT);
INSERT INTO `Drivers` (`idDriver`, `driverCityId`, `driverVehicleId`, `driverPersNumber`, `driverFirstName`, `driverLastName`, `driverStatus`, `driverLastStatusUpdate`, `driverWorkedThisMonth`) VALUES (DEFAULT, 1, NULL, 'UIO345678', 'Ivan', 'Kuznetsov', DEFAULT, DEFAULT, DEFAULT);
INSERT INTO `Drivers` (`idDriver`, `driverCityId`, `driverVehicleId`, `driverPersNumber`, `driverFirstName`, `driverLastName`, `driverStatus`, `driverLastStatusUpdate`, `driverWorkedThisMonth`) VALUES (DEFAULT, 1, NULL, 'PAS901234', 'Alexandr', 'Popov', DEFAULT, DEFAULT, DEFAULT);
INSERT INTO `Drivers` (`idDriver`, `driverCityId`, `driverVehicleId`, `driverPersNumber`, `driverFirstName`, `driverLastName`, `driverStatus`, `driverLastStatusUpdate`, `driverWorkedThisMonth`) VALUES (DEFAULT, 1, NULL, 'DFG567890', 'Artem', 'Vasilyev', DEFAULT, DEFAULT, DEFAULT);
INSERT INTO `Drivers` (`idDriver`, `driverCityId`, `driverVehicleId`, `driverPersNumber`, `driverFirstName`, `driverLastName`, `driverStatus`, `driverLastStatusUpdate`, `driverWorkedThisMonth`) VALUES (DEFAULT, 1, NULL, 'HJK987654', 'Nikita', 'Petrov', DEFAULT, DEFAULT, DEFAULT);
INSERT INTO `Drivers` (`idDriver`, `driverCityId`, `driverVehicleId`, `driverPersNumber`, `driverFirstName`, `driverLastName`, `driverStatus`, `driverLastStatusUpdate`, `driverWorkedThisMonth`) VALUES (DEFAULT, 2, NULL, 'LZX321098', 'Dmitriy', 'Popov', DEFAULT, DEFAULT, DEFAULT);
INSERT INTO `Drivers` (`idDriver`, `driverCityId`, `driverVehicleId`, `driverPersNumber`, `driverFirstName`, `driverLastName`, `driverStatus`, `driverLastStatusUpdate`, `driverWorkedThisMonth`) VALUES (DEFAULT, 2, NULL, 'CVB765432', 'Maxim', 'Sokolov', DEFAULT, DEFAULT, DEFAULT);
INSERT INTO `Drivers` (`idDriver`, `driverCityId`, `driverVehicleId`, `driverPersNumber`, `driverFirstName`, `driverLastName`, `driverStatus`, `driverLastStatusUpdate`, `driverWorkedThisMonth`) VALUES (DEFAULT, 2, NULL, 'NMQ109876', 'Egor', 'Mikhaylov', DEFAULT, DEFAULT, DEFAULT);
INSERT INTO `Drivers` (`idDriver`, `driverCityId`, `driverVehicleId`, `driverPersNumber`, `driverFirstName`, `driverLastName`, `driverStatus`, `driverLastStatusUpdate`, `driverWorkedThisMonth`) VALUES (DEFAULT, 2, NULL, 'WER543210', 'Daniil', 'Novikov', DEFAULT, DEFAULT, DEFAULT);
INSERT INTO `Drivers` (`idDriver`, `driverCityId`, `driverVehicleId`, `driverPersNumber`, `driverFirstName`, `driverLastName`, `driverStatus`, `driverLastStatusUpdate`, `driverWorkedThisMonth`) VALUES (DEFAULT, 2, NULL, 'TYU135791', 'Mikhail', 'Fedorov', DEFAULT, DEFAULT, DEFAULT);
INSERT INTO `Drivers` (`idDriver`, `driverCityId`, `driverVehicleId`, `driverPersNumber`, `driverFirstName`, `driverLastName`, `driverStatus`, `driverLastStatusUpdate`, `driverWorkedThisMonth`) VALUES (DEFAULT, 3, NULL, 'IOP357913', 'Alexandr', 'Morozov', DEFAULT, DEFAULT, DEFAULT);
INSERT INTO `Drivers` (`idDriver`, `driverCityId`, `driverVehicleId`, `driverPersNumber`, `driverFirstName`, `driverLastName`, `driverStatus`, `driverLastStatusUpdate`, `driverWorkedThisMonth`) VALUES (DEFAULT, 3, NULL, 'ASD579135', 'Andrey', 'Volkov', DEFAULT, DEFAULT, DEFAULT);
INSERT INTO `Drivers` (`idDriver`, `driverCityId`, `driverVehicleId`, `driverPersNumber`, `driverFirstName`, `driverLastName`, `driverStatus`, `driverLastStatusUpdate`, `driverWorkedThisMonth`) VALUES (DEFAULT, 3, NULL, 'FGH791357', 'Alexey', 'Ivanov', DEFAULT, DEFAULT, DEFAULT);
INSERT INTO `Drivers` (`idDriver`, `driverCityId`, `driverVehicleId`, `driverPersNumber`, `driverFirstName`, `driverLastName`, `driverStatus`, `driverLastStatusUpdate`, `driverWorkedThisMonth`) VALUES (DEFAULT, 3, NULL, 'JKL913579', 'Ilya', 'Alexeev', DEFAULT, DEFAULT, DEFAULT);
INSERT INTO `Drivers` (`idDriver`, `driverCityId`, `driverVehicleId`, `driverPersNumber`, `driverFirstName`, `driverLastName`, `driverStatus`, `driverLastStatusUpdate`, `driverWorkedThisMonth`) VALUES (DEFAULT, 4, NULL, 'ZXC753197', 'Ivan', 'Lebedev', DEFAULT, DEFAULT, DEFAULT);
INSERT INTO `Drivers` (`idDriver`, `driverCityId`, `driverVehicleId`, `driverPersNumber`, `driverFirstName`, `driverLastName`, `driverStatus`, `driverLastStatusUpdate`, `driverWorkedThisMonth`) VALUES (DEFAULT, 4, NULL, 'VBN531975', 'Nikita', 'Semenov', DEFAULT, DEFAULT, DEFAULT);
INSERT INTO `Drivers` (`idDriver`, `driverCityId`, `driverVehicleId`, `driverPersNumber`, `driverFirstName`, `driverLastName`, `driverStatus`, `driverLastStatusUpdate`, `driverWorkedThisMonth`) VALUES (DEFAULT, 4, NULL, 'MQW319753', 'Vladislav', 'Egorov', DEFAULT, DEFAULT, DEFAULT);
INSERT INTO `Drivers` (`idDriver`, `driverCityId`, `driverVehicleId`, `driverPersNumber`, `driverFirstName`, `driverLastName`, `driverStatus`, `driverLastStatusUpdate`, `driverWorkedThisMonth`) VALUES (DEFAULT, 4, NULL, 'ERT197531', 'Mark', 'Pavlov', DEFAULT, DEFAULT, DEFAULT);
INSERT INTO `Drivers` (`idDriver`, `driverCityId`, `driverVehicleId`, `driverPersNumber`, `driverFirstName`, `driverLastName`, `driverStatus`, `driverLastStatusUpdate`, `driverWorkedThisMonth`) VALUES (DEFAULT, 5, NULL, 'YUI478523', 'Sergey', 'Kozlov', DEFAULT, DEFAULT, DEFAULT);
INSERT INTO `Drivers` (`idDriver`, `driverCityId`, `driverVehicleId`, `driverPersNumber`, `driverFirstName`, `driverLastName`, `driverStatus`, `driverLastStatusUpdate`, `driverWorkedThisMonth`) VALUES (DEFAULT, 5, NULL, 'OPA699632', 'Sergey', 'Popov', DEFAULT, DEFAULT, DEFAULT);
INSERT INTO `Drivers` (`idDriver`, `driverCityId`, `driverVehicleId`, `driverPersNumber`, `driverFirstName`, `driverLastName`, `driverStatus`, `driverLastStatusUpdate`, `driverWorkedThisMonth`) VALUES (DEFAULT, 5, NULL, 'SDF587410', 'Roman', 'Stepanov', DEFAULT, DEFAULT, DEFAULT);
INSERT INTO `Drivers` (`idDriver`, `driverCityId`, `driverVehicleId`, `driverPersNumber`, `driverFirstName`, `driverLastName`, `driverStatus`, `driverLastStatusUpdate`, `driverWorkedThisMonth`) VALUES (DEFAULT, 6, NULL, 'GHJ014785', 'Vladimir', 'Nikolaev', DEFAULT, DEFAULT, DEFAULT);
INSERT INTO `Drivers` (`idDriver`, `driverCityId`, `driverVehicleId`, `driverPersNumber`, `driverFirstName`, `driverLastName`, `driverStatus`, `driverLastStatusUpdate`, `driverWorkedThisMonth`) VALUES (DEFAULT, 6, NULL, 'KLZ236996', 'Timofey', 'Orlov', DEFAULT, DEFAULT, DEFAULT);
INSERT INTO `Drivers` (`idDriver`, `driverCityId`, `driverVehicleId`, `driverPersNumber`, `driverFirstName`, `driverLastName`, `driverStatus`, `driverLastStatusUpdate`, `driverWorkedThisMonth`) VALUES (DEFAULT, 6, NULL, 'XCV325874', 'Matvey', 'Andreev', DEFAULT, DEFAULT, DEFAULT);
INSERT INTO `Drivers` (`idDriver`, `driverCityId`, `driverVehicleId`, `driverPersNumber`, `driverFirstName`, `driverLastName`, `driverStatus`, `driverLastStatusUpdate`, `driverWorkedThisMonth`) VALUES (DEFAULT, 7, NULL, 'BNM100147', 'Nikolay', 'Makarov', DEFAULT, DEFAULT, DEFAULT);
INSERT INTO `Drivers` (`idDriver`, `driverCityId`, `driverVehicleId`, `driverPersNumber`, `driverFirstName`, `driverLastName`, `driverStatus`, `driverLastStatusUpdate`, `driverWorkedThisMonth`) VALUES (DEFAULT, 7, NULL, 'NBV852369', 'Pavel', 'Nikitin', DEFAULT, DEFAULT, DEFAULT);
INSERT INTO `Drivers` (`idDriver`, `driverCityId`, `driverVehicleId`, `driverPersNumber`, `driverFirstName`, `driverLastName`, `driverStatus`, `driverLastStatusUpdate`, `driverWorkedThisMonth`) VALUES (DEFAULT, 8, NULL, 'CXZ963258', 'Alexey', 'Zakharov', DEFAULT, DEFAULT, DEFAULT);
INSERT INTO `Drivers` (`idDriver`, `driverCityId`, `driverVehicleId`, `driverPersNumber`, `driverFirstName`, `driverLastName`, `driverStatus`, `driverLastStatusUpdate`, `driverWorkedThisMonth`) VALUES (DEFAULT, 8, NULL, 'LKJ741001', 'Arseniy', 'Smirnov', DEFAULT, DEFAULT, DEFAULT);
INSERT INTO `Drivers` (`idDriver`, `driverCityId`, `driverVehicleId`, `driverPersNumber`, `driverFirstName`, `driverLastName`, `driverStatus`, `driverLastStatusUpdate`, `driverWorkedThisMonth`) VALUES (DEFAULT, 9, NULL, 'HGF478523', 'Denis', 'Zaycev', DEFAULT, DEFAULT, DEFAULT);
INSERT INTO `Drivers` (`idDriver`, `driverCityId`, `driverVehicleId`, `driverPersNumber`, `driverFirstName`, `driverLastName`, `driverStatus`, `driverLastStatusUpdate`, `driverWorkedThisMonth`) VALUES (DEFAULT, 9, NULL, 'DSA699632', 'Stepan', 'Solovyev', DEFAULT, DEFAULT, DEFAULT);
INSERT INTO `Drivers` (`idDriver`, `driverCityId`, `driverVehicleId`, `driverPersNumber`, `driverFirstName`, `driverLastName`, `driverStatus`, `driverLastStatusUpdate`, `driverWorkedThisMonth`) VALUES (DEFAULT, 10, NULL, 'POI145698', 'Fedor', 'Borisov', DEFAULT, DEFAULT, DEFAULT);
INSERT INTO `Drivers` (`idDriver`, `driverCityId`, `driverVehicleId`, `driverPersNumber`, `driverFirstName`, `driverLastName`, `driverStatus`, `driverLastStatusUpdate`, `driverWorkedThisMonth`) VALUES (DEFAULT, 11, NULL, 'UYT877896', 'Anton', 'Yakovlev', DEFAULT, DEFAULT, DEFAULT);
INSERT INTO `Drivers` (`idDriver`, `driverCityId`, `driverVehicleId`, `driverPersNumber`, `driverFirstName`, `driverLastName`, `driverStatus`, `driverLastStatusUpdate`, `driverWorkedThisMonth`) VALUES (DEFAULT, 12, NULL, 'REW654123', 'Gleb', 'Grigoryev', DEFAULT, DEFAULT, DEFAULT);
INSERT INTO `Drivers` (`idDriver`, `driverCityId`, `driverVehicleId`, `driverPersNumber`, `driverFirstName`, `driverLastName`, `driverStatus`, `driverLastStatusUpdate`, `driverWorkedThisMonth`) VALUES (DEFAULT, 13, NULL, 'QMN332145', 'Dmitriy', 'Romanov', DEFAULT, DEFAULT, DEFAULT);
INSERT INTO `Drivers` (`idDriver`, `driverCityId`, `driverVehicleId`, `driverPersNumber`, `driverFirstName`, `driverLastName`, `driverStatus`, `driverLastStatusUpdate`, `driverWorkedThisMonth`) VALUES (DEFAULT, 14, NULL, 'BVC569877', 'Yaroslav', 'Vorobyev', DEFAULT, DEFAULT, DEFAULT);
INSERT INTO `Drivers` (`idDriver`, `driverCityId`, `driverVehicleId`, `driverPersNumber`, `driverFirstName`, `driverLastName`, `driverStatus`, `driverLastStatusUpdate`, `driverWorkedThisMonth`) VALUES (DEFAULT, 15, NULL, 'XZL789654', 'Semen', 'Sergeev', DEFAULT, DEFAULT, DEFAULT);
INSERT INTO `Drivers` (`idDriver`, `driverCityId`, `driverVehicleId`, `driverPersNumber`, `driverFirstName`, `driverLastName`, `driverStatus`, `driverLastStatusUpdate`, `driverWorkedThisMonth`) VALUES (DEFAULT, 17, NULL, 'KJH456321', 'Grigoriy', 'Kuzmin', DEFAULT, DEFAULT, DEFAULT);

COMMIT;