CREATE DATABASE IF NOT EXISTS `animales`;
USE `animales`;

CREATE TABLE `especie` (
	id INT NOT NULL AUTO_INCREMENT,
	nombre VARCHAR(100) NOT NULL,
	PRIMARY KEY (id)
);

CREATE TABLE `animal` (
	codigo VARCHAR(50) NOT NULL,
	nombre VARCHAR(100) NOT NULL,
	especie INT NOT NULL,
	raza VARCHAR(100) DEFAULT NULL,
	sexo CHAR(1) DEFAULT NULL,
	edad INT(3) DEFAULT NULL,
	peso DECIMAL(6,2) DEFAULT NULL,
	observaciones VARCHAR(1000) DEFAULT NULL,
	primera_consulta DATE DEFAULT NULL,
	foto MEDIUMBLOB DEFAULT NULL,
	PRIMARY KEY (codigo),
	CONSTRAINT fk_especie_animal FOREIGN KEY (`especie`) REFERENCES `especie`(`id`)
);
