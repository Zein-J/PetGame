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

public class MainMenuController {
    @FXML
    private Button newGameButton;
    @FXML
    private Button loadGameButton;
    @FXML
    private Button settingsButton;
    @FXML
    private Button exitButton;

    @FXML
    public void initialize() {
        // Create hover animations for each button
        setupHoverAnimation(newGameButton);
        setupHoverAnimation(loadGameButton);
        setupHoverAnimation(settingsButton);
        setupHoverAnimation(exitButton);
    }

    private void setupHoverAnimation(Button button) {
        SequentialTransition hoverAnimation = new SequentialTransition(button);
        
        ScaleTransition scaleUpBig = new ScaleTransition(Duration.millis(150));
        scaleUpBig.setToX(1.1);
        scaleUpBig.setToY(1.1);
        scaleUpBig.setInterpolator(Interpolator.EASE_OUT);
        
        ScaleTransition scaleUpSmall = new ScaleTransition(Duration.millis(100));
        scaleUpSmall.setToX(1.05);
        scaleUpSmall.setToY(1.05);
        scaleUpSmall.setInterpolator(Interpolator.EASE_OUT);
        
        hoverAnimation.getChildren().addAll(scaleUpBig, scaleUpSmall);

        SequentialTransition exitAnimation = new SequentialTransition(button);
        
        ScaleTransition scaleDownBig = new ScaleTransition(Duration.millis(150));
        scaleDownBig.setToX(0.95);
        scaleDownBig.setToY(0.95);
        scaleDownBig.setInterpolator(Interpolator.EASE_OUT);
        
        ScaleTransition scaleDownSmall = new ScaleTransition(Duration.millis(100));
        scaleDownSmall.setToX(1.0);
        scaleDownSmall.setToY(1.0);
        scaleDownSmall.setInterpolator(Interpolator.EASE_OUT);
        
        exitAnimation.getChildren().addAll(scaleDownBig, scaleDownSmall);

        SequentialTransition clickAnimation = new SequentialTransition(button);
        
        ScaleTransition scaleClick = new ScaleTransition(Duration.millis(100));
        scaleClick.setToX(0.8);
        scaleClick.setToY(0.8);
        scaleClick.setInterpolator(Interpolator.EASE_OUT);
        
        clickAnimation.getChildren().add(scaleClick);

        SequentialTransition releaseAnimation = new SequentialTransition(button);
        
        ScaleTransition scaleReleaseBig = new ScaleTransition(Duration.millis(150));
        scaleReleaseBig.setToX(1.1);
        scaleReleaseBig.setToY(1.1);
        scaleReleaseBig.setInterpolator(Interpolator.EASE_OUT);
        
        ScaleTransition scaleReleaseSmall = new ScaleTransition(Duration.millis(100));
        scaleReleaseSmall.setToX(1.0);
        scaleReleaseSmall.setToY(1.0);
        scaleReleaseSmall.setInterpolator(Interpolator.EASE_OUT);
        
        releaseAnimation.getChildren().addAll(scaleReleaseBig, scaleReleaseSmall);

        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(0);
        button.setEffect(colorAdjust);

        Timeline colorTimeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(colorAdjust.brightnessProperty(), 0)),
                new KeyFrame(Duration.millis(200), new KeyValue(colorAdjust.brightnessProperty(), -0.1))
        );

        Timeline reverseColorTimeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(colorAdjust.brightnessProperty(), -0.1)),
                new KeyFrame(Duration.millis(200), new KeyValue(colorAdjust.brightnessProperty(), 0))
        );

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
        SceneController.getInstance().switchToSettings();
    }

    @FXML
    private void exitGame() {
        System.exit(0);
    }
}
