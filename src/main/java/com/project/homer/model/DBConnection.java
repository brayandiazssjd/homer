package com.project.homer.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import io.github.cdimascio.dotenv.Dotenv;

public final class DBConnection {
    private static String url;
    private static String username;
    private static String password;
    private static Connection instance = null;
    public static final String ROLE = "POSTGRES";
    

    private DBConnection() {
        
    }

    public static Connection getInstance(String userRole) throws SQLException {
        if (instance == null) {
            Dotenv dotenv = Dotenv.load();
            switch (userRole) {
                case ROLE:
                    username =  dotenv.get("DB_" + userRole + "_USERNAME");
                    password =  dotenv.get("DB_" + userRole + "_PASSWORD");
                    url = dotenv.get("DB_URL");
                    break;
                default:
                    break;
            }
            System.out.println(username+"PS"+password+"u"+url);
            instance = DriverManager.getConnection(url, username, password);
        }
        return instance;
    }

    public void disconnect() {
        instance = null;
    }
}
