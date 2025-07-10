package com.project.homer.controller.DAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.project.homer.model.DBConnection;
import com.project.homer.model.Reservation;
import com.project.homer.model.Room;
import com.project.homer.model.User;

/**
 * ReservationDAO
 */
public class ReservationDAO {
    
    private RoomDAO roomDAO = new RoomDAO();
    private UserDAO userDAO = new UserDAO();
    
    public ArrayList<Reservation> getAll() throws SQLException {
        String query = "SELECT * FROM reserva";
        Connection c = DBConnection.getInstance();
        Statement st = c.createStatement();
        ResultSet rs = st.executeQuery(query);
        
        ArrayList<Reservation> reservations = new ArrayList<>();
        
        while (rs.next()) {
            Reservation reservation = new Reservation();
            reservation.setId(rs.getInt("id"));
            reservation.setEntry(rs.getDate("fecha_entrada"));
            reservation.setDeparture(rs.getDate("fecha_salida"));
            
            // Cargar habitación asociada
            Room room = roomDAO.getByNumber(rs.getInt("numero_habitacion"));
            reservation.setRoom(room);
            
            // Cargar usuario asociado
            User user = userDAO.getById(rs.getString("id_usuario"));
            reservation.setUser(user);
            
            reservations.add(reservation);
        }
        
        rs.close();
        st.close();
        return reservations;
    }
    
    public Reservation getById(int id) throws SQLException {
        String query = "SELECT * FROM reserva WHERE id = ?";
        Connection c = DBConnection.getInstance();
        PreparedStatement ps = c.prepareStatement(query);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        
        Reservation reservation = null;
        if (rs.next()) {
            reservation = new Reservation();
            reservation.setId(rs.getInt("id"));
            reservation.setEntry(rs.getDate("fecha_entrada"));
            reservation.setDeparture(rs.getDate("fecha_salida"));
            
            // Cargar habitación asociada
            Room room = roomDAO.getByNumber(rs.getInt("numero_habitacion"));
            reservation.setRoom(room);
            
            // Cargar usuario asociado
            User user = userDAO.getById(rs.getString("id_usuario"));
            reservation.setUser(user);
        }
        
        rs.close();
        ps.close();
        return reservation;
    }
    
    public ArrayList<Reservation> getByUser(String userId) throws SQLException {
        String query = "SELECT * FROM reserva WHERE id_usuario = ?";
        Connection c = DBConnection.getInstance();
        PreparedStatement ps = c.prepareStatement(query);
        ps.setString(1, userId);
        ResultSet rs = ps.executeQuery();
        
        ArrayList<Reservation> reservations = new ArrayList<>();
        
        while (rs.next()) {
            Reservation reservation = new Reservation();
            reservation.setId(rs.getInt("id"));
            reservation.setEntry(rs.getDate("fecha_entrada"));
            reservation.setDeparture(rs.getDate("fecha_salida"));
            
            // Cargar habitación asociada
            Room room = roomDAO.getByNumber(rs.getInt("numero_habitacion"));
            reservation.setRoom(room);
            
            // Cargar usuario asociado
            User user = userDAO.getById(rs.getString("id_usuario"));
            reservation.setUser(user);
            
            reservations.add(reservation);
        }
        
        rs.close();
        ps.close();
        return reservations;
    }
    
    public void insert(Reservation reservation) throws SQLException {
        String query = "INSERT INTO reserva (fecha_entrada, fecha_salida, numero_habitacion, id_usuario) VALUES (?, ?, ?, ?)";
        Connection c = DBConnection.getInstance();
        PreparedStatement ps = c.prepareStatement(query);
        ps.setDate(1, new java.sql.Date(reservation.getEntry().getTime()));
        ps.setDate(2, new java.sql.Date(reservation.getDeparture().getTime()));
        ps.setInt(3, reservation.getRoom().getNumber());
        ps.setString(4, reservation.getUser().getId());
        ps.executeUpdate();
        ps.close();
    }
    
    public void update(Reservation reservation) throws SQLException {
        String query = "UPDATE reserva SET fecha_entrada = ?, fecha_salida = ?, numero_habitacion = ?, id_usuario = ? WHERE id = ?";
        Connection c = DBConnection.getInstance();
        PreparedStatement ps = c.prepareStatement(query);
        ps.setDate(1, new java.sql.Date(reservation.getEntry().getTime()));
        ps.setDate(2, new java.sql.Date(reservation.getDeparture().getTime()));
        ps.setInt(3, reservation.getRoom().getNumber());
        ps.setString(4, reservation.getUser().getId());
        ps.setInt(5, reservation.getId());
        ps.executeUpdate();
        ps.close();
    }
    
    public void delete(int id) throws SQLException {
        String query = "DELETE FROM reserva WHERE id = ?";
        Connection c = DBConnection.getInstance();
        PreparedStatement ps = c.prepareStatement(query);
        ps.setInt(1, id);
        ps.executeUpdate();
        ps.close();
    }
}
