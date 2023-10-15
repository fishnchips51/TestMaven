package com.example.Controllers;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.example.Client;
import com.example.Database;
import com.example.UserSingleton;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;

public class MainPageController {
    
    private Database db;
    private UserSingleton user;

    @FXML
    private TextField connInput;

    @FXML
    private Button connButton;

    @FXML
    private Button dashboardButton;
    
    @FXML
    private Button reportButton;

    @FXML
    private Button systemButton;

    @FXML 
    private Button profileButton;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private FlowPane flowPane;

    public void initialize() {
        db = new Database();
        user = UserSingleton.getInstance();
		scrollPane.setFitToWidth(true);
		scrollPane.setFitToHeight(true);
        initializeClients();
    }

    private void initializeClients() {
        try {
            ArrayList<Client> clients = db.getClients();
            for (int i = 0; i < clients.size(); i++) {
                addClient(clients.get(i));
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    private void addClient(Client user) throws IOException {
        Pane client = FXMLLoader.load(getClass().getResource("../../../fxml/Client.fxml"));
        Label userId = (Label) client.getChildren().get(1);
        if (db.getUsername(user.getUsername()) != null) {
            userId.setText(user.getUsername());
            flowPane.getChildren().add(client);
        } 
    }

    public void submitClient() {
        String username = connInput.getText();
        try {
            if (username == null) {
                return;
            } else {
                addClient(new Client(username));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
