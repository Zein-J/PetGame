package com.example.util;

import javafx.animation.TranslateTransition;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Toggle extends ToggleButton {
    private final Rectangle background;
    private final Circle thumb;
    private final TranslateTransition transition;

    public Toggle() {
        // Create background track
        background = new Rectangle(52, 32);
        background.setArcWidth(32);
        background.setArcHeight(32);
        background.setFill(Color.valueOf("#e9e9eb"));

        // Create thumb
        thumb = new Circle(14);
        thumb.setFill(Color.WHITE);
        thumb.setTranslateX(16);
        thumb.setEffect(new javafx.scene.effect.DropShadow(4, 0, 2, Color.rgb(0, 0, 0, 0.2)));

        // Stack components
        StackPane layout = new StackPane(background, thumb);
        setGraphic(layout);

        // Set up animation
        transition = new TranslateTransition(Duration.millis(200), thumb);

        // Style the toggle button
        setStyle("-fx-background-color: transparent; -fx-padding: 0;");

        // Add listener for state changes
        selectedProperty().addListener((obs, oldVal, newVal) -> {
            background.setFill(newVal ? Color.valueOf("#34c759") : Color.valueOf("#e9e9eb"));
            transition.setToX(newVal ? 36 : 16);
            transition.play();
        });
    }
}
