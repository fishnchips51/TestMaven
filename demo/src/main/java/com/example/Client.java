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
    private InputStreamReader inputStreamReader;
    private OutputStreamWriter outputStreamWriter;
    private BufferedReader bufferedReader;
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
        System.out.println("Is this working");
        try {
            db = new Database();
            

            socket = new Socket(ip, db.getPort(clientId));


            inputStreamReader = new InputStreamReader(socket.getInputStream());
            outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
            bufferedReader = new BufferedReader(inputStreamReader);
            bufferedWriter = new BufferedWriter(outputStreamWriter);


            bufferedWriter.write("Request Connection");
            bufferedWriter.newLine();

            bufferedWriter.write(Integer.toString(serverId));
            bufferedWriter.newLine();
            bufferedWriter.flush();


            socket.close();
            inputStreamReader.close();
            outputStreamWriter.close();
            bufferedReader.close();
            bufferedWriter.close();


        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
}
