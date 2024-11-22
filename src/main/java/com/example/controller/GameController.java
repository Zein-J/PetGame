package com.example.controller;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.util.Duration;
import javafx.scene.paint.Color;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.Image;
import java.util.Random;

/**
 * Controller class responsible for managing the game scene/view.
 * This class handles the mole animation, sprite rendering, and navigation
 * to other scenes within the application.
 */
public class GameController {

    @FXML
    private ImageView moleSprite;

    private Timeline animation;
    private Random random = new Random();

    /**
     * Constructor for GameController.
     * Initializes the controller when the game scene is created.
     */
    public GameController() {
        System.out.println("GameController initialized");

    }

    /**
     * Initializes the game scene.
     * This method is automatically called after the FXML file is loaded.
     * It sets up the mole sprite, handles fallback rendering in case of errors,
     * and starts the mole animation.
     */
    @FXML
    public void initialize() {
        try {
            // Load image from resources
            Image moleImage = new Image(getClass().getResourceAsStream("/images/mole.png"));
            if (moleImage.isError()) {
                System.err.println("Error loading mole image: " + moleImage.getException());
                createFallbackMoleSprite();
            } else {
                moleSprite.setImage(moleImage);
            }

            // Start mole animation
            startMoleAnimation();
        } catch (Exception e) {
            System.err.println("Error in initialize: " + e.getMessage());
            e.printStackTrace();
            createFallbackMoleSprite();
        }
    }

    /**
     * Creates a fallback mole sprite in case the default mole image cannot be loaded.
     * The fallback sprite is a simple brown rectangle designed to mimic the mole's appearance.
     */
    private void createFallbackMoleSprite() {
        Rectangle moleRect = new Rectangle(300, 300);
        moleRect.setFill(Color.BROWN);
        moleRect.setArcWidth(20);
        moleRect.setArcHeight(20);

        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        WritableImage image = moleRect.snapshot(params, null);

        moleSprite.setImage(image);
    }

    /**
     * Starts the mole animation.
     * The mole moves randomly within a predefined area and changes direction to face
     * the direction of movement. Animation is designed to loop indefinitely.
     */
    private void startMoleAnimation() {
        // Center the mole initially
        moleSprite.setTranslateX(0);
        moleSprite.setTranslateY(0);

        moleSprite.getParent().layoutBoundsProperty().addListener((obs, old, bounds) -> {
            double containerWidth = bounds.getWidth();
            double containerHeight = bounds.getHeight();

            // Calculate movement bounds
            double maxOffset = Math.min(containerWidth, containerHeight) * 0.3; // 30% of smaller dimension

            animation = new Timeline(
                    new KeyFrame(Duration.seconds(2), event -> {
                        // Generate random positions relative to center
                        double newX = (random.nextDouble() * maxOffset * 2) - maxOffset;
                        double newY = (random.nextDouble() * maxOffset * 2) - maxOffset;

                        // Determine direction for sprite facing
                        double currentX = moleSprite.getTranslateX();
                        if (newX < currentX) {
                            moleSprite.setScaleX(-1);  // Face left
                        } else {
                            moleSprite.setScaleX(1);   // Face right
                        }

                        // Animate to new position
                        Timeline moveAnimation = new Timeline(
                                new KeyFrame(Duration.millis(1000),
                                        new KeyValue(moleSprite.translateXProperty(), newX),
                                        new KeyValue(moleSprite.translateYProperty(), newY)
                                )
                        );
                        moveAnimation.play();
                    })
            );

            animation.setCycleCount(Timeline.INDEFINITE);
            animation.play();
        });
    }

    /**
     * Event handler for the back button.
     * Stops the mole animation and switches the scene back to the main menu.
     */
    @FXML
    private void goBack() {
        if (animation != null) {
            animation.stop();
        }
        SceneController.getInstance().switchToMainMenu();
    }
}
