USE sistema_reservas;

SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS reserva;
DROP TABLE IF EXISTS usuario;
DROP TABLE IF EXISTS vehiculo;

SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE vehiculo (
    vehiculo_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    numero_placa VARCHAR(20) NOT NULL,
    modelo VARCHAR(50) NOT NULL,
    fecha_fabricacion DATE,
    kilometraje INT NOT NULL,
    capacidad_personas SMALLINT NOT NULL,
    precio_dia FLOAT NOT NULL,
    imagen MEDIUMBLOB,
    marca VARCHAR(50) NOT NULL,
    tipo_combustible VARCHAR(30) NOT NULL,
    transmision VARCHAR(30) NOT NULL,
    carroceria VARCHAR(30) NOT NULL,
    disponible BOOLEAN NOT NULL
);

CREATE TABLE usuario (
    usuarioId BIGINT AUTO_INCREMENT PRIMARY KEY,
    rfc VARCHAR(13) NOT NULL,
    email VARCHAR(255) NOT NULL,
    nombreCompleto VARCHAR(255) NOT NULL,
    numeroTelefono VARCHAR(20) NOT NULL,
    fechaNacimiento DATE NOT NULL,
    contrasena VARCHAR(255) NOT NULL,
    esAdministrador BOOLEAN NOT NULL,
    direccion VARCHAR(255) NOT NULL,
    pais VARCHAR(100) NOT NULL,
    ciudad VARCHAR(100) NOT NULL
);

CREATE TABLE reserva (
    reservaId BIGINT AUTO_INCREMENT PRIMARY KEY,
    fechaInicio DATE NOT NULL,
    fechaFin DATE NOT NULL,
    asientoInfantil BOOLEAN NOT NULL,
    asientoElevador BOOLEAN NOT NULL,
    conductoresAdicionales BOOLEAN NOT NULL,
    pagoTotal FLOAT NOT NULL,
    fechaReserva DATE NOT NULL,
    cancelada BOOLEAN NOT NULL,
    vehiculoId BIGINT NOT NULL,
    usuarioID BIGINT NOT NULL,
    FOREIGN KEY (vehiculoId) REFERENCES vehiculo(vehiculo_id),
    FOREIGN KEY (usuarioID) REFERENCES usuario(usuarioId)
);

DROP TABLE IF EXISTS Tarjeta;
CREATE TABLE Tarjeta (
    tarjetaId BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombreTitular VARCHAR(100) NOT NULL,
    numeroTarjeta VARCHAR(16) NOT NULL UNIQUE,
    cvv VARCHAR(4) NOT NULL,
    fechaExpiracion DATE NOT NULL
);