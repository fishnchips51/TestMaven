package com.example.Controllers;

import java.io.IOException;

import com.example.Database;
import com.example.UserSingleton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginPageController {
    private Database db;
    private UserSingleton user = UserSingleton.getInstance();

	@FXML
	private TextField username;
	
	@FXML
	private PasswordField password;
	
	@FXML
	private Button loginButton;
	
	@FXML
	private Label invalidUsrPwd;
	
	@FXML
	private Hyperlink signupLink;

    public void initialize() {
        db = new Database();
    }

    public void submit(ActionEvent event) {
        String inputUsername = username.getText();
        String inputPassword = password.getText();

        if (inputUsername.equals(db.getUsername(inputUsername)) && inputPassword.equals(db.getPassword(inputPassword))) {
            try {
                user.setUserId(db.getUserId(inputUsername, inputPassword));
                user.setUsername(inputUsername);
                user.setEmail(inputPassword);

                Parent root = FXMLLoader.load(getClass().getResource("../../../fxml/Main.fxml"));
                Scene scene = new Scene(root);
                Stage window = (Stage) loginButton.getScene().getWindow();

                window.setScene(scene);
                window.setMaximized(true);

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            invalidUsrPwd.setVisible(true);
        }
    }

    public void signup(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../../../fxml/Signup.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage) loginButton.getScene().getWindow();

        window.setScene(scene);
    } 
}