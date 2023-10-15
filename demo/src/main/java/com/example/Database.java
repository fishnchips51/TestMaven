package com.example;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class Database {
    private String url = "jdbc:postgresql://localhost:5432/mavendesktop";
    private String username = "postgres";
    private String password = "hackme123";
    private Connection conn;
    private Statement stmt;
    private PreparedStatement prstmt;
    private UserSingleton user;

    public Database() {
        try {
            conn = DriverManager.getConnection(url, username, password);
            stmt = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        user = UserSingleton.getInstance();
    }

    public String getUsername(String username) {
        String correctUsername = null;

        try {
            ResultSet result = stmt.executeQuery(
                "SELECT username " + 
                "FROM users " +
                "WHERE username = '" + username + "'");

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
            ResultSet result = stmt.executeQuery(
                "SELECT password " + 
                "FROM users " +
                "WHERE password = '" + password + "'");

            if (result.next()) {
                correctPassword = result.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return correctPassword;
    }

    public int getUserId(String username, String password) {
        int userId = -1;
        try {
            ResultSet result = stmt.executeQuery(
                "SELECT userId " +
                "FROM users " + 
                "WHERE username = '" + username + "' AND password = '" + password + "'");
            if (result.next()) {
                userId = result.getInt(1);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userId;
    } 

    public void insertUser(String username, String password, String email, Date birthDate, String gender) throws SQLException {
        prstmt = conn.prepareStatement(
            "INSERT INTO users(username, password, email, dateofbirth, gender) " + 
            "VALUES(?,?,?,?,?)");

        prstmt.setString(1, username);
        prstmt.setString(2, password);
        prstmt.setString(3, email);
        prstmt.setDate(4, birthDate);
        prstmt.setString(5, gender);
        prstmt.executeUpdate();
    }

    public ArrayList<Client> getClients() throws SQLException {
        ArrayList<Client> clients = new ArrayList<>();
        ResultSet rs = stmt.executeQuery(
        "SELECT a.username AS server, b.username AS clients " + 
        "FROM clients c " + 
        "LEFT JOIN users a ON a.userId = c.server " +
        "LEFT JOIN users b ON b.userId = c.client " +
        "WHERE a.userId = " + user.getUserId());

        while (rs.next()) {
            clients.add(new Client(rs.getString(2)));
        }

        return clients;
    }
}
