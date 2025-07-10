package com.project.homer.controller.DAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Statement;

import com.project.homer.model.DBConnection;
import com.project.homer.model.Service;

/**
 * ServiceDAO
 */
public class ServiceDAO {
    
    public ArrayList<Service> getAll() throws SQLException {
        String query = "SELECT * FROM servicio";
        Connection c = DBConnection.getInstance();
        Statement st = c.createStatement();
        ResultSet rs = st.executeQuery(query);
        
        ArrayList<Service> services = new ArrayList<>();

        while (rs.next()) {
            Service service = new Service();
            service.setId(rs.getInt("idservicio"));
            service.setName(rs.getString("nombre"));
            service.setDescription(rs.getString("serdescripcion"));
            service.setPrice(rs.getFloat("precio"));
            services.add(service);
        }
        
        rs.close();
        st.close();
        return services;
    }
    
    public Service getById(int id) throws SQLException {
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
    
    public void insert(Service service) throws SQLException {
        String query = "INSERT INTO servicio (nombre, serdescripcion, precio) VALUES (?, ?, ?)";
        Connection c = DBConnection.getInstance();
        PreparedStatement ps = c.prepareStatement(query);
        ps.setString(1, service.getName());
        ps.setString(2, service.getDescription());
        ps.setFloat(3, service.getPrice());
        ps.executeUpdate();
        ps.close();
    }

    public void update(Service service) throws SQLException {
        String query = "UPDATE servicio SET nombre = ?, serdescripcion = ?, precio = ? WHERE idservicio = ?";
        Connection c = DBConnection.getInstance();
        PreparedStatement ps = c.prepareStatement(query);
        ps.setString(1, service.getName());
        ps.setString(2, service.getDescription());
        ps.setFloat(3, service.getPrice());
        ps.setInt(4, service.getId());
        ps.executeUpdate();
        ps.close();
    }
    
    public void delete(int id) throws SQLException {
        String query = "DELETE FROM servicio WHERE idservicio = ?";
        Connection c = DBConnection.getInstance();
        PreparedStatement ps = c.prepareStatement(query);
        ps.setInt(1, id);
        ps.executeUpdate();
        ps.close();
    }
}
