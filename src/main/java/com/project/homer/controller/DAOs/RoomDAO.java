package com.project.homer.controller.DAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.project.homer.model.DBConnection;
import com.project.homer.model.Room;

/**
 * RoomDAO
 */
public class RoomDAO {
    
    public ArrayList<Room> getAll() throws SQLException {
        String query = "SELECT * FROM habitacion";
        Connection c = DBConnection.getInstance();
        Statement st = c.createStatement();
        ResultSet rs = st.executeQuery(query);
        
        ArrayList<Room> rooms = new ArrayList<>();
        
        while (rs.next()) {
            Room room = new Room();
            room.setNumber(rs.getInt("numero"));
            room.setStatus(rs.getString("estado"));
            room.setCategory(rs.getString("categoria"));
            room.setPricePerNight(rs.getDouble("precio_por_noche"));
            rooms.add(room);
        }
        
        rs.close();
        st.close();
        return rooms;
    }
    
    public Room getByNumber(int number) throws SQLException {
        String query = "SELECT * FROM habitacion WHERE numero = ?";
        Connection c = DBConnection.getInstance();
        PreparedStatement ps = c.prepareStatement(query);
        ps.setInt(1, number);
        ResultSet rs = ps.executeQuery();
        
        Room room = null;
        if (rs.next()) {
            room = new Room();
            room.setNumber(rs.getInt("numero"));
            room.setStatus(rs.getString("estado"));
            room.setCategory(rs.getString("categoria"));
            room.setPricePerNight(rs.getDouble("precio_por_noche"));
        }
        
        rs.close();
        ps.close();
        return room;
    }
    
    public ArrayList<Room> getByStatus(String status) throws SQLException {
        String query = "SELECT * FROM habitacion WHERE estado = ?";
        Connection c = DBConnection.getInstance();
        PreparedStatement ps = c.prepareStatement(query);
        ps.setString(1, status);
        ResultSet rs = ps.executeQuery();
        
        ArrayList<Room> rooms = new ArrayList<>();
        
        while (rs.next()) {
            Room room = new Room();
            room.setNumber(rs.getInt("numero"));
            room.setStatus(rs.getString("estado"));
            room.setCategory(rs.getString("categoria"));
            room.setPricePerNight(rs.getDouble("precio_por_noche"));
            rooms.add(room);
        }
        
        rs.close();
        ps.close();
        return rooms;
    }
    
    public void insert(Room room) throws SQLException {
        String query = "INSERT INTO habitacion (numero, estado, categoria, precio_por_noche) VALUES (?, ?, ?, ?)";
        Connection c = DBConnection.getInstance();
        PreparedStatement ps = c.prepareStatement(query);
        ps.setInt(1, room.getNumber());
        ps.setString(2, room.getStatus());
        ps.setString(3, room.getCategory());
        ps.setDouble(4, room.getPricePerNight());
        ps.executeUpdate();
        ps.close();
    }
    
    public void update(Room room) throws SQLException {
        String query = "UPDATE habitacion SET estado = ?, categoria = ?, precio_por_noche = ? WHERE numero = ?";
        Connection c = DBConnection.getInstance();
        PreparedStatement ps = c.prepareStatement(query);
        ps.setString(1, room.getStatus());
        ps.setString(2, room.getCategory());
        ps.setDouble(3, room.getPricePerNight());
        ps.setInt(4, room.getNumber());
        ps.executeUpdate();
        ps.close();
    }
    
    public void delete(int number) throws SQLException {
        String query = "DELETE FROM habitacion WHERE numero = ?";
        Connection c = DBConnection.getInstance();
        PreparedStatement ps = c.prepareStatement(query);
        ps.setInt(1, number);
        ps.executeUpdate();
        ps.close();
    }
}
