package com.example.controller;

import com.example.model.GameState;
import com.example.model.Pet;
import com.example.model.VitalStats;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
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

//TODO: Rewrite this whole file
/**
 * Controller class responsible for managing the game scene/view.
 * This class handles the mole animation, sprite rendering, and navigation
 * to other scenes within the application.
 */
public class GameController {


    public ProgressBar energyBar;
    public ProgressBar hygieneBar;
    public ProgressBar hungerBar;
    public ProgressBar happinessBar;
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
        // Get the current GameState instance
        GameState gameState = GameState.getCurrentState();
        Pet pet = gameState.getPet(); // Retrieve the Pet object
        if (pet != null) {
            VitalStats stats = pet.getStats();

            // Bind progress bars to stats
            energyBar.progressProperty().bind(Bindings.divide(stats.energyProperty(), 100.0));
            hygieneBar.progressProperty().bind(Bindings.divide(stats.hygieneProperty(), 100.0));
            hungerBar.progressProperty().bind(Bindings.divide(stats.hungerProperty(), 100.0));
            happinessBar.progressProperty().bind(Bindings.divide(stats.happinessProperty(), 100.0));


            System.out.println("Loaded Pet: " + pet.getName() + ", Type: " + pet.getSpecies());
            // Update UI or initialize game logic with the Pet's data
            //setupPetData(pet);
        } else {
            System.out.println("No pet found. Please create or load a save.");
        }
        try {
            // Load image from resources
            Image moleImage = new Image(getClass().getResourceAsStream("/images/mole.png"));
            if (moleImage.isError()) {
                System.err.println("Error loading mole image: " + moleImage.getException());
            } else {
                moleSprite.setImage(moleImage);
            }

        } catch (Exception e) {
            System.err.println("Error in initialize: " + e.getMessage());
            e.printStackTrace();

        }
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
