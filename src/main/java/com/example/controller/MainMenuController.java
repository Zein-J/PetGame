package com.example.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.animation.ScaleTransition;
import javafx.util.Duration;
import javafx.scene.effect.ColorAdjust;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.Interpolator;
import javafx.animation.SequentialTransition;

// Controller class for the main menu interface
public class MainMenuController {
    // FXML injected button elements from the UI
    @FXML
    private Button newGameButton;
    @FXML
    private Button loadGameButton;
    @FXML
    private Button settingsButton;
    @FXML
    private Button exitButton;

    // Initializes the controller and sets up animations for all buttons
    @FXML
    public void initialize() {
        setupHoverAnimation(newGameButton);
        setupHoverAnimation(loadGameButton);
        setupHoverAnimation(settingsButton);
        setupHoverAnimation(exitButton);
    }

    // Sets up interactive animations for button hover, click, and release effects
    private void setupHoverAnimation(Button button) {
        // Create a sequence of animations for hover effect
        SequentialTransition hoverAnimation = new SequentialTransition(button);
        
        // First hover animation: Scale up to 110%
        ScaleTransition scaleUpBig = new ScaleTransition(Duration.millis(150));
        scaleUpBig.setToX(1.1);
        scaleUpBig.setToY(1.1);
        scaleUpBig.setInterpolator(Interpolator.EASE_OUT);
        
        // Second hover animation: Scale back to 105%
        ScaleTransition scaleUpSmall = new ScaleTransition(Duration.millis(100));
        scaleUpSmall.setToX(1.05);
        scaleUpSmall.setToY(1.05);
        scaleUpSmall.setInterpolator(Interpolator.EASE_OUT);
        
        hoverAnimation.getChildren().addAll(scaleUpBig, scaleUpSmall);

        // Create a sequence of animations for mouse exit
        SequentialTransition exitAnimation = new SequentialTransition(button);
        
        // First exit animation: Scale down to 95%
        ScaleTransition scaleDownBig = new ScaleTransition(Duration.millis(150));
        scaleDownBig.setToX(0.95);
        scaleDownBig.setToY(0.95);
        scaleDownBig.setInterpolator(Interpolator.EASE_OUT);
        
        // Second exit animation: Return to original size
        ScaleTransition scaleDownSmall = new ScaleTransition(Duration.millis(100));
        scaleDownSmall.setToX(1.0);
        scaleDownSmall.setToY(1.0);
        scaleDownSmall.setInterpolator(Interpolator.EASE_OUT);
        
        exitAnimation.getChildren().addAll(scaleDownBig, scaleDownSmall);

        // Animation for when button is clicked (pressed)
        SequentialTransition clickAnimation = new SequentialTransition(button);
        // Scale down to 80% when clicked
        ScaleTransition scaleClick = new ScaleTransition(Duration.millis(100));
        scaleClick.setToX(0.8);
        scaleClick.setToY(0.8);
        scaleClick.setInterpolator(Interpolator.EASE_OUT);
        
        clickAnimation.getChildren().add(scaleClick);

        // Animation for when button is released
        SequentialTransition releaseAnimation = new SequentialTransition(button);
        // Scale back to 110% when released
        ScaleTransition scaleReleaseBig = new ScaleTransition(Duration.millis(150));
        scaleReleaseBig.setToX(1.1);
        scaleReleaseBig.setToY(1.1);
        scaleReleaseBig.setInterpolator(Interpolator.EASE_OUT);
        
        ScaleTransition scaleReleaseSmall = new ScaleTransition(Duration.millis(100));
        scaleReleaseSmall.setToX(1.0);
        scaleReleaseSmall.setToY(1.0);
        scaleReleaseSmall.setInterpolator(Interpolator.EASE_OUT);
        
        releaseAnimation.getChildren().addAll(scaleReleaseBig, scaleReleaseSmall);

        // Set up color adjustment effect for hover
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(0);
        button.setEffect(colorAdjust);

        // Timeline for darkening button on hover
        Timeline colorTimeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(colorAdjust.brightnessProperty(), 0)),
                new KeyFrame(Duration.millis(200), new KeyValue(colorAdjust.brightnessProperty(), -0.1))
        );

        // Timeline for restoring button color when hover ends
        Timeline reverseColorTimeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(colorAdjust.brightnessProperty(), -0.1)),
                new KeyFrame(Duration.millis(200), new KeyValue(colorAdjust.brightnessProperty(), 0))
        );

        // Event handlers for mouse interactions
        button.setOnMouseEntered(e -> {
            hoverAnimation.play();
            colorTimeline.play();
        });

        button.setOnMouseExited(e -> {
            exitAnimation.play();
            reverseColorTimeline.play();
        });

        button.setOnMousePressed(e -> {
            clickAnimation.play();
        });

        button.setOnMouseReleased(e -> {
            releaseAnimation.play();
        });
    }

    // Handler for starting a new game
    @FXML
    private void startNewGame() {
        // Switch to the game scene
        SceneController.getInstance().switchToGame();
    }

    // Handler for loading a saved game
    @FXML
    private void loadGame() {
        // Switch to the save menu scene
        SceneController.getInstance().switchToSaveMenu();
    }

    // Handler for opening settings menu
    @FXML
    private void openSettings() {
        // Switch to the settings scene
        SceneController.getInstance().switchToSettings();
    }

    // Handler for exiting the game
    @FXML
    private void exitGame() {
        // Terminate the application
        System.exit(0);
    }
}
