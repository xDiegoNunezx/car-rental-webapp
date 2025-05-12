INSERT INTO vehiculo (
    numero_placa, modelo, fecha_fabricacion, kilometraje, capacidad_personas,
    precio_dia, imagen, marca, tipo_combustible, transmision, carroceria, disponible
)
VALUES
    ('ABC1234', 'Corolla', '2018-05-10', 50000, 5, 450.0, '', 'Toyota', 'Gasolina', 'Automática', 'Sedán', TRUE),
    ('XYZ5678', 'Civic', '2019-09-22', 40000, 5, 470.0, '', 'Honda', 'Gasolina', 'Manual', 'Sedán', TRUE),
    ('JKL8901', 'Model 3', '2021-03-14', 20000, 5, 850.0, '', 'Tesla', 'Eléctrico', 'Automática', 'Sedán', TRUE),
    ('MNO4321', 'Rav4', '2017-07-18', 75000, 7, 600.0, '', 'Toyota', 'Híbrido', 'Automática', 'SUV', TRUE),
    ('DEF5678', 'CX-5', '2020-02-11', 30000, 5, 520.0, '', 'Mazda', 'Gasolina', 'Automática', 'SUV', TRUE),
    ('GHI9012', 'Mustang', '2022-06-01', 12000, 4, 950.0, '', 'Ford', 'Gasolina', 'Manual', 'Coupé', TRUE),
    ('LMN3456', 'X5', '2021-10-20', 25000, 5, 1100.0, '', 'BMW', 'Diésel', 'Automática', 'SUV', TRUE),
    ('PQR7890', 'Accord', '2020-04-15', 35000, 5, 550.0, '', 'Honda', 'Gasolina', 'Automática', 'Sedán', TRUE);

-- La imagen se cargará en el campo BLOB
UPDATE vehiculo
SET imagen = LOAD_FILE('/Users/diego/Desktop/ProyectoDiplo/maquetado/src/main/resources/static/image/corolla.jpg')
WHERE numero_placa = 'ABC1234';

UPDATE vehiculo
SET imagen = LOAD_FILE('/Users/diego/Desktop/ProyectoDiplo/maquetado/src/main/resources/static/image/civic.png')
WHERE numero_placa = 'XYZ5678';

UPDATE vehiculo
SET imagen = LOAD_FILE('/Users/diego/Desktop/ProyectoDiplo/maquetado/src/main/resources/static/image/model3.png')
WHERE numero_placa = 'JKL8901';

UPDATE vehiculo
SET imagen = LOAD_FILE('/Users/diego/Desktop/ProyectoDiplo/maquetado/src/main/resources/static/image/rav4.jpg')
WHERE numero_placa = 'MNO4321';

UPDATE vehiculo
SET imagen = LOAD_FILE('/Users/diego/Desktop/ProyectoDiplo/maquetado/src/main/resources/static/image/cx5.png')
WHERE numero_placa = 'DEF5678';

UPDATE vehiculo
SET imagen = LOAD_FILE('/Users/diego/Desktop/ProyectoDiplo/maquetado/src/main/resources/static/image/mustang.png')
WHERE numero_placa = 'GHI9012';

UPDATE vehiculo
SET imagen = LOAD_FILE('/Users/diego/Desktop/ProyectoDiplo/maquetado/src/main/resources/static/image/x5.jpg')
WHERE numero_placa = 'LMN3456';

UPDATE vehiculo
SET imagen = LOAD_FILE('/Users/diego/Desktop/ProyectoDiplo/maquetado/src/main/resources/static/image/accord.png')
WHERE numero_placa = 'PQR7890';

-- Insertar usuarios
INSERT INTO usuario (
    rfc, email, nombreCompleto, numeroTelefono, fechaNacimiento,
    contrasena, esAdministrador, direccion, pais, ciudad
)
VALUES
    ('ABC1234567890', 'juan@example.com', 'Juan Pérez', '5523456789', '1990-05-12',
     'contrasena123', FALSE, 'Av. Siempre Viva 123', 'México', 'CDMX'),

    ('XYZ9876543210', 'laura@example.com', 'Laura García', '5587654321', '1988-11-03',
     'segura456', FALSE, 'Calle Falsa 456', 'México', 'Guadalajara');

-- Insertar una reserva para Juan (usuarioId = 1) usando un vehículo existente (por ejemplo, vehiculo_id = 1)
INSERT INTO reserva (
    fechaInicio, fechaFin, asientoInfantil, asientoElevador,
    conductoresAdicionales, pagoTotal, fechaReserva, cancelada,
    vehiculoId, usuarioID
)
VALUES (
           '2025-04-10', '2025-04-15', TRUE, FALSE,
           FALSE, 2250.0, CURDATE(), FALSE,
           1, 1
       );