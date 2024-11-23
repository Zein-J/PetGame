package com.example.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;
import java.io.IOException;

/**
 * Singleton controller class responsible for managing scene transitions in the application.
 * Provides methods to navigate between different scenes and ensures a single shared instance
 * to manage scene switching throughout the application.
 */
public class SceneController {
    // Single instance of the controller (Singleton pattern)
    private static SceneController instance;
    // Reference to the current active scene
    private Scene currentScene;

    /**
     * Private constructor to prevent direct instantiation.
     * Implements the Singleton pattern to ensure only one instance of SceneController exists.
     */
    private SceneController() {
    }

    /**
     * Returns the single instance of the SceneController.
     * If the instance does not exist, it is created.
     *
     * @return The singleton instance of SceneController.
     */
    public static SceneController getInstance() {
        if (instance == null) {
            instance = new SceneController();
        }
        return instance;
    }

    /**
     * Switches to the game scene by loading the corresponding FXML file.
     * Uses "game.fxml" as the scene definition.
     */
    public void switchToGame() {
        loadFXML("game.fxml");
    }

    /**
     * Switches to the save menu scene by loading the corresponding FXML file.
     * Uses "save_menu.fxml" as the scene definition.
     */
    public void switchToSaveMenu() {
        loadFXML("save_menu.fxml");
    }

    /**
     * Switches to the main menu scene by loading the corresponding FXML file.
     * Uses "main_menu.fxml" as the scene definition.
     */
    public void switchToMainMenu() {
        loadFXML("main_menu.fxml");
    }

    /**
     * Switches to the settings scene by loading the corresponding FXML file.
     * Uses "settings.fxml" as the scene definition.
     */
    public void switchToSettings() {
        loadFXML("settings.fxml");
    }

    public void switchToInventory() {
        loadFXML("inventory_menu.fxml");
    }

    /**
     * Retrieves the current active scene.
     * If no scene is currently active, attempts to find and set the scene from the first showing window.
     *
     * @return The current active {@link Scene}, or {@code null} if no active window exists.
     */
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

    /**
     * Loads a new scene from the specified FXML file and switches to it.
     * Also updates the current scene's root and applies new stylesheets.
     *
     * @param fxml The name of the FXML file to load (relative to the "fxml/" directory).
     */
    private void loadFXML(String fxml) {
        try {
            // Load the FXML file from the "fxml/" directory
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/" + fxml));

            // Retrieve the current scene and update its root and styles
            Scene scene = getCurrentScene();
            if (scene != null) {
                scene.setRoot(root);
                scene.getStylesheets().clear();
                scene.getStylesheets().addAll(
                        getClass().getClassLoader().getResource("styles/main.css").toExternalForm(),
                        getClass().getClassLoader().getResource("styles/menu.css").toExternalForm()
                );
                currentScene = scene;
            }
        } catch (IOException e) {
            // Handle errors in loading the FXML file
            System.err.println("Failed to load FXML: " + fxml);
            e.printStackTrace();
        }
    }


}
