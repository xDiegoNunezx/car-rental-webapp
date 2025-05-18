USE sistema_reservas;

SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS mantenimientos;
DROP TABLE IF EXISTS reservas;
DROP TABLE IF EXISTS vehiculos;
DROP TABLE IF EXISTS clientes;
DROP TABLE IF EXISTS usuarios;
DROP TABLE IF EXISTS tipos_mantenimiento;
DROP TABLE IF EXISTS marcas_vehiculo;
DROP TABLE IF EXISTS transmisiones_vehiculo;
DROP TABLE IF EXISTS tipos_carroceria_vehiculo;
DROP TABLE IF EXISTS tipos_combustible_vehiculo;
DROP TABLE IF EXISTS disponibilidades_vehiculo;

SET FOREIGN_KEY_CHECKS = 1;

-- Catálogos
CREATE TABLE disponibilidades_vehiculo (
    id BIGINT NOT NULL,
    descripcion VARCHAR(100) NOT NULL UNIQUE,
    PRIMARY KEY (id)
);

CREATE TABLE tipos_combustible_vehiculo (
    id BIGINT NOT NULL,
    descripcion VARCHAR(100) NOT NULL UNIQUE,
    PRIMARY KEY (id)
);

CREATE TABLE tipos_carroceria_vehiculo (
    id BIGINT NOT NULL,
    descripcion VARCHAR(100) NOT NULL UNIQUE,
    PRIMARY KEY (id)
);

CREATE TABLE transmisiones_vehiculo (
    id BIGINT NOT NULL,
    descripcion VARCHAR(100) NOT NULL UNIQUE,
    PRIMARY KEY (id)
);

CREATE TABLE marcas_vehiculo (
    id BIGINT NOT NULL,
    nombre VARCHAR(100) NOT NULL UNIQUE,
    PRIMARY KEY (id)
);

CREATE TABLE tipos_mantenimiento (
    id BIGINT NOT NULL,
    descripcion VARCHAR(100) NOT NULL UNIQUE,
    PRIMARY KEY (id)
);

-- Entidades principales
CREATE TABLE usuarios (
    id BIGINT AUTO_INCREMENT,
    rfc VARCHAR(13) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    nombre_completo VARCHAR(100) NOT NULL,
    numero_telefono VARCHAR(15) NOT NULL,
    fecha_nacimiento DATE NOT NULL,
    contrasena VARCHAR(100) NOT NULL,
    es_administrador BOOLEAN NOT NULL,
    direccion VARCHAR(200) NOT NULL,
    pais VARCHAR(50) NOT NULL,
    ciudad VARCHAR(50) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE clientes (
    id BIGINT NOT NULL,
    numero_licencia VARCHAR(20) NOT NULL UNIQUE,
    fecha_emision_licencia DATE NOT NULL,
    fecha_expiracion_licencia DATE NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (id) REFERENCES usuarios(id)
);

CREATE TABLE vehiculos (
    id BIGINT AUTO_INCREMENT,
    numero_placa VARCHAR(20) NOT NULL UNIQUE,
    marca_id BIGINT NOT NULL,
    modelo VARCHAR(50) NOT NULL,
    fecha_fabricacion DATE NOT NULL,
    tipo_combustible_id BIGINT NOT NULL,
    kilometraje INT NOT NULL,
    transmision_id BIGINT NOT NULL,
    capacidad_personas SMALLINT NOT NULL,
    disponibilidad_id BIGINT NOT NULL,
    precio_dia DECIMAL(10,2) NOT NULL,
    imagen MEDIUMBLOB,
    tipo_carroceria_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (marca_id) REFERENCES marcas_vehiculo(id),
    FOREIGN KEY (tipo_combustible_id) REFERENCES tipos_combustible_vehiculo(id),
    FOREIGN KEY (transmision_id) REFERENCES transmisiones_vehiculo(id),
    FOREIGN KEY (disponibilidad_id) REFERENCES disponibilidades_vehiculo(id),
    FOREIGN KEY (tipo_carroceria_id) REFERENCES tipos_carroceria_vehiculo(id)
);

CREATE TABLE reservas (
    id BIGINT AUTO_INCREMENT,
    cliente_id BIGINT NOT NULL,
    vehiculo_id BIGINT NOT NULL,
    fecha_inicio DATE NOT NULL,
    fecha_fin DATE NOT NULL,
    cancelada BOOLEAN NOT NULL,
    fecha_reserva DATE NOT NULL,
    pago_total DECIMAL(10,2) NOT NULL,
    asiento_infantil BOOLEAN NOT NULL,
    asiento_elevador BOOLEAN NOT NULL,
    conductores_adicionales BOOLEAN NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (cliente_id) REFERENCES clientes(id),
    FOREIGN KEY (vehiculo_id) REFERENCES vehiculos(id)
);

CREATE TABLE mantenimientos (
    id BIGINT AUTO_INCREMENT,
    vehiculo_id BIGINT NOT NULL,
    tipo_mantenimiento_id BIGINT NOT NULL,
    fecha_inicio DATE NOT NULL,
    fecha_fin DATE,
    costo DECIMAL(10,2) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (vehiculo_id) REFERENCES vehiculos(id),
    FOREIGN KEY (tipo_mantenimiento_id) REFERENCES tipos_mantenimiento(id)
);