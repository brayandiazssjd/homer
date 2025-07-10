-- 1. Crear el usuario recepcionista 
    CREATE USER recepcionista_1 WITH
      PASSWORD 'Recepcion.2024' 
      NOSUPERUSER
      NOCREATEDB
      NOCREATEROLE
      INHERIT
      NOREPLICATION;
-- 2. Asignar el rol recepcionista al usuario
GRANT recepcionista_1 TO recepcionista;

-- 1. Crear el usuario administrador 
CREATE USER admin_hotel_user WITH
      PASSWORD 'AdminSecure@2024'  
      NOSUPERUSER                 
      CREATEDB                    
      CREATEROLE              
      INHERIT                     
      REPLICATION;

-- 2. Asignar el rol admin_hotel al usuario
GRANT admin_hotel TO admin_hotel_user;       

-- 1. Crear el usuario masajista (si no existe)
CREATE USER masajista WITH
      PASSWORD 'M4s4j3$2024'  -- Cambiar por una contraseña segura
      NOSUPERUSER
      NOCREATEDB
      NOCREATEROLE
      INHERIT
      NOREPLICATION;

-- 2. Asignar el rol servicios al usuario masajista
GRANT servicios TO masajista;
