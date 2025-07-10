package com.project.homer.controller.DAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.project.homer.model.DBConnection;
import com.project.homer.model.Employee;

/**
 * EmployeeDAO
 */
public class EmployeeDAO {
    
    private Cargo positionDAO = new CargoDAO();
    private ReservationDAO reservationDAO = new ReservationDAO();
    
    public ArrayList<Employee> getAll() throws SQLException {
        String query = "SELECT * FROM empleado";
        Connection c = DBConnection.getInstance(DBConnection.ROLE);
        Statement st = c.createStatement();
        ResultSet rs = st.executeQuery(query);
        
        ArrayList<Employee> employees = new ArrayList<>();
        
        while (rs.next()) {
            Employee employee = new Employee();
            employee.setId(rs.getString("id"));
            employee.setNames(rs.getString("nombres"));
            employee.setSurnames(rs.getString("apellidos"));
            employee.setTelephone(rs.getLong("telefono"));
            employee.setAddress(rs.getString("direccion"));
            employee.setEmail(rs.getString("email"));
            
            // Cargar posición
            if (rs.getInt("posicion_id") != 0) {
                employee.setPosition(positionDAO.getById(rs.getInt("posicion_id")));
            }
            
            // Cargar reservaciones asignadas
            employee.setReservations(getReservationsByEmployee(rs.getString("id")));
            
            employees.add(employee);
        }
        
        rs.close();
        st.close();
        return employees;
    }
    
    public Employee getById(String id) throws SQLException {
        String query = "SELECT * FROM empleado WHERE id = ?";
        Connection c = DBConnection.getInstance(DBConnection.ROLE);
        PreparedStatement ps = c.prepareStatement(query);
        ps.setString(1, id);
        ResultSet rs = ps.executeQuery();
        
        Employee employee = null;
        if (rs.next()) {
            employee = new Employee();
            employee.setId(rs.getString("id"));
            employee.setNames(rs.getString("nombres"));
            employee.setSurnames(rs.getString("apellidos"));
            employee.setTelephone(rs.getLong("telefono"));
            employee.setAddress(rs.getString("direccion"));
            employee.setEmail(rs.getString("email"));
            
            // Cargar posición
            if (rs.getInt("posicion_id") != 0) {
                employee.setPosition(positionDAO.getById(rs.getInt("posicion_id")));
            }
            
            // Cargar reservaciones asignadas
            employee.setReservations(getReservationsByEmployee(rs.getString("id")));
        }
        
        rs.close();
        ps.close();
        return employee;
    }
    
    public ArrayList<Employee> getByPosition(int positionId) throws SQLException {
        String query = "SELECT * FROM empleado WHERE posicion_id = ?";
        Connection c = DBConnection.getInstance(DBConnection.ROLE);
        PreparedStatement ps = c.prepareStatement(query);
        ps.setInt(1, positionId);
        ResultSet rs = ps.executeQuery();
        
        ArrayList<Employee> employees = new ArrayList<>();
        
        while (rs.next()) {
            Employee employee = new Employee();
            employee.setId(rs.getString("id"));
            employee.setNames(rs.getString("nombres"));
            employee.setSurnames(rs.getString("apellidos"));
            employee.setTelephone(rs.getLong("telefono"));
            employee.setAddress(rs.getString("direccion"));
            employee.setEmail(rs.getString("email"));
            
            // Cargar posición
            employee.setPosition(positionDAO.getById(rs.getInt("posicion_id")));
            
            // Cargar reservaciones asignadas
            employee.setReservations(getReservationsByEmployee(rs.getString("id")));
            
            employees.add(employee);
        }
        
        rs.close();
        ps.close();
        return employees;
    }
    
    public void insert(Employee employee) throws SQLException {
        String query = "INSERT INTO empleado (id, nombres, apellidos, telefono, direccion, email, posicion_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        Connection c = DBConnection.getInstance(DBConnection.ROLE);
        PreparedStatement ps = c.prepareStatement(query);
        ps.setString(1, employee.getId());
        ps.setString(2, employee.getNames());
        ps.setString(3, employee.getSurnames());
        ps.setLong(4, employee.getTelephone());
        ps.setString(5, employee.getAddress());
        ps.setString(6, employee.getEmail());
        ps.setInt(7, employee.getPosition() != null ? employee.getPosition().getId() : 0);
        ps.executeUpdate();
        ps.close();
    }
    
    public void update(Employee employee) throws SQLException {
        String query = "UPDATE empleado SET nombres = ?, apellidos = ?, telefono = ?, direccion = ?, email = ?, posicion_id = ? WHERE id = ?";
        Connection c = DBConnection.getInstance(DBConnection.ROLE);
        PreparedStatement ps = c.prepareStatement(query);
        ps.setString(1, employee.getNames());
        ps.setString(2, employee.getSurnames());
        ps.setLong(3, employee.getTelephone());
        ps.setString(4, employee.getAddress());
        ps.setString(5, employee.getEmail());
        ps.setInt(6, employee.getPosition() != null ? employee.getPosition().getId() : 0);
        ps.setString(7, employee.getId());
        ps.executeUpdate();
        ps.close();
    }
    
    public void delete(String id) throws SQLException {
        String query = "DELETE FROM empleado WHERE id = ?";
        Connection c = DBConnection.getInstance(DBConnection.ROLE);
        PreparedStatement ps = c.prepareStatement(query);
        ps.setString(1, id);
        ps.executeUpdate();
        ps.close();
    }
}
