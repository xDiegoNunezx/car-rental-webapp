# Proyecto de Renta de Autos / Car Rental System

## Español 🇪🇸

Sistema web de renta de autos desarrollado con Spring Boot, Thymeleaf, JPA, JWT y MariaDB. Utiliza Bootstrap para el diseño visual y proporciona funcionalidades para que los usuarios se registren, consulten vehículos disponibles, realicen reservas, y para que los administradores gestionen autos, mantenimientos y clientes.

## English 🇬🇧

Web-based car rental system developed with Spring Boot, Thymeleaf, JPA, JWT, and MariaDB. It uses Bootstrap for UI design and offers features for users to register, browse available vehicles, make reservations, and for administrators to manage cars, maintenance, and customers.

# Manual de Uso del Sistema

## Requisitos Previos
- Java JDK 17 o superior
- Base de datos MariaDB instalada
- Maven 3.6+ (para manejo de dependencias)
- Conexión a internet (para descargar dependencias)

## Instalación y Configuración

### Clonar el repositorio
```bash
git clone https://github.com/xDiegoNunezx/car-rental-webapp
cd nombre_del_proyecto
```

### Configuración de MariaDB
spring.datasource.url=jdbc:mariadb://localhost:3306/nombre_base_datos


spring.datasource.username=tu_usuario


spring.datasource.password=tu_contraseña


spring.datasource.driver-class-name=org.mariadb.jdbc.Driver

### Creación de tablas 
Dentro del proyecto, ve a la siguiente ruta: 
- src/main/resources/database

En ella encontrarás dos archivos que contiene el DDL y el DML para crear las tablas y realizar algunas inserciones para el correcto funcionamiento del sistema.

El mismo proyecto cuenta con un Unit Test, el cuál, al correrlo ejecutará estos mismos scripts.

**Nota:** En el archivo schema.sql es necesario indicar el nombre de tu base de datos en la primera línea.

## Primeros Pasos
Una vez iniciada la aplicación, puedes acceder a: http://localhost:8080/public

Primero se mostrará la pantalla principal del sistema. En la parte superior podrás ver una barra de navegación en el que podrás navegar en las siguientes pestañas:
- Inicio: Presentación del negocio de renta de autos.
- Renta: Se presentan automóviles disponibles donde el usuario podrá buscar por fecha de disponibilidad.
- Automóviles: Todos los automóviles con los que cuenta el negocio.
- Nosotros: Información del negocio.
- Contacto: Datos de contacto del negocio.
- FAQ: Preguntas y respuestas.
- Login: Inicio de sesión tanto para usuarios como para administradores.

## Iniciar sesión como cliente
Al ir a la pestaña de Login, podrás observar un formulario en el que podrás ingresar el email y contraseña del cliente.
Algunos datos de prueba son:

| Email | Contraseña |
|-------|------------|
| juan@example.com | password |
| laura@example.com | password |

En caso de querer registrar un nuevo usuario puedes dar click en **Regístrate aquí**.

Te llevará a una pantalla en donde encontrarás un formulario para llenar los datos solicitas para crear un cliente.

## Iniciar sesión como administrador
En este sistema sólo existe una cuenta de administrador:

| Email | Contraseña |
|-------|------------|
| admin@rentacar.com | password |

## Rentar un automóvil
Para rentar un automóvil es necesario estar autenticado como cliente.

Una vez hecho esto, puedes ir a la pestaña de Automóviles para ver algún auto que te interese, o ir a la pestaña de renta para buscar por fechas.

Cuando encuentres algún automóvil atractivo dar click en **Reservar ahora**. Esto te llevará a un formulario donde deberás ingresar algunas opciones con respecto a tu renta.

Al confirmar la renta se te llevará a tu pestaña de perfil donde podrás ver tus autos rentados.

## Página de perfil cliente
Al estar autenticado como cliente, en el menú de navegación aparecerá una pestaña de **Perfil**.

En esta página se mostrarán los datos personales del cliente junto con una lista con el historial de reservas. 

En este historial se puede generar un PDF de comprobante de cada reserva. Además, para las reservas activas, podrás realizar una cancelación.

## Revisar menú administrador
Al estar autenticado como administrador, en el menú de navegación aparecerá una pestaña de **Administración**.

Aquí encontraremos un menú con tres pantallas:
- Mantenimiento de Autos

Esta pantalla te mostrará una lista con todos los vehículos en la flota. Puedes elegir entre ver todos los vehículo o los vehículos en mantenimiento.

Esta misma pantalla contiene una opción para agregar más automóviles a la flota. Para hacer dar click en el botón verde que dice **Nuevo Vehículo**. Se mostrará un formulario para llenar los datos del nuevo vehículo.

Para cada vehículo puedes entrar a una pestalla de detalles. En ella podrás editar sus datos, ver su historial de mantenimientos, ver su historial de reservas y eliminarlo.

En el historial de mantenimientos podrás añadir un nuevo mantenimiento así como editar o eliminar los mantenimientos ya existentes.

- Reservas

En esta pantalla encontrarás todas las reservas realizadas por todos los clientes. 

- Métricas de desempeño

Esta pantalla muestra un conjunto de métricas útiles que describen cosas como los vehículos más rentados, los ingresos mensuales, etc. 














