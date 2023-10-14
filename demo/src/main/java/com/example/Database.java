package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private String url = "jdbc:postgresql://localhost:5432/rmdesktop";
    private String username = "postgres";
    private String password = "hackme123";
    private Connection conn;
    private Statement stmt;
    public Database() {
        try {
            conn = DriverManager.getConnection(url, username, password);
            stmt = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getUsername(String username) {
        String correctUsername = null;

        try {
            ResultSet result = stmt.executeQuery("SELECT name FROM users WHERE name = '" + username + "'");
            if (result.next()) {
                correctUsername = result.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return correctUsername;
    }

    public String getPassword(String password) {
        String correctPassword = null;

        try {
            ResultSet result = stmt.executeQuery("SELECT password FROM users WHERE password = '" + password + "'");
            if (result.next()) {
                correctPassword = result.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return correctPassword;
    }


}
