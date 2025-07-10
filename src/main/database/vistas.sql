-- 1.1 Vista de clientes registrados
CREATE VIEW v_clientes_registrados AS
SELECT p.cedula, CONCAT(p.nombres, ' ', p.apellidos) as cliente, p.direccion, p.telefono, p.email
FROM persona p NATURAL JOIN usuario u ORDER BY p.apellidos, p.nombres;

-- 1.2 Vista de reservas activas
SELECT r.fechaEntrada, r.noHabitacion,  p.cedula, CONCAT(p.nombres, ' ', p.apellidos) AS cliente, 
     r.fechaSalida, h.categoria, h.precionoche,
    (EXTRACT(EPOCH FROM (r.fechaSalida - r.fechaEntrada)) / 86400) AS dias_estancia,
    (h.precionoche * EXTRACT(EPOCH FROM (r.fechaSalida - r.fechaEntrada)) / 86400) AS total_habitacion
FROM reserva r  NATURAL JOIN persona p  NATURAL JOIN habitacion h 
ORDER BY r.fechaEntrada;

-- 1.3 Vista de historial de reservas por cliente
CREATE VIEW v_historial_reservas_cliente AS
SELECT p.cedula, CONCAT(p.nombres, ' ', p.apellidos) as cliente, COUNT(r.fechaEntrada) as total_reservas,
    MIN(r.fechaEntrada) as primera_reserva, MAX(r.fechaSalida) as ultima_reserva,
    SUM(h.precionoche * EXTRACT(EPOCH FROM (r.fechaSalida - r.fechaEntrada)) / 86400) as total_gastado_habitaciones
FROM persona p NATURAL JOIN usuario u
LEFT JOIN reserva r ON u.cedula = r.cedula
LEFT JOIN habitacion h ON r.noHabitacion = h.noHabitacion
GROUP BY p.cedula, p.nombres, p.apellidos ORDER BY total_reservas DESC, p.apellidos;

-- 2.1 Vista de disponibilidad actual de habitaciones
CREATE VIEW v_disponibilidad_actual AS
SELECT h.noHabitacion, h.categoria, h.precionoche, h.estado,
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

-- 2.2 Vista de disponibilidad por categoría
CREATE VIEW v_disponibilidad_categoria AS
SELECT 
    h.categoria,
    COUNT(*) as total_habitaciones,
    SUM(CASE WHEN h.estado = 'disponible' THEN 1 ELSE 0 END) as disponibles,
    SUM(CASE WHEN h.estado = 'no disponible' THEN 1 ELSE 0 END) as no_disponibles,
    SUM(CASE WHEN h.estado = 'mantenimiento' THEN 1 ELSE 0 END) as en_mantenimiento
FROM habitacion h GROUP BY h.categoria ORDER BY h.categoria;

-- 2.3 Vista de habitaciones disponibles para rango de fechas
CREATE VIEW v_habitaciones_disponibles_fechas AS
SELECT h.noHabitacion, h.categoria, h.precionoche, h.estado
FROM habitacion h
WHERE h.estado = 'disponible'
  AND h.noHabitacion NOT IN (
    SELECT r.noHabitacion 
    FROM reserva r
    WHERE (r.fechaEntrada <= '2024-08-15 15:00:00-05' AND r.fechaSalida >= '2024-08-10 14:00:00-05')
  )
ORDER BY h.categoria, h.precionoche;

-- 2.4 Vista de ocupación actual del hotel
CREATE VIEW v_ocupacion_actual AS
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

-- 4.2 Vista de consumos por reserva
CREATE VIEW v_consumos_reserva AS
SELECT r.cliente, CONCAT(p.nombres, ' ', p.apellidos) as cliente,
    s.idServicio, s.nombre as servicio, s.costo, c.fecha as fecha_consumo
FROM consume c NATURAL JOIN reserva r NATURAL JOIN servicio s NATURAL JOIN persona p 
ORDER BY c.fecha;

-- 4.3 Vista de total de consumos por reserva
CREATE VIEW v_total_consumos_reserva AS
SELECT CONCAT(p.nombres, ' ', p.apellidos) as cliente,
    r.noHabitacion, COUNT(c.fecha) as total_servicios_consumidos,
    SUM(s.costo) as total_consumos
FROM reserva r
JOIN persona p ON r.cedula = p.cedula
LEFT JOIN consume c ON r.cedula = c.cedula and r.nohabitacion = c.nohabitacion and r.fechaentrada = c.fechaentrada
LEFT JOIN servicio s ON c.idServicio = s.idServicio
GROUP BY r.cedula, p.nombres, p.apellidos, r.noHabitacion HAVING COUNT(c.fecha) > 0 ORDER BY total_servicios_consumidos DESC;

-- 5.1 Vista de todos los empleados
CREATE VIEW v_empleados_completo AS
SELECT p.cedula, CONCAT(p.nombres, ' ', p.apellidos) as empleado,
    c.nombreC as cargo, a.nombreA as area, p.telefono, p.email, p.direccion
FROM empleado e NATURAL JOIN persona p NATURAL JOIN cargo c NATURAL JOIN area a
ORDER BY a.nombreA, c.nombreC, p.apellidos;

-- 5.2 Vista de empleados por área
CREATE VIEW v_empleados_por_area AS
SELECT a.nombreA as area, COUNT(e.cedula) as total_empleados, STRING_AGG(CONCAT(p.nombres, ' ', p.apellidos), ', ') as empleados
FROM area a
LEFT JOIN cargo c ON a.idArea = c.idArea
LEFT JOIN empleado e ON c.idCargo = e.idCargo
LEFT JOIN persona p ON e.cedula = p.cedula
GROUP BY a.idArea, a.nombreA ORDER BY total_empleados DESC;

-- 7.4 Vista de análisis de ocupación por categoría
CREATE VIEW v_analisis_ocupacion_categoria AS
SELECT h.categoria, COUNT(h.noHabitacion) as total_habitaciones,
    COUNT(r.fechaEntrada) as total_reservas,
    ROUND(COUNT(r.fechaentrada) * 100.0 / COUNT(h.noHabitacion), 2) as porcentaje_ocupacion,
    SUM(h.precionoche * EXTRACT(EPOCH FROM (r.fechaSalida - r.fechaEntrada)) / 86400) as ingresos_categoria
FROM habitacion h
LEFT JOIN reserva r ON h.noHabitacion = r.noHabitacion
GROUP BY h.categoria ORDER BY porcentaje_ocupacion DESC;

-- 7.5 Vista de estancia promedio por tipo de cliente
CREATE VIEW v_estancia_promedio_categoria AS
SELECT h.categoria, COUNT(r.fechaentrada) as total_reservas,
    ROUND(AVG(EXTRACT(EPOCH FROM (r.fechaSalida - r.fechaEntrada)) / 86400), 2) as estancia_promedio_dias,
    ROUND(AVG(h.precionoche), 0) as precio_promedio_noche
FROM reserva r
JOIN habitacion h ON r.noHabitacion = h.noHabitacion
GROUP BY h.categoria ORDER BY estancia_promedio_dias DESC;

-- 7.6 Vista de reporte financiero consolidado
CREATE VIEW v_reporte_financiero AS
SELECT 'INGRESOS HABITACIONES' as concepto,
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

