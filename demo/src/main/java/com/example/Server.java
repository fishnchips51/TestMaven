package com.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private Socket socket;
    private InputStreamReader inputStreamReader;
    private OutputStreamWriter outputStreamWriter;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private ServerSocket serverSocket;
    public Database db;

    public Server() {
        db = new Database();
    }

    public void connectToClient() {
        try {
            serverSocket = new ServerSocket(1234);
            System.out.println("connected to server");

            while (true) {
                socket = serverSocket.accept();

                inputStreamReader = new InputStreamReader(socket.getInputStream());
                outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
    
                bufferedReader = new BufferedReader(inputStreamReader);
                bufferedWriter = new BufferedWriter(outputStreamWriter);


                while (true) {
                    String msg = bufferedReader.readLine();
                    System.out.println(msg);
                    if (msg.equalsIgnoreCase("close")) {
                        break;
                    }
                }
                serverSocket.close();
                inputStreamReader.close();
                outputStreamWriter.close();
                bufferedReader.close();
                bufferedWriter.close(); 
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
