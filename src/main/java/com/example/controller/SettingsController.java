package com.example.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;

public class SettingsController {
    @FXML
    private ToggleButton parentalControlsToggle;

    @FXML
    public void initialize() {
        parentalControlsToggle.selectedProperty().addListener((obs, oldVal, newVal) -> {
            handleParentalControlsToggle(newVal);
        });
    }

    private void handleParentalControlsToggle(boolean enabled) {
        // TODO: implement parental controls
        System.out.println("Parental controls " + (enabled ? "enabled" : "disabled"));
    }

    @FXML
    private void goBack() {
        SceneController.getInstance().switchToMainMenu();
    }
}
