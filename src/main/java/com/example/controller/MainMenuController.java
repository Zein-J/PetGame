package com.example.controller;

import javafx.fxml.FXML;

public class MainMenuController {

    @FXML
    private void startNewGame() {
        SceneController.getInstance().switchToGame();
    }

    @FXML
    private void loadGame() {
        SceneController.getInstance().switchToSaveMenu();
    }

    @FXML
    private void openSettings() {

    }

    @FXML
    private void exitGame() {
        System.exit(0);
    }

}
