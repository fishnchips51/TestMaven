package com.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.sql.SQLException;

public class Client {
    private int clientId;
    private String username;
    private String email;
    private String ip;
    private Socket socket;
    private OutputStreamWriter outputStreamWriter;
    private BufferedWriter bufferedWriter;
    private Database db;

    public Client(int userId, String username, String email, String ip) {
        this.clientId = userId;
        this.username = username;
        this.email = email;
        this.ip = ip;
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

    public String getIp() {
        return ip;
    }

    public void requestClient(int serverId) {
        System.out.println(username + ": Requesting Connection ");
        try {
            db = new Database();
            

            socket = new Socket(ip, db.getPort(clientId));
            outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
            bufferedWriter = new BufferedWriter(outputStreamWriter);


            bufferedWriter.write("Request Connection");
            bufferedWriter.newLine();
            bufferedWriter.write(Integer.toString(serverId));
            bufferedWriter.newLine();
            bufferedWriter.flush();


            socket.close();
            outputStreamWriter.close();
            bufferedWriter.close();


        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
}
