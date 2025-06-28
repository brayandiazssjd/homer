-- Primero crear la base de datos con la query en "creacion_hotel.sql"
-- Meter datos en el orden sugerido
-- NOTA: Tener los archivos.csv en una carpeta con permisos (la carpeta C:)
-- 1. Personas
COPY persona(cedula,nombres,apellidos,direccion,telefono,email)
FROM 'S:/Current/homer/src/main/database/datos/persona.csv'
DELIMITER ',' CSV HEADER;

-- 2. Usuarios
COPY usuario(cedula) 
FROM 'S:/Current/homer/src/main/database/datos/usuario.csv' 
DELIMITER ',' CSV HEADER;

-- 3. Area
COPY area(idArea,nombreA,descripcionA) 
FROM 'S:/Current/homer/src/main/database/datos/area.csv' 
DELIMITER ',' CSV HEADER;

-- 4. Cargo
COPY cargo(idCargo,nombreC,descripcionC,idArea) 
FROM 'S:/Current/homer/src/main/database/datos/cargo.csv' 
DELIMITER ',' CSV HEADER;

-- 5. Empleado
COPY empleado(cedula, idCargo) 
FROM 'S:/Current/homer/src/main/database/datos/empleado.csv' 
DELIMITER 'datos,' CSV HEADER;

-- 6. Habitacion
COPY habitacion(noHabitacion,categoria,precionoche,estado) 
FROM 'S:/Current/homer/src/main/database/datos/habitacion.csv' 
DELIMITER ',' CSV HEADER;

-- 7. Reserva
COPY reserva(idReserva,fechaEntrada,fechaSalida,noHabitacion,cedula) 
FROM 'S:/Current/homer/src/main/database/datos/reserva.csv' 
DELIMITER ',' CSV HEADER;

-- 8. Servicio
COPY servicio(idServicio,detalles,nombre,costo) 
FROM 'S:/Current/homer/src/main/database/datos/servicio.csv' 
DELIMITER ',' CSV HEADER;

-- 9. Consume
COPY consume(fecha,idReserva,idServicio,idConsumo)
FROM 'S:/Current/homer/src/main/database/datos/consume.csv' 
DELIMITER ',' CSV HEADER;