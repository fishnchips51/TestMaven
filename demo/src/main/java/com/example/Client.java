package com.example;

public class Client {
    private int clientId;
    private String username;
    private String email;

    public Client(int userId, String username, String email) {
        this.clientId = userId;
        this.username = username;
        this.email = email;
    }

    public Client(String username) {
        this.username = username;
    }

    public int getClientId() {
        return clientId;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}
