package com.example.components;

import javafx.animation.TranslateTransition;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Interpolator;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class CustomToggle extends ToggleButton {
    private final Rectangle background;
    private final Circle thumb;
    private Timeline colorTimeline;
    private Timeline translateTimeline;
    private boolean isAnimating = false;

    public CustomToggle() {
        // create background track
        background = new Rectangle(39, 24);
        background.setArcWidth(24);
        background.setArcHeight(24);
        background.setFill(Color.valueOf("#e9e9eb"));

        // create thumb
        thumb = new Circle(10.5);
        thumb.setFill(Color.WHITE);
        thumb.setTranslateX(-7.5);
        thumb.setEffect(new javafx.scene.effect.DropShadow(3, 0, 1.5, Color.rgb(0, 0, 0, 0.2)));

        // stack components
        StackPane layout = new StackPane(background, thumb);
        layout.setMaxSize(39, 24);
        setGraphic(layout);
        
        // prevent default handling
        setOnMouseClicked(event -> {
            if (!isAnimating) {
                setSelected(!isSelected());
                event.consume();
            }
        });

        // style the toggle button
        setStyle("-fx-background-color: transparent; -fx-padding: 0;");

        // add listener for state changes
        selectedProperty().addListener((obs, oldVal, newVal) -> {
            animateToggle(newVal);
        });

        // set initial state
        background.setFill(Color.valueOf("#e9e9eb"));
        thumb.setTranslateX(-7.5);
    }

    private void animateToggle(boolean selected) {
        // stop any running animations
        if (colorTimeline != null) colorTimeline.stop();
        if (translateTimeline != null) translateTimeline.stop();
        
        isAnimating = true;

        // color transition
        Color startColor = (Color) background.getFill();
        Color endColor = selected ? Color.valueOf("#34c759") : Color.valueOf("#e9e9eb");
        
        colorTimeline = new Timeline(
            new KeyFrame(Duration.ZERO, new KeyValue(
                background.fillProperty(), 
                startColor,
                Interpolator.SPLINE(0.25, 0.1, 0.25, 1)
            )),
            new KeyFrame(Duration.millis(300), new KeyValue(
                background.fillProperty(), 
                endColor,
                Interpolator.SPLINE(0.25, 0.1, 0.25, 1)
            ))
        );

        // thumb translation
        double endX = selected ? 7.5 : -7.5;
        
        translateTimeline = new Timeline(
            new KeyFrame(Duration.ZERO, new KeyValue(
                thumb.translateXProperty(),
                thumb.getTranslateX(),
                Interpolator.SPLINE(0.25, 0.1, 0.25, 1)
            )),
            new KeyFrame(Duration.millis(300), new KeyValue(
                thumb.translateXProperty(),
                endX,
                Interpolator.SPLINE(0.25, 0.1, 0.25, 1)
            ))
        );

        // play animations
        colorTimeline.play();
        translateTimeline.play();
        translateTimeline.setOnFinished(e -> isAnimating = false);
    }
}
