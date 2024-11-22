package com.example.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Slider;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.StackPane;
import javafx.application.Platform;
import javafx.scene.Node;

// Controller class for managing game settings and preferences
public class SettingsController {
    @FXML
    private ToggleButton parentalControlsToggle;

    @FXML
    private Slider volumeSlider;

    private Rectangle coloredTrack;

    @FXML
    public void initialize() {
        // Add listener to handle state changes of the parental controls toggle
        parentalControlsToggle.selectedProperty().addListener((obs, oldVal, newVal) -> {
            handleParentalControlsToggle(newVal);
        });

        // Add listener for volume changes
        volumeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            handleVolumeChange(newVal.doubleValue());
        });

        // Setup the volume slider after the scene is fully loaded
        Platform.runLater(this::setupVolumeSlider);
    }

    private void setupVolumeSlider() {
        // Create colored track
        coloredTrack = new Rectangle();
        coloredTrack.getStyleClass().add("colored-track");
        coloredTrack.setHeight(8);
        coloredTrack.setY(4);

        // Find the track node
        Node track = volumeSlider.lookup(".track");
        if (track instanceof StackPane) {
            ((StackPane) track).getChildren().add(coloredTrack);
            
            // Initial update
            updateColoredTrack();

            // Add listeners for changes
            volumeSlider.widthProperty().addListener((obs, oldVal, newVal) -> updateColoredTrack());
            volumeSlider.valueProperty().addListener((obs, oldVal, newVal) -> updateColoredTrack());
        }
    }

    private void updateColoredTrack() {
        if (coloredTrack != null) {
            double width = volumeSlider.getWidth() - 16;
            double percentage = (volumeSlider.getValue() - volumeSlider.getMin()) / 
                              (volumeSlider.getMax() - volumeSlider.getMin());
            coloredTrack.setWidth(Math.max(0, width * percentage));
        }
    }

    private void handleParentalControlsToggle(boolean enabled) {
        System.out.println("Parental controls " + (enabled ? "enabled" : "disabled"));
    }

    private void handleVolumeChange(double volume) {
        System.out.println("Volume set to: " + volume);
    }

    @FXML
    private void goBack() {
        SceneController.getInstance().switchToMainMenu();
    }
}
