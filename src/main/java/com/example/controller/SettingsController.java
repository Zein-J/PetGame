package com.example.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Slider;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.StackPane;
import javafx.application.Platform;
import javafx.scene.Node;

/**
 * Controller class for managing game settings and preferences.
 * Handles parental controls, volume adjustments, and user interactions with the settings menu.
 */
public class SettingsController {
    @FXML
    private ToggleButton parentalControlsToggle;

    @FXML
    private Slider volumeSlider;

    private Rectangle coloredTrack;

    /**
     * Initializes the settings menu.
     * - Sets up listeners for parental controls and volume adjustments.
     * - Configures a custom colored track for the volume slider.
     */
    @FXML
    public void initialize() {
        // Listener for parental controls toggle
        parentalControlsToggle.selectedProperty().addListener((obs, oldVal, newVal) -> {
            handleParentalControlsToggle(newVal);
        });

        // Listener for volume slider value changes
        volumeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            handleVolumeChange(newVal.doubleValue());
        });

        // Delayed setup for the volume slider after the scene loads
        Platform.runLater(this::setupVolumeSlider);
    }

    /**
     * Configures a custom colored track on the volume slider.
     * The track visually represents the current volume level and updates dynamically.
     */
    private void setupVolumeSlider() {
        // Create a colored rectangle to represent the filled portion of the track
        coloredTrack = new Rectangle();
        coloredTrack.getStyleClass().add("colored-track");
        coloredTrack.setHeight(8);
        coloredTrack.setY(4);

        // Locate the track node within the slider
        Node track = volumeSlider.lookup(".track");
        if (track instanceof StackPane) {
            // Add the colored track as a child of the track node
            ((StackPane) track).getChildren().add(coloredTrack);

            // Perform the initial update of the colored track
            updateColoredTrack();

            // Add listeners to update the track when slider width or value changes
            volumeSlider.widthProperty().addListener((obs, oldVal, newVal) -> updateColoredTrack());
            volumeSlider.valueProperty().addListener((obs, oldVal, newVal) -> updateColoredTrack());
        }
    }

    /**
     * Updates the width of the colored track based on the current volume slider value.
     * The track dynamically reflects the percentage of the slider's value.
     */
    private void updateColoredTrack() {
        if (coloredTrack != null) {
            double width = volumeSlider.getWidth() - 16; // Account for slider padding
            double percentage = (volumeSlider.getValue() - volumeSlider.getMin()) /
                    (volumeSlider.getMax() - volumeSlider.getMin());
            coloredTrack.setWidth(Math.max(0, width * percentage));
        }
    }

    /**
     * Handles changes to the parental controls toggle state.
     *
     * @param enabled {@code true} if parental controls are enabled, {@code false} otherwise.
     */
    private void handleParentalControlsToggle(boolean enabled) {
        System.out.println("Parental controls " + (enabled ? "enabled" : "disabled"));
    }

    /**
     * Handles changes to the volume slider value.
     *
     * @param volume The new volume level as a double between the slider's minimum and maximum.
     */
    private void handleVolumeChange(double volume) {
        System.out.println("Volume set to: " + volume);
    }

    /**
     * Returns the user to the main menu.
     */
    @FXML
    private void goBack() {
        SceneController.getInstance().switchToMainMenu();
    }
}
