1. GESTIÓN DE CLIENTES Y RESERVAS

-- 1.1 Consultar todos los clientes registrados
SELECT p.cedula, CONCAT(p.nombres, ' ', p.apellidos) as cliente, p.direccion, p.telefono, p.email
FROM persona p NATURAL JOIN usuario u ORDER BY p.apellidos, p.nombres;

-- 1.2 Consultar reservas activas
SELECT r.idReserva,  p.cedula, CONCAT(p.nombres, ' ', p.apellidos) AS cliente, 
    r.fechaEntrada, r.fechaSalida, r.noHabitacion, h.categoria, h.precionoche,
    (EXTRACT(EPOCH FROM (r.fechaSalida - r.fechaEntrada)) / 86400) AS dias_estancia,
    (h.precionoche * EXTRACT(EPOCH FROM (r.fechaSalida - r.fechaEntrada)) / 86400) AS total_habitacion
FROM reserva r  NATURAL JOIN persona p  NATURAL JOIN habitacion h 
ORDER BY r.fechaEntrada;


-- 1.3 Historial completo de reservas por cliente
SELECT p.cedula, CONCAT(p.nombres, ' ', p.apellidos) as cliente, COUNT(r.idReserva) as total_reservas,
    MIN(r.fechaEntrada) as primera_reserva, MAX(r.fechaSalida) as ultima_reserva,
    SUM(h.precionoche * EXTRACT(EPOCH FROM (r.fechaSalida - r.fechaEntrada)) / 86400) as total_gastado_habitaciones
FROM persona p NATURAL JOIN usuario u
LEFT JOIN reserva r ON u.cedula = r.cedula
LEFT JOIN habitacion h ON r.noHabitacion = h.noHabitacion
GROUP BY p.cedula, p.nombres, p.apellidos ORDER BY total_reservas DESC, p.apellidos;

1.4 Insertar nueva reserva (ejemplo de procedimiento)
INSERT INTO reserva (idReserva, fechaEntrada, fechaSalida, noHabitacion, cedula) 
VALUES (1011, '2024-08-15 15:00:00-05', '2024-08-18 11:00:00-05', 102, 5555555555);

1.5 Actualizar información de cliente
UPDATE persona SET telefono = 3001111111, email = 'nuevo.email@gmail.com' WHERE cedula = 1234567890;

----------------------------------------------
2. DISPONIBILIDAD Y ESTADO DE HABITACIONES

-- 2.1 Consultar disponibilidad actual de habitaciones
SELECT  h.noHabitacion, h.categoria, h.precionoche, h.estado,
    CASE 
        WHEN h.estado = 'disponible' AND h.noHabitacion NOT IN (
            SELECT r.noHabitacion 
            FROM reserva r 
            WHERE CURRENT_TIMESTAMP BETWEEN r.fechaEntrada AND r.fechaSalida
        ) THEN 'LIBRE'
        WHEN h.estado = 'disponible' AND h.noHabitacion IN (
            SELECT r.noHabitacion 
            FROM reserva r 
            WHERE CURRENT_TIMESTAMP BETWEEN r.fechaEntrada AND r.fechaSalida
        ) THEN 'OCUPADA'
        ELSE UPPER(h.estado)
    END as estado_actual
FROM habitacion h
ORDER BY h.categoria, h.noHabitacion;

-- 2.2 Disponibilidad por categoría
SELECT 
    h.categoria,
    COUNT(*) as total_habitaciones,
    SUM(CASE WHEN h.estado = 'disponible' THEN 1 ELSE 0 END) as disponibles,
    SUM(CASE WHEN h.estado = 'no disponible' THEN 1 ELSE 0 END) as no_disponibles,
    SUM(CASE WHEN h.estado = 'mantenimiento' THEN 1 ELSE 0 END) as en_mantenimiento
FROM habitacion h GROUP BY h.categoria ORDER BY h.categoria;

-- 2.3 Habitaciones disponibles para un rango de fechas específico
SELECT h.noHabitacion, h.categoria, h.precionoche, h.estado
FROM habitacion h
WHERE h.estado = 'disponible'
  AND h.noHabitacion NOT IN (
    SELECT r.noHabitacion 
    FROM reserva r
    WHERE (r.fechaEntrada <= '2024-08-15 15:00:00-05' AND r.fechaSalida >= '2024-08-10 14:00:00-05')
  )
ORDER BY h.categoria, h.precionoche;

-- 2.4 Ocupación actual del hotel
SELECT 
    COUNT(DISTINCT r.noHabitacion) AS habitaciones_ocupadas,
    COUNT(CASE 
        WHEN r.noHabitacion IS NULL AND h.estado = 'disponible' THEN 1 
    END) AS habitaciones_libres,
    COUNT(CASE WHEN h.estado = 'mantenimiento' THEN 1 END) AS en_mantenimiento,
    COUNT(*) AS total_habitaciones,
    ROUND(COUNT(DISTINCT r.noHabitacion) * 100.0 / COUNT(*), 2) AS porcentaje_ocupacion
FROM habitacion h
LEFT JOIN reserva r ON h.noHabitacion = r.noHabitacion;


-------------------------------------------
3. ACTUALIZACIÓN DE ESTADOS DE HABITACIONES
-- 3.1 Cambiar estado de habitación a mantenimiento
UPDATE habitacion SET estado = 'no dispobible' WHERE noHabitacion = 202;

3.2 Marcar habitación como disponible después de mantenimiento
UPDATE habitacion SET estado = 'mantenimiento' WHERE noHabitacion = 202;

----------------------------------------------
4. REGISTRO DE CONSUMOS ADICIONALES
-- 4.1 Registrar nuevo consumo para una reserva
INSERT INTO consume (fecha, idReserva, idServicio, idConsumo) VALUES (CURRENT_TIMESTAMP, 1001, 5, 33);

-- 4.2 Consultar consumos por reserva
SELECT r.idReserva, CONCAT(p.nombres, ' ', p.apellidos) as cliente,
    s.idServicio, s.nombre as servicio, s.costo, c.fecha as fecha_consumo
FROM consume c NATURAL JOIN reserva r NATURAL JOIN servicio s NATURAL JOIN persona p 
WHERE r.idReserva = 1002 ORDER BY c.fecha;

-- 4.3 Total de consumos por reserva
SELECT r.idReserva, CONCAT(p.nombres, ' ', p.apellidos) as cliente,
    r.noHabitacion, COUNT(c.idConsumo) as total_servicios_consumidos,
    SUM(s.costo) as total_consumos
FROM reserva r
JOIN persona p ON r.cedula = p.cedula
LEFT JOIN consume c ON r.idReserva = c.idReserva
LEFT JOIN servicio s ON c.idServicio = s.idServicio
GROUP BY r.idReserva, p.nombres, p.apellidos, r.noHabitacion HAVING COUNT(c.idConsumo) > 0 ORDER BY total_servicios_consumidos DESC;

-----------------------------------------------
5. GESTIÓN DE EMPLEADOS
-- 5.1 Consultar todos los empleados con su información completa
SELECT p.cedula, CONCAT(p.nombres, ' ', p.apellidos) as empleado,
    c.nombreC as cargo, a.nombreA as area, p.telefono, p.email,p.direccion
FROM empleado e NATURAL JOIN persona p NATURAL JOIN cargo c NATURAL JOIN area a
ORDER BY a.nombreA, c.nombreC, p.apellidos;

-- 5.2 Empleados por área
SELECT a.nombreA as area, COUNT(e.cedula) as total_empleados, STRING_AGG(CONCAT(p.nombres, ' ', p.apellidos), ', ') as empleados
FROM area a
LEFT JOIN cargo c ON a.idArea = c.idArea
LEFT JOIN empleado e ON c.idCargo = e.idCargo
LEFT JOIN persona p ON e.cedula = p.cedula
GROUP BY a.idArea, a.nombreA ORDER BY total_empleados DESC;


-- 5.3 Insertar nuevo empleado (Primero insertar en persona, luego en empleado)
INSERT INTO persona (cedula, nombres, apellidos, direccion, telefono, email) 
VALUES (9999999999, 'Nuevo', 'Empleado Test', 'Dirección Test', 3009999999, 'nuevo@hotel.com');
INSERT INTO empleado (cedula, idCargo) VALUES (9999999999, 1);

-- 5.4 Actualizar cargo de empleado
UPDATE empleado SET idCargo = 2 WHERE cedula = 9999999999;

-- 5.5 Eliminar empleado (mantiene persona, elimina solo relación laboral)
DELETE FROM empleado WHERE cedula = 9999999999;

------------------------------------------------
6. ADMINISTRACIÓN DE SERVICIOS

-- 6.1 Consultar todos los servicios disponibles
SELECT s.idServicio, s.nombre, s.detalles, s.costo, COUNT(c.idConsumo) as veces_consumido,
    SUM(s.costo) as ingresos_generados
FROM servicio s LEFT JOIN consume c ON s.idServicio = c.idServicio
GROUP BY s.idServicio, s.nombre, s.detalles, s.costo ORDER BY veces_consumido DESC;

-- 6.2 Servicios más populares
SELECT s.nombre, COUNT(c.idConsumo) as total_consumos, SUM(s.costo) as ingresos_totales, ROUND(AVG(s.costo), 0) as precio_promedio
FROM servicio s NATURAL JOIN consume c 
GROUP BY s.idServicio, s.nombre ORDER BY total_consumos DESC;

-- 6.3 Actualizar precio de servicio
UPDATE servicio SET costo = 28000 WHERE idServicio = 1;

-- 6.4 Insertar nuevo servicio
INSERT INTO servicio (idServicio, detalles, nombre, costo) VALUES (13, 'Servicio de niñera por horas', 'Cuidado Infantil', 25000);

---------------------------------------------------------
7. CONSULTAS ADMINISTRATIVAS Y ANÁLISIS

-- 7.4 Análisis de ocupación por categoría de habitación
SELECT  h.categoria, COUNT(h.noHabitacion) as total_habitaciones,
    COUNT(r.idReserva) as total_reservas,
    ROUND(COUNT(r.idReserva) * 100.0 / COUNT(h.noHabitacion), 2) as porcentaje_ocupacion,
    SUM(h.precionoche * EXTRACT(EPOCH FROM (r.fechaSalida - r.fechaEntrada)) / 86400) as ingresos_categoria
FROM habitacion h
LEFT JOIN reserva r ON h.noHabitacion = r.noHabitacion
GROUP BY h.categoria ORDER BY porcentaje_ocupacion DESC;

-- 7.5 Estancia promedio por tipo de cliente
SELECT  h.categoria, COUNT(r.idReserva) as total_reservas,
    ROUND(AVG(EXTRACT(EPOCH FROM (r.fechaSalida - r.fechaEntrada)) / 86400), 2) as estancia_promedio_dias,
    ROUND(AVG(h.precionoche), 0) as precio_promedio_noche
FROM reserva r
JOIN habitacion h ON r.noHabitacion = h.noHabitacion
GROUP BY h.categoria ORDER BY estancia_promedio_dias DESC;

-- 7.6 Reporte financiero consolidado
SELECT  'INGRESOS HABITACIONES' as concepto,
    SUM(h.precionoche * EXTRACT(EPOCH FROM (r.fechaSalida - r.fechaEntrada)) / 86400) as monto
FROM reserva r
JOIN habitacion h ON r.noHabitacion = h.noHabitacion
UNION ALL
SELECT 
    'INGRESOS SERVICIOS',
    SUM(s.costo)
FROM consume c
JOIN servicio s ON c.idServicio = s.idServicio
UNION ALL
SELECT 
    'TOTAL GENERAL',
    (SELECT SUM(h.precionoche * EXTRACT(EPOCH FROM (r.fechaSalida - r.fechaEntrada)) / 86400) 
     FROM reserva r JOIN habitacion h ON r.noHabitacion = h.noHabitacion) +
    (SELECT SUM(s.costo) FROM consume c JOIN servicio s ON c.idServicio = s.idServicio);