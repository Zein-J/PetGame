package com.example.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;
import java.io.IOException;

// Singleton controller class responsible for managing scene transitions in the application
public class SceneController {
    // Single instance of the controller (Singleton pattern)
    private static SceneController instance;
    // Reference to the current active scene
    private Scene currentScene;

    // Private constructor to prevent direct instantiation (Singleton pattern)
    private SceneController() {
    }

    // Static method to get the singleton instance
    public static SceneController getInstance() {
        if (instance == null) {
            instance = new SceneController();
        }
        return instance;
    }

    // Navigation methods to switch between different scenes
    public void switchToGame() {
        loadFXML("game.fxml");
    }

    public void switchToSaveMenu() {
        loadFXML("save_menu.fxml");
    }

    public void switchToMainMenu() {
        loadFXML("main_menu.fxml");
    }

    public void switchToSettings() {
        loadFXML("settings.fxml");
    }

    // Helper method to get the current active scene
    // Creates a reference if none exists by finding the active window
    private Scene getCurrentScene() {
        if (currentScene == null) {
            // Find the first showing window in the application
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

    // Loads and switches to a new FXML scene
    private void loadFXML(String fxml) {
        try {
            // Load the FXML file from resources
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/" + fxml));
            Scene scene = getCurrentScene();
            if (scene != null) {
                // Update the scene's root with the new FXML content
                scene.setRoot(root);
                currentScene = scene;
            } else {
                System.err.println("No active scene found");
            }
        } catch (IOException e) {
            // Error handling for FXML loading failures
            System.err.println("Failed to load FXML: " + fxml);
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
