-- Crear el rol de recepcionista
CREATE ROLE recepcionista WITH
  LOGIN
  NOSUPERUSER
  NOCREATEDB
  NOCREATEROLE
  INHERIT
  NOREPLICATION
  CONNECTION LIMIT -1;
  
  -- Conceder acceso a las vistas de gestión de clientes y reservas (grupo 1)
GRANT SELECT ON 
  v_clientes_registrados,
  v_reservas_activas,
  v_historial_reservas_cliente
TO recepcionista;

-- Conceder acceso a las vistas de disponibilidad (grupo 2)
GRANT SELECT ON
  v_disponibilidad_actual,
  v_disponibilidad_categoria,
  v_habitaciones_disponibles_fechas,
  v_ocupacion_actual
TO recepcionista;

-- Permisos completos sobre la tabla reserva
GRANT SELECT, INSERT, UPDATE, DELETE ON reserva TO recepcionista;

-- 1. Crear el rol administrador
    CREATE ROLE admin_hotel WITH
      LOGIN
      NOSUPERUSER
      CREATEDB
      CREATEROLE
      INHERIT
      REPLICATION
      CONNECTION LIMIT -1;

-- 2. Conceder permisos sobre todas las tablas existentes
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO admin_hotel;

-- 3. Conceder permisos sobre tablas futuras (opcional para entornos dinámicos)
ALTER DEFAULT PRIVILEGES IN SCHEMA public
  GRANT ALL ON TABLES TO admin_hotel;

ALTER DEFAULT PRIVILEGES IN SCHEMA public
  GRANT ALL ON SEQUENCES TO admin_hotel;

-- 4. Conceder acceso específico a las vistas
GRANT SELECT ON ALL TABLES IN SCHEMA public TO admin_hotel;

-- 5. Conceder permisos adicionales
GRANT CREATE ON SCHEMA public TO admin_hotel;

-- Crear o modificar el rol servicios 
    CREATE ROLE servicios WITH
      LOGIN
      NOSUPERUSER
      NOCREATEDB
      NOCREATEROLE
      INHERIT
      NOREPLICATION
      CONNECTION LIMIT -1;
  
  -- Permisos en tabla consumo
  GRANT SELECT, INSERT, UPDATE ON TABLE consume TO servicios;
  
  -- Permiso SELECT en servicios
  GRANT SELECT ON TABLE servicio TO servicios;
  
  -- Permiso SELECT en reserva (solo si es necesario para operaciones con consumo)
  GRANT SELECT ON TABLE reserva TO servicios;
