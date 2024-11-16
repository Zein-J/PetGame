package com.example.controller;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;

public class SceneController {
    private static SceneController instance;
    private Scene currentScene;
    
    private SceneController() {}
    
    public static SceneController getInstance() {
        if (instance == null) {
            instance = new SceneController();
        }
        return instance;
    }

    public void switchToGame() {
        loadFXML("game.fxml");
    }

    public void switchToSaveMenu() {
        loadFXML("save_menu.fxml");
    }

    public void switchToMainMenu() {
        loadFXML("main_menu.fxml");
    }

    private Scene getCurrentScene() {
        if (currentScene == null) {
            Window window = Stage.getWindows().stream()
                .filter(Window::isShowing)
                .findFirst()
                .orElse(null);
            
            if (window != null) {
                currentScene = ((Stage) window).getScene();
            }
        }
        return currentScene;
    }

    private void loadFXML(String fxml) {
        try {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/" + fxml));
            Scene scene = getCurrentScene();
            if (scene != null) {
                scene.setRoot(root);
                currentScene = scene;
            } else {
                System.err.println("No active scene found");
            }
        } catch (IOException e) {
            System.err.println("Failed to load FXML: " + fxml);
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
