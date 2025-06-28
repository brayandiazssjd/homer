-- [ ] Consultar Servicios más/menos usados

select
    servicio.nombre,
    count(consume.idservicio) as num_uso
from servicio
natural join consume
group by servicio.nombre
-- order by asc;
order by desc;

-- [ ] Consultar Habitaciones más reservadas

select
    habitacion.nohabitacion,
    count(reserva.nohabitacion)
from habitacion
natural join reserva
group by habitacion.nohabitacion
-- order by asc;
order by desc;

-- [ ] Consultar Usuarios que más/menos gastan

select
    persona.nombres,
    persona.apellidos,
    sum(servicio.costo) as gasto
from persona
natural join reserva
natural join consume
natural join servicio
group by
    persona.nombres,
    persona.apellidos
-- order by asc;
order by desc;

-- [ ] Consultar Usuarios más fieles/infieles

select
    persona.nombres,
    persona.apellidos,
    count(reserva.cedula) as num_reservas
from persona
natural join reserva
group by
    persona.nombres,
    persona.apellidos
-- order by asc;
order by desc;

-- [ ] Consultar Habitaciones más reservadas

select
    habitacion.nohabitacion,
    count(reserva.nohabitacion)
from habitacion
natural join reserva
group by habitacion.nohabitacion
-- order by asc;
order by desc;

-- [ ] Consultar precio del consumo acumulado en un rango

select sum(servicio.costo) as total
from servicio
natural join consume
natural join reserva
where
    reserva.fechaentrada >= <inicio> and
    reserva.fechasalida <= <fin>;

-- [ ] Consultar precio del consumo promedio en un rango

select avg(total_reserva)
from (
    select
        reserva.idreserva,
        sum(servicio.costo) as total_reserva
    from reserva
    natural join consume
    natural join servicio
    where
        reserva.fechaentrada >= <inicio> and
        reserva.fechasalida <= <fin>;
    group by reserva.idreserva
)
