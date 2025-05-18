-- Disponibilidad
INSERT INTO disponibilidades_vehiculo (id, descripcion) VALUES
    (1, 'Disponible'),
    (2, 'En mantenimiento'),
    (3, 'Reservado');

-- Tipo de Combustible
INSERT INTO tipos_combustible_vehiculo (id, descripcion) VALUES
    (1, 'Gasolina'),
    (2, 'Eléctrico'),
    (3, 'Híbrido'),
    (4, 'Diésel');

-- Tipo de Carrocería
INSERT INTO tipos_carroceria_vehiculo (id, descripcion) VALUES
    (1, 'Sedán'),
    (2, 'SUV'),
    (3, 'Coupé');

-- Transmisión
INSERT INTO transmisiones_vehiculo (id, descripcion) VALUES
    (1, 'Automática'),
    (2, 'Manual');

-- Marca
INSERT INTO marcas_vehiculo (id, nombre) VALUES
    (1, 'Toyota'),
    (2, 'Honda'),
    (3, 'Tesla'),
    (4, 'Mazda'),
    (5, 'Ford'),
    (6, 'BMW');

-- Tipo de Mantenimiento
INSERT INTO tipos_mantenimiento (id, descripcion) VALUES
    (1, 'Cambio de aceite'),
    (2, 'Revisión general'),
    (3, 'Frenos'),
    (4, 'Preventivo'),
    (5, 'Correctivo');

-- Vehículos
INSERT INTO vehiculos (
    numero_placa, marca_id, modelo, fecha_fabricacion,
    tipo_combustible_id, kilometraje, transmision_id,
    capacidad_personas, disponibilidad_id, precio_dia, imagen, tipo_carroceria_id
)
VALUES
    ('ABC1234', 1, 'Corolla', '2018-05-10', 1, 50000, 1, 5, 1, 450.00, '', 1),
    ('XYZ5678', 2, 'Civic', '2019-09-22', 1, 40000, 2, 5, 1, 470.00, '', 1),
    ('JKL8901', 3, 'Model 3', '2021-03-14', 2, 20000, 1, 5, 1, 850.00, '', 1),
    ('MNO4321', 1, 'Rav4', '2017-07-18', 3, 75000, 1, 7, 1, 600.00, '', 2),
    ('DEF5678', 4, 'CX-5', '2020-02-11', 1, 30000, 1, 5, 1, 520.00, '', 2),
    ('GHI9012', 5, 'Mustang', '2022-06-01', 1, 12000, 2, 4, 1, 950.00, '', 3),
    ('LMN3456', 6, 'X5', '2021-10-20', 4, 25000, 1, 5, 1, 1100.00, '', 2),
    ('PQR7890', 2, 'Accord', '2020-04-15', 1, 35000, 1, 5, 1, 550.00, '', 1);

-- La imagen se cargará en el campo BLOB
UPDATE vehiculos SET imagen = LOAD_FILE('/Users/diego/Desktop/Imagenes/corolla.jpg') WHERE numero_placa = 'ABC1234';
UPDATE vehiculos SET imagen = LOAD_FILE('/Users/diego/Desktop/Imagenes/civic.png') WHERE numero_placa = 'XYZ5678';
UPDATE vehiculos SET imagen = LOAD_FILE('/Users/diego/Desktop/Imagenes/model3.png') WHERE numero_placa = 'JKL8901';
UPDATE vehiculos SET imagen = LOAD_FILE('/Users/diego/Desktop/Imagenes/rav4.jpg') WHERE numero_placa = 'MNO4321';
UPDATE vehiculos SET imagen = LOAD_FILE('/Users/diego/Desktop/Imagenes/cx5.png') WHERE numero_placa = 'DEF5678';
UPDATE vehiculos SET imagen = LOAD_FILE('/Users/diego/Desktop/Imagenes/mustang.png') WHERE numero_placa = 'GHI9012';
UPDATE vehiculos SET imagen = LOAD_FILE('/Users/diego/Desktop/Imagenes/x5.jpg') WHERE numero_placa = 'LMN3456';
UPDATE vehiculos SET imagen = LOAD_FILE('/Users/diego/Desktop/Imagenes/accord.png') WHERE numero_placa = 'PQR7890';

-- Usuarios
INSERT INTO usuarios (
    rfc, email, nombre_completo, numero_telefono, fecha_nacimiento,
    contrasena, es_administrador, direccion, pais, ciudad
)
VALUES
    ('ABC1234567890', 'juan@example.com', 'Juan Pérez', '5523456789', '1990-05-12',
     'contrasena123', FALSE, 'Av. Siempre Viva 123', 'México', 'CDMX'),

    ('XYZ9876543210', 'laura@example.com', 'Laura García', '5587654321', '1988-11-03',
     'segura456', FALSE, 'Calle Falsa 456', 'México', 'Guadalajara'),

    ('ADM1234567890', 'admin@rentacar.com', 'Administrador General', '5512345678', '1985-02-20',
     'adminsecurepass', TRUE, 'Oficinas Centrales 100', 'México', 'CDMX');

-- Clientes
INSERT INTO clientes (
    id, numero_licencia, fecha_emision_licencia, fecha_expiracion_licencia
)
VALUES
    (1, 'LIC123456', '2020-01-01', '2026-01-01'),
    (2, 'LIC654321', '2019-06-15', '2025-06-15');

-- Reservas
INSERT INTO reservas (
    cliente_id, vehiculo_id, fecha_inicio, fecha_fin,
    cancelada, fecha_reserva, pago_total,
    asiento_infantil, asiento_elevador, conductores_adicionales
)
VALUES (
    1, 1, '2025-04-10', '2025-04-15',
    FALSE, CURDATE(), 2250.00, -- 5 días * $450
    TRUE, FALSE, FALSE
);

-- Mantenimiento
INSERT INTO mantenimientos (
    vehiculo_id, tipo_mantenimiento_id, fecha_inicio, fecha_fin, costo
)
VALUES (
    2, 1, '2025-03-20', '2025-03-21', 1500.00 -- Mantenimiento preventivo para el vehículo 2
);