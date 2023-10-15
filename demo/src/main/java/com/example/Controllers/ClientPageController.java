package com.example.Controllers;

import com.example.Client;
import com.example.Server;

import javafx.event.ActionEvent;

public class ClientPageController {

    public void connectClient(ActionEvent event) {
        Runnable r  = new Runnable() {
            public void run() {
                Client client = new Client("john");
                client.connectClient();
            }
        };
        new Thread(r).start();
    }
    
    public void connectServer(ActionEvent event) {
        Runnable r = new Runnable() {
            public void run() {
                Server server = new Server();
                server.connectToClient();
            }
        };

        new Thread(r).start();
    }
}
