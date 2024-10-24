CREATE DATABASE  IF NOT EXISTS `your_database_name`;
USE `your_database_name`;
DROP TABLE IF EXISTS `USERS`;

CREATE TABLE `USERS` (
                         `ID` int NOT NULL AUTO_INCREMENT,
                         `FULL_NAME` varchar(500) DEFAULT NULL,
                         `PASSWORD` varchar(500) DEFAULT NULL,
                         `EMAIL` varchar(500) DEFAULT NULL,
                         `ADDRESS` varchar(100) DEFAULT NULL,
                         `PHONE` varchar(250) DEFAULT NULL,
                         `RESUME` varchar(500) DEFAULT NULL,
                         `DEPARTMENT` varchar(500) DEFAULT NULL,
                         `ROLE` varchar(500) DEFAULT NULL,
                         `ADMIN_ID` int,
                         PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


