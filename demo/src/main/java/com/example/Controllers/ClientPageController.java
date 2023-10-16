package com.example.Controllers;

import java.sql.SQLException;

import com.example.Client;
import com.example.Database;
import com.example.Server;
import com.example.Singletons.UserSingleton;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;

public class ClientPageController {

    private Client client;
    private Database db;
    private UserSingleton user;

    public void setClient(Client client) {
        this.client = client;
    }

    public Client getClient() {
        return client;
    }

    public void requestConnection(ActionEvent event) {
        user = UserSingleton.getInstance();

        // Code for pop up

        client.requestClient(user.getUserId());

    }




}
