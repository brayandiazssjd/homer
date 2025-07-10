package com.project.homer.controller.DAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.project.homer.model.Consumption;
import com.project.homer.model.DBConnection;
import com.project.homer.model.Reservation;
import com.project.homer.model.Room;
import com.project.homer.model.Service;
import com.project.homer.model.User;

/**
 * ConsumptionDAO
 */
public class ConsumptionDAO {
    
    private ServiceDAO serviceDAO = new ServiceDAO();
    private ReservationDAO reservationDAO = new ReservationDAO();
    private UserDAO userDAO = new UserDAO();
    private RoomDAO roomDAO = new RoomDAO();
    
    public ArrayList<Consumption> getAll() throws SQLException {
        String query = "SELECT * FROM consumo";
        Connection c = DBConnection.getInstance();
        Statement st = c.createStatement();
        ResultSet rs = st.executeQuery(query);
        
        ArrayList<Consumption> consumptions = new ArrayList<>();
        
        while (rs.next()) {
            Consumption consumption = new Consumption();
            consumption.setId(rs.getInt("id"));
            consumption.setDate(rs.getDate("fecha"));
            
            // Cargar servicio asociado
            Service service = getServiceById(rs.getInt("id_servicio"));
            consumption.setService(service);
            
            // Cargar reserva asociada
            Reservation reservation = reservationDAO.getById(rs.getInt("id_reserva"));
            consumption.setReservation(reservation);
            
            // Cargar usuario asociado
            User user = userDAO.getById(rs.getString("id_usuario"));
            consumption.setUser(user);
            
            // Cargar habitación asociada
            Room room = roomDAO.getByNumber(rs.getInt("numero_habitacion"));
            consumption.setRoom(room);
            
            consumptions.add(consumption);
        }
        
        rs.close();
        st.close();
        return consumptions;
    }
    
    public Consumption getById(int id) throws SQLException {
        String query = "SELECT * FROM consumo WHERE id = ?";
        Connection c = DBConnection.getInstance();
        PreparedStatement ps = c.prepareStatement(query);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        
        Consumption consumption = null;
        if (rs.next()) {
            consumption = new Consumption();
            consumption.setId(rs.getInt("id"));
            consumption.setDate(rs.getDate("fecha"));
            
            // Cargar servicio asociado
            Service service = getServiceById(rs.getInt("id_servicio"));
            consumption.setService(service);
            
            // Cargar reserva asociada
            Reservation reservation = reservationDAO.getById(rs.getInt("id_reserva"));
            consumption.setReservation(reservation);
            
            // Cargar usuario asociado
            User user = userDAO.getById(rs.getString("id_usuario"));
            consumption.setUser(user);
            
            // Cargar habitación asociada
            Room room = roomDAO.getByNumber(rs.getInt("numero_habitacion"));
            consumption.setRoom(room);
        }
        
        rs.close();
        ps.close();
        return consumption;
    }
    
    public ArrayList<Consumption> getByReservation(int reservationId) throws SQLException {
        String query = "SELECT * FROM consumo WHERE id_reserva = ?";
        Connection c = DBConnection.getInstance();
        PreparedStatement ps = c.prepareStatement(query);
        ps.setInt(1, reservationId);
        ResultSet rs = ps.executeQuery();
        
        ArrayList<Consumption> consumptions = new ArrayList<>();
        
        while (rs.next()) {
            Consumption consumption = new Consumption();
            consumption.setId(rs.getInt("id"));
            consumption.setDate(rs.getDate("fecha"));
            
            // Cargar servicio asociado
            Service service = getServiceById(rs.getInt("id_servicio"));
            consumption.setService(service);
            
            // Cargar reserva asociada
            Reservation reservation = reservationDAO.getById(rs.getInt("id_reserva"));
            consumption.setReservation(reservation);
            
            // Cargar usuario asociado
            User user = userDAO.getById(rs.getString("id_usuario"));
            consumption.setUser(user);
            
            // Cargar habitación asociada
            Room room = roomDAO.getByNumber(rs.getInt("numero_habitacion"));
            consumption.setRoom(room);
            
            consumptions.add(consumption);
        }
        
        rs.close();
        ps.close();
        return consumptions;
    }
    
    public ArrayList<Consumption> getByUser(String userId) throws SQLException {
        String query = "SELECT * FROM consumo WHERE id_usuario = ?";
        Connection c = DBConnection.getInstance();
        PreparedStatement ps = c.prepareStatement(query);
        ps.setString(1, userId);
        ResultSet rs = ps.executeQuery();
        
        ArrayList<Consumption> consumptions = new ArrayList<>();
        
        while (rs.next()) {
            Consumption consumption = new Consumption();
            consumption.setId(rs.getInt("id"));
            consumption.setDate(rs.getDate("fecha"));
            
            // Cargar servicio asociado
            Service service = getServiceById(rs.getInt("id_servicio"));
            consumption.setService(service);
            
            // Cargar reserva asociada
            Reservation reservation = reservationDAO.getById(rs.getInt("id_reserva"));
            consumption.setReservation(reservation);
            
            // Cargar usuario asociado
            User user = userDAO.getById(rs.getString("id_usuario"));
            consumption.setUser(user);
            
            // Cargar habitación asociada
            Room room = roomDAO.getByNumber(rs.getInt("numero_habitacion"));
            consumption.setRoom(room);
            
            consumptions.add(consumption);
        }
        
        rs.close();
        ps.close();
        return consumptions;
    }
    
    public void insert(Consumption consumption) throws SQLException {
        String query = "INSERT INTO consumo (fecha, id_servicio, id_reserva, id_usuario, numero_habitacion) VALUES (?, ?, ?, ?, ?)";
        Connection c = DBConnection.getInstance();
        PreparedStatement ps = c.prepareStatement(query);
        ps.setDate(1, new java.sql.Date(consumption.getDate().getTime()));
        ps.setInt(2, consumption.getService().getId());
        ps.setInt(3, consumption.getReservation().getId());
        ps.setString(4, consumption.getUser().getId());
        ps.setInt(5, consumption.getRoom().getNumber());
        ps.executeUpdate();
        ps.close();
    }
    
    public void update(Consumption consumption) throws SQLException {
        String query = "UPDATE consumo SET fecha = ?, id_servicio = ?, id_reserva = ?, id_usuario = ?, numero_habitacion = ? WHERE id = ?";
        Connection c = DBConnection.getInstance();
        PreparedStatement ps = c.prepareStatement(query);
        ps.setDate(1, new java.sql.Date(consumption.getDate().getTime()));
        ps.setInt(2, consumption.getService().getId());
        ps.setInt(3, consumption.getReservation().getId());
        ps.setString(4, consumption.getUser().getId());
        ps.setInt(5, consumption.getRoom().getNumber());
        ps.setInt(6, consumption.getId());
        ps.executeUpdate();
        ps.close();
    }
    
    public void delete(int id) throws SQLException {
        String query = "DELETE FROM consumo WHERE id = ?";
        Connection c = DBConnection.getInstance();
        PreparedStatement ps = c.prepareStatement(query);
        ps.setInt(1, id);
        ps.executeUpdate();
        ps.close();
    }
    
    // Método auxiliar para obtener un servicio por ID
    private Service getServiceById(int id) throws SQLException {
        String query = "SELECT * FROM servicio WHERE idservicio = ?";
        Connection c = DBConnection.getInstance();
        PreparedStatement ps = c.prepareStatement(query);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        
        Service service = null;
        if (rs.next()) {
            service = new Service();
            service.setId(rs.getInt("idservicio"));
            service.setName(rs.getString("nombre"));
            service.setDescription(rs.getString("serdescripcion"));
            service.setPrice(rs.getFloat("precio"));
        }
        
        rs.close();
        ps.close();
        return service;
    }
