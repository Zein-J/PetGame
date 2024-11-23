package com.example.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label userError;

    @FXML
    private Label passwordError;

    @FXML
    private void handleLogin() {
        // Mock credentials for demo purposes
        String validUsername = "CS2212";
        String validPassword = "Rubberducky!";

        // Clear previous error messages
        userError.setVisible(false);
        passwordError.setVisible(false);

        // Validate user input
        boolean valid = true;
        if (!usernameField.getText().equals(validUsername)) {
            userError.setVisible(true);
            valid = false;
        }

        if (!passwordField.getText().equals(validPassword)) {
            passwordError.setVisible(true);
            valid = false;
        }

        if (valid) {
            System.out.println("Login successful!");
            // Proceed to the next screen or action
            SceneController.getInstance().switchToParentMenu();
        } else {
            System.out.println("Login failed. Please check your credentials.");
        }
    }

    @FXML
    private void goBack() {
        System.out.println("Returning to the previous screen...");
        // Logic to switch to the previous screen
    }
}