package com.project.homer.controller.DAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.project.homer.model.DBConnection;
import com.project.homer.model.User;

/**
 * UserDAO
 */
public class UserDAO {
    
    public ArrayList<User> getAll() throws SQLException {
        String query = "SELECT * FROM usuario";
        Connection c = DBConnection.getInstance();
        Statement st = c.createStatement();
        ResultSet rs = st.executeQuery(query);
        
        ArrayList<User> users = new ArrayList<>();
        
        while (rs.next()) {
            User user = new User();
            user.setId(rs.getString("id"));
            user.setNames(rs.getString("nombres"));
            user.setSurnames(rs.getString("apellidos"));
            user.setTelephone(rs.getLong("telefono"));
            user.setAddress(rs.getString("direccion"));
            user.setEmail(rs.getString("email"));
            users.add(user);
        }
        
        rs.close();
        st.close();
        return users;
    }
    
    public User getById(String id) throws SQLException {
        String query = "SELECT * FROM usuario WHERE id = ?";
        Connection c = DBConnection.getInstance();
        PreparedStatement ps = c.prepareStatement(query);
        ps.setString(1, id);
        ResultSet rs = ps.executeQuery();
        
        User user = null;
        if (rs.next()) {
            user = new User();
            user.setId(rs.getString("id"));
            user.setNames(rs.getString("nombres"));
            user.setSurnames(rs.getString("apellidos"));
            user.setTelephone(rs.getLong("telefono"));
            user.setAddress(rs.getString("direccion"));
            user.setEmail(rs.getString("email"));
        }
        
        rs.close();
        ps.close();
        return user;
    }
    
    public void insert(User user) throws SQLException {
        String query = "INSERT INTO usuario (id, nombres, apellidos, telefono, direccion, email) VALUES (?, ?, ?, ?, ?, ?)";
        Connection c = DBConnection.getInstance();
        PreparedStatement ps = c.prepareStatement(query);
        ps.setString(1, user.getId());
        ps.setString(2, user.getNames());
        ps.setString(3, user.getSurnames());
        ps.setLong(4, user.getTelephone());
        ps.setString(5, user.getAddress());
        ps.setString(6, user.getEmail());
        ps.executeUpdate();
        ps.close();
    }
    
    public void update(User user) throws SQLException {
        String query = "UPDATE usuario SET nombres = ?, apellidos = ?, telefono = ?, direccion = ?, email = ? WHERE id = ?";
        Connection c = DBConnection.getInstance();
        PreparedStatement ps = c.prepareStatement(query);
        ps.setString(1, user.getNames());
        ps.setString(2, user.getSurnames());
        ps.setLong(3, user.getTelephone());
        ps.setString(4, user.getAddress());
        ps.setString(5, user.getEmail());
        ps.setString(6, user.getId());
        ps.executeUpdate();
        ps.close();
    }
    
    public void delete(String id) throws SQLException {
        String query = "DELETE FROM usuario WHERE id = ?";
        Connection c = DBConnection.getInstance();
        PreparedStatement ps = c.prepareStatement(query);
        ps.setString(1, id);
        ps.executeUpdate();
        ps.close();
    }
}
