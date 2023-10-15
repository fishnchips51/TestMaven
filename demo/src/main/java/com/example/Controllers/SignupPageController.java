package com.example.Controllers;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

import com.example.Database;
import com.example.UserSingleton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class SignupPageController {

    private Database db;
    private UserSingleton user;

	@FXML
	private TextField username;
	
	@FXML
	private PasswordField password;
	
	@FXML
	private TextField email;
	
	@FXML
	private TextField day;
	
	@FXML
	private ComboBox<String> month;
	
	@FXML
	private TextField year;
	
	@FXML
	private ToggleGroup gender;
	
	@FXML
	private RadioButton male;
	
	@FXML
	private RadioButton female;

	@FXML
	private RadioButton nonBinary;

	@FXML
	private RadioButton other;

	@FXML
	private RadioButton nothing;

	@FXML
	private CheckBox news;
	
	@FXML
	private CheckBox terms;
	
	@FXML
	private Button signup;

    public void initialize() {
        db = new Database();
        user = UserSingleton.getInstance();
    }

    public void submits(ActionEvent event) {
        String inputUsername = username.getText();
        String inputPassword = password.getText();
        String inputEmail = email.getText();
        Date inputBirthDate = Date.valueOf(getBirthdate());
        String inputGender = getGender();

        if (!terms.isSelected()) {
            return;
        }

        try {
            db.insertUser(inputUsername, inputPassword, inputEmail, inputBirthDate, inputGender);
            user.setUserId(db.getUserId(inputUsername, inputPassword));
			user.setUsername(inputUsername);
			user.setEmail(inputEmail);

            Parent root = FXMLLoader.load(getClass().getResource("../../../fxml/Main.fxml"));
            Scene scene = new Scene(root);
            Stage window = (Stage) signup.getScene().getWindow();
            window.setScene(scene);
            window.setMaximized(true);
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

	private String getBirthdate() {
		String inputDay = day.getText();
		String inputMonth = getMonth(month.getValue());
		String inputYear = year.getText();
		return inputYear + "-" + inputMonth + "-" + inputDay;
	}

	private String getMonth(String month) {
		String value = null;
		switch (month) {
		case  "January":
			value = "01";
			break;
		case "Feburary":
			value = "02";
			break;
		case "March":
			value = "03";
			break;
		case "April":
			value = "04";
			break;
		case "May":
			value = "05";
			break;
		case "June":
			value = "06";
			break;
		case "July":
			value = "07";
			break;
		case "August":
			value = "08";
			break;
		case "September":
			value = "09";
			break;
		case "October":
			value = "10";
			break;
		case "November":
			value = "11";
			break;
		case "December":
			value = "12";
			break;
		}
		return value;
	}

	private String getGender() {
		if (male.isSelected()) {
			return "Male";
		} else if (female.isSelected()) {
			return "Female";
		} else if (nonBinary.isSelected()) {
			return "Non-Binary";
		} else if (other.isSelected()) {
			return "Other";
		} else if (nothing.isSelected()) {
			return "Prefer not to say";
		} else {
			return null;
		}
	}    
}
