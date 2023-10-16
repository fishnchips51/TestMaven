package com.example.Controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import com.example.Client;
import com.example.Database;
import com.example.Server;
import com.example.Singletons.UserSingleton;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

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
        initializeClients();

        Runnable r = new Runnable() {
            public void run() {
                Server server = new Server();
                server.startLocalServer();
            }
        };

        new Thread(r).start();
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

    private void addClient(Client clientUser) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/Client.fxml"));
        loader.load();

        ClientPageController clientController = loader.getController();
        clientController.setClient(clientUser);

        Pane client = loader.getRoot();
        Label userId = (Label) client.getChildren().get(1);

        if (db.getUsername(clientUser.getUsername()) != null) {
            userId.setText(clientUser.getUsername());
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

    public Stage getStage() {
        Stage stage = (Stage) connInput.getScene().getWindow();
        return stage;
    }
}
