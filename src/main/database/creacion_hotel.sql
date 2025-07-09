--Creacion Base de datos--
CREATE DATABASE "Hotel" 

-- Creacion Tablas--
CREATE TABLE persona(
    cedula numeric(10),
    nombres varchar(255),
    apellidos varchar(255),
    direccion varchar(255),
    telefono numeric(10),
    email varchar(255)
);
CREATE TABLE usuario(
    cedula numeric(10)
);
CREATE TABLE reserva(
    fechaEntrada timestamp with time zone,
    fechaSalida timestamp with time zone,
    noHabitacion numeric(10) NOT NULL,
    cedula numeric(10)
);
CREATE TABLE habitacion(
    noHabitacion numeric(10),
    categoria varchar(9),
    precionoche numeric(10),
    estado varchar(14)
);
CREATE TABLE servicio(
    idServicio numeric(10),
    detalles varchar(255),
    nombre varchar(255),
    costo numeric(10)
);
CREATE TABLE consume(
    fecha timestamp with time zone,
    fechaEntrada timestamp with time zone,
    noHabitacion numeric(10) NOT NULL,
    cedula numeric(10),
    idServicio numeric(10)
);
CREATE TABLE empleado(
    cedula numeric(10),
    idCargo numeric(10)
);
CREATE TABLE cargo(
    idCargo numeric(10),
    nombreC varchar(255),
    descripcionC varchar(255),
    idArea numeric(10)
);
CREATE TABLE Area(
    idArea numeric(10),
    nombreA varchar(255),
    descripcionA varchar(255)
);
--Asignacion de llaves--
ALTER TABLE persona
ADD CONSTRAINT pkpersona
PRIMARY KEY (cedula);

ALTER TABLE usuario
ADD CONSTRAINT pkusuario
PRIMARY KEY (cedula);

ALTER TABLE empleado
ADD CONSTRAINT pkempleado
PRIMARY KEY (cedula);

ALTER TABLE habitacion
ADD CONSTRAINT pkhabitacion
PRIMARY KEY (noHabitacion);

ALTER TABLE reserva
ADD CONSTRAINT pkreserva
PRIMARY KEY (cedula,noHabitacion,fechaEntrada);

ALTER TABLE consume
ADD CONSTRAINT pkconsumo
PRIMARY KEY (fecha,
    fechaEntrada,
    noHabitacion,
    cedula,
    idServicio);

ALTER TABLE servicio
ADD CONSTRAINT pkservicio
PRIMARY KEY (idServicio);

ALTER TABLE cargo
ADD CONSTRAINT pkcargo
PRIMARY KEY (idCargo);

ALTER TABLE area
ADD CONSTRAINT pkarea
PRIMARY KEY (idArea);

ALTER TABLE usuario
ADD CONSTRAINT fkusuariocedula
FOREIGN KEY (cedula)
REFERENCES persona(cedula);

ALTER TABLE empleado
ADD CONSTRAINT fkempleadocedula
FOREIGN KEY (cedula)
REFERENCES persona(cedula);

ALTER TABLE reserva
ADD CONSTRAINT fkreservahabitacion
FOREIGN KEY (nohabitacion)
REFERENCES habitacion(nohabitacion);

ALTER TABLE reserva
ADD CONSTRAINT fkreservausuario
FOREIGN KEY (cedula)
REFERENCES usuario(cedula);

ALTER TABLE consume
ADD CONSTRAINT fkconsumeservicio
FOREIGN KEY (idServicio)
REFERENCES servicio(idServicio);

ALTER TABLE consume
ADD CONSTRAINT fkconsumereserva
FOREIGN KEY (cedula, noHabitacion, fechaEntrada)
REFERENCES reserva(cedula, noHabitacion, fechaEntrada);

ALTER TABLE empleado
ADD CONSTRAINT fkempleadocargo
FOREIGN KEY (idCargo)
REFERENCES cargo(idCargo);

ALTER TABLE cargo
ADD CONSTRAINT fkcargoarea
FOREIGN KEY (idArea)
REFERENCES area(idArea);

ALTER TABLE habitacion
ADD CONSTRAINT ckcategoria
CHECK (categoria IN ('sencilla', 'doble', 'suite'));

ALTER TABLE habitacion
ADD CONSTRAINT ckestado
CHECK (estado IN ('disponible', 'no disponible', 'mantenimiento'));