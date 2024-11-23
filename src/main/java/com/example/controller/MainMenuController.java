package com.example.controller;

import javafx.fxml.FXML;
import com.example.components.CustomButton;

/**
 * Controller class for the main menu interface.
 * Manages scene transitions and user interactions on the main menu screen.
 */
public class MainMenuController {

    @FXML
    private CustomButton loadGameButton;
    @FXML
    private CustomButton settingsButton;
    @FXML
    private CustomButton exitButton;

    /**
     * Handles the "Load Game" button action.
     * Switches to the save menu scene.
     */
    @FXML
    private void startGame() {
        SceneController.getInstance().switchToSaveMenu();
    }

    /**
     * Handles the "Settings" button action.
     * Switches to the settings menu scene.
     */
    @FXML
    private void openSettings() {
        SceneController.getInstance().switchToSettings();
    }

    /**
     * Handles the "Exit" button action.
     * Terminates the application.
     */
    @FXML
    private void exitGame() {
        System.exit(0);
    }
}
