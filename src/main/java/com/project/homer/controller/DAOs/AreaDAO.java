package com.project.homer.controller.DAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.project.homer.model.Area;
import com.project.homer.model.DBConnection;

/**
 * AreaDAO
 */
public class AreaDAO {
    
    public ArrayList<Area> getAll() throws SQLException {
        String query = "SELECT * FROM area";
        Connection c = DBConnection.getInstance();
        Statement st = c.createStatement();
        ResultSet rs = st.executeQuery(query);
        
        ArrayList<Area> areas = new ArrayList<>();
        
        while (rs.next()) {
            Area area = new Area();
            area.setId(rs.getInt("id"));
            area.setName(rs.getString("nombre"));
            area.setDescription(rs.getString("descripcion"));
            areas.add(area);
        }
        
        rs.close();
        st.close();
        return areas;
    }
    
    public Area getById(int id) throws SQLException {
        String query = "SELECT * FROM area WHERE id = ?";
        Connection c = DBConnection.getInstance();
        PreparedStatement ps = c.prepareStatement(query);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        
        Area area = null;
        if (rs.next()) {
            area = new Area();
            area.setId(rs.getInt("id"));
            area.setName(rs.getString("nombre"));
            area.setDescription(rs.getString("descripcion"));
        }
        
        rs.close();
        ps.close();
        return area;
    }
    
    public void insert(Area area) throws SQLException {
        String query = "INSERT INTO area (nombre, descripcion) VALUES (?, ?)";
        Connection c = DBConnection.getInstance();
        PreparedStatement ps = c.prepareStatement(query);
        ps.setString(1, area.getName());
        ps.setString(2, area.getDescription());
        ps.executeUpdate();
        ps.close();
    }
    
    public void update(Area area) throws SQLException {
        String query = "UPDATE area SET nombre = ?, descripcion = ? WHERE id = ?";
        Connection c = DBConnection.getInstance();
        PreparedStatement ps = c.prepareStatement(query);
        ps.setString(1, area.getName());
        ps.setString(2, area.getDescription());
        ps.setInt(3, area.getId());
        ps.executeUpdate();
        ps.close();
    }
    
    public void delete(int id) throws SQLException {
        String query = "DELETE FROM area WHERE id = ?";
        Connection c = DBConnection.getInstance();
        PreparedStatement ps = c.prepareStatement(query);
        ps.setInt(1, id);
        ps.executeUpdate();
        ps.close();
    }
}
