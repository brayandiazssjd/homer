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
    ArrayList<Service> cached;

    public ArrayList<Service> getFullAll() throws SQLException {
        String query = "select * from servicio";
        Connection c = DBConnection.getInstance();
        Statement st = c.createStatement();
        ResultSet rs = st.executeQuery(query);
        
        ArrayList<Service> l = new ArrayList<>();

        while (rs.next()) {
            Service s = new Service();
            s.setName(rs.getString("nombre"));
            s.setId(rs.getInt("idservicio"));
            s.setDescription(rs.getString("serdescripcion"));
            l.add(s);
        }
        return l;
    }

    public void fullUpdate(Service s) throws SQLException {
        String query = "update servicio set descripcion = ?, id = ?, nombre = ? where id = ?";
        Connection c = DBConnection.getInstance();
        PreparedStatement ps = c.prepareStatement(query);
        ps.setString(s.getDescription());
        ps.setInt(s.getId());
        ps.setString(s.getName());
        ps.executeUpdate();
    }
}
