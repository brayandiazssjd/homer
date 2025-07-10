package com.project.homer.controller.DAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.project.homer.model.DBConnection;
import com.project.homer.model.Position;

/**
 * PositionDAO
 */
public class CargoDAO {
    
    private AreaDAO areaDAO = new AreaDAO();
    
    public ArrayList<Cargo> getAll() throws SQLException {
        String query = "SELECT * FROM posicion";
        Connection c = DBConnection.getInstance(DBConnection.ROLE);
        Statement st = c.createStatement();
        ResultSet rs = st.executeQuery(query);
        
        ArrayList<Position> positions = new ArrayList<>();
        
        while (rs.next()) {
            Cargo position = new Position();
            position.setId(rs.getInt("id"));
            position.setName(rs.getString("nombre"));
            position.setDescription(rs.getString("descripcion"));
            
            // Cargar área asociada
            if (rs.getInt("area_id") != 0) {
                position.setArea(areaDAO.getById(rs.getInt("area_id")));
            }
            
            positions.add(position);
        }
        
        rs.close();
        st.close();
        return positions;
    }
    
    public Position getById(int id) throws SQLException {
        String query = "SELECT * FROM posicion WHERE id = ?";
        Connection c = DBConnection.getInstance(DBConnection.ROLE);
        PreparedStatement ps = c.prepareStatement(query);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        
        Cargo position = null;
        if (rs.next()) {
            position = new Position();
            position.setId(rs.getInt("id"));
            position.setName(rs.getString("nombre"));
            position.setDescription(rs.getString("descripcion"));
            
            // Cargar área asociada
            if (rs.getInt("area_id") != 0) {
                position.setArea(areaDAO.getById(rs.getInt("area_id")));
            }
        }
        
        rs.close();
        ps.close();
        return position;
    }
    
    public ArrayList<Position> getByArea(int areaId) throws SQLException {
        String query = "SELECT * FROM cargo WHERE area_id = ?";
        Connection c = DBConnection.getInstance(DBConnection.ROLE);
        PreparedStatement ps = c.prepareStatement(query);
        ps.setInt(1, areaId);
        ResultSet rs = ps.executeQuery();
        
        ArrayList<Position> positions = new ArrayList<>();
        
        while (rs.next()) {
            Position position = new Position();
            position.setId(rs.getInt("id"));
            position.setName(rs.getString("nombre"));
            position.setDescription(rs.getString("descripcion"));
            
            // Cargar área asociada
            position.setArea(areaDAO.getById(rs.getInt("area_id")));
            
            positions.add(position);
        }
        
        rs.close();
        ps.close();
        return positions;
    }
    
    public void insert(Cargo position) throws SQLException {
        String query = "INSERT INTO cargo (nombre, descripcion, area_id) VALUES (?, ?, ?)";
        Connection c = DBConnection.getInstance(DBConnection.ROLE);
        PreparedStatement ps = c.prepareStatement(query);
        ps.setString(1, position.getName());
        ps.setString(2, position.getDescription());
        ps.setInt(3, position.getArea() != null ? position.getArea().getId() : 0);
        ps.executeUpdate();
        ps.close();
    }
    
    public void update(Cargo position) throws SQLException {
        String query = "UPDATE cargo SET nombre = ?, descripcion = ?, area_id = ? WHERE id = ?";
        Connection c = DBConnection.getInstance(DBConnection.ROLE);
        PreparedStatement ps = c.prepareStatement(query);
        ps.setString(1, position.getName());
        ps.setString(2, position.getDescription());
        ps.setInt(3, position.getArea() != null ? position.getArea().getId() : 0);
        ps.setInt(4, position.getId());
        ps.executeUpdate();
        ps.close();
    }
    
    public void delete(int id) throws SQLException {
        String query = "DELETE FROM cargo WHERE id = ?";
        Connection c = DBConnection.getInstance(DBConnection.ROLE);
        PreparedStatement ps = c.prepareStatement(query);
        ps.setInt(1, id);
        ps.executeUpdate();
        ps.close();
    }
}
