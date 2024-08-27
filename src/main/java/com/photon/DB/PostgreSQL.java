package com.photon.DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

public class PostgreSQL {
    private static PostgreSQL instance; // Singleton pattern
    private String url;
    private String user;
    private String password;
    private Connection conn;

    public PostgreSQL() {
        // PostgreSQL Database credentials
        this.url = "jdbc:postgresql://10.35.103.48:5432/photon";
        this.user = "student";
        this.password = "student";
        this.conn = null;
        CreateDBConnection();
    }

    public PostgreSQL(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
        this.conn = null;
        CreateDBConnection();
    }

    //*******************************************************************************************
    // This is important to prevent multiple instances of this class,
    // and ultimately, multiple DB connections from being created. 
    //*******************************************************************************************
    public static PostgreSQL getInstance() {
        if (instance == null) {
            instance = new PostgreSQL();
        }
        return instance;
    }

    // Overloaded getInstance method to accept parameters
    public static PostgreSQL getInstance(String url, String user, String password) {
        if (instance == null) {
            instance = new PostgreSQL(url, user, password);
        }
        return instance;
    }


//*******************************************************************************************
//! PRIVATE METHODS !//
//*******************************************************************************************
    private boolean CreateDBConnection() {
        boolean returnVal = false;

        try {
            // Load PostgreSQL JDBC Driver
            Class.forName("org.postgresql.Driver");

            // Establish connection
            this.conn = DriverManager.getConnection(this.url, this.user, this.password);
            System.out.println("Connected to the PostgreSQL server successfully.");

            returnVal = true;
        } catch (Exception e) {
            e.printStackTrace();
            returnVal = false;
            
        }
        return returnVal;
    }
    


//*******************************************************************************************
// GETTERS AND SETTERS
//*******************************************************************************************
    public Connection getConnection() {
        try {
            if (this.conn == null || this.conn.isClosed()) {
                CreateDBConnection();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return this.conn;
    }

    public void closeConnection() {
        try {
            if (this.conn != null && !this.conn.isClosed()) {
                this.conn.close();
                System.out.println("PostgreSQL connection closed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
