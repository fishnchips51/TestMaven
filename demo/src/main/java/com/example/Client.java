package com.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

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

    public void connectClient() {
        try {
            socket = new Socket("192.168.1.101", 1234);

            inputStreamReader = new InputStreamReader(socket.getInputStream());
            outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());

            bufferedReader = new BufferedReader(inputStreamReader);
            bufferedWriter = new BufferedWriter(outputStreamWriter);

            Scanner scanner = new Scanner(System.in);
            while (true) {
                String msg = scanner.nextLine();
                bufferedWriter.write(msg);
                bufferedWriter.newLine();
                bufferedWriter.flush();

                if (msg.equalsIgnoreCase("close")) {
                    break;
                }
            }
            socket.close();
            inputStreamReader.close();
            outputStreamWriter.close();
            bufferedReader.close();
            bufferedWriter.close();
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
