package com.example.components;

import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.util.Duration;
import javafx.animation.Interpolator;

public class CustomButton extends Button {
    private Timeline colorTimeline;
    private Timeline reverseColorTimeline;

    public CustomButton() {
        this("");
    }

    public CustomButton(String text) {
        super(text);
        setupAnimation();
        // set default style class
        getStyleClass().add("custom-button");
    }

    private void setupAnimation() {
        // hover animation
        SequentialTransition hoverAnimation = new SequentialTransition(this);
        ScaleTransition scaleUpBig = new ScaleTransition(Duration.millis(150));
        scaleUpBig.setToX(1.1);
        scaleUpBig.setToY(1.1);
        scaleUpBig.setInterpolator(Interpolator.EASE_OUT);
        
        ScaleTransition scaleUpSmall = new ScaleTransition(Duration.millis(100));
        scaleUpSmall.setToX(1.05);
        scaleUpSmall.setToY(1.05);
        scaleUpSmall.setInterpolator(Interpolator.EASE_OUT);
        
        hoverAnimation.getChildren().addAll(scaleUpBig, scaleUpSmall);

        // exit animation
        SequentialTransition exitAnimation = new SequentialTransition(this);
        ScaleTransition scaleDownBig = new ScaleTransition(Duration.millis(150));
        scaleDownBig.setToX(0.95);
        scaleDownBig.setToY(0.95);
        scaleDownBig.setInterpolator(Interpolator.EASE_OUT);
        
        ScaleTransition scaleDownSmall = new ScaleTransition(Duration.millis(100));
        scaleDownSmall.setToX(1.0);
        scaleDownSmall.setToY(1.0);
        scaleDownSmall.setInterpolator(Interpolator.EASE_OUT);
        
        exitAnimation.getChildren().addAll(scaleDownBig, scaleDownSmall);

        // click animation
        SequentialTransition clickAnimation = new SequentialTransition(this);
        ScaleTransition scaleClick = new ScaleTransition(Duration.millis(100));
        scaleClick.setToX(0.8);
        scaleClick.setToY(0.8);
        scaleClick.setInterpolator(Interpolator.EASE_OUT);
        
        clickAnimation.getChildren().add(scaleClick);

        // color effect
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(0);
        setEffect(colorAdjust);

        // initialize the timelines
        colorTimeline = new Timeline(
            new KeyFrame(Duration.ZERO, new KeyValue(colorAdjust.brightnessProperty(), 0)),
            new KeyFrame(Duration.millis(200), new KeyValue(colorAdjust.brightnessProperty(), -0.1))
        );

        reverseColorTimeline = new Timeline(
            new KeyFrame(Duration.ZERO, new KeyValue(colorAdjust.brightnessProperty(), -0.1)),
            new KeyFrame(Duration.millis(200), new KeyValue(colorAdjust.brightnessProperty(), 0))
        );

        // event handlers
        setOnMouseEntered(e -> {
            hoverAnimation.play();
            colorTimeline.play();
        });

        setOnMouseExited(e -> {
            exitAnimation.play();
            reverseColorTimeline.play();
        });

        setOnMousePressed(e -> clickAnimation.play());
    }
}