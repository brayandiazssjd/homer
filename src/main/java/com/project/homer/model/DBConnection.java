package com.project.homer.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DBConnection {
    private static String url = System.getenv("DATABASE_URL");
    private static String user = System.getenv("DATABASE_USER");
    private static String password = System.getenv("DATABASE_PASSWORD");
    private static Connection instance = null;

    private DBConnection() {
        try {
            if (instance == null) {
                instance = DriverManager.getConnection(url, user, password);
            }
        } catch (SQLException sqle) {
            System.out.println(sqle.toString());
        }
    }

    public static Connection getInstance() {
        DBConnection(); 
        return instance;
    }

    public void disconnect() {
        instance = null;
    }
}
