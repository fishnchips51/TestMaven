package com.example;

import java.io.IOException;

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
		scrollPane.setFitToWidth(true);
		scrollPane.setFitToHeight(true);
    }

    public void addClient() throws IOException {
        Pane client = FXMLLoader.load(getClass().getResource("Client.fxml"));
        Label userId = (Label) client.getChildren().get(1);
        userId.setText("1");

        flowPane.getChildren().add(client);
    }

 

}
