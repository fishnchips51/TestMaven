package com.example.Singletons;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class UserSingleton {
    private static UserSingleton instance = new UserSingleton();
    private int userId;
    private String username;
    private String email;
    private String ip;
    private FXMLLoader loader;

    private UserSingleton() {}

    public static UserSingleton getInstance() {
        return instance;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId; 
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getIp() {
        return ip;
    }

    public void setLoader(FXMLLoader loader) {
        this.loader = loader;
    }

    public FXMLLoader getLoader() {
        return loader;
    }
}
