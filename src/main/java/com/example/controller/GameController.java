package com.example.controller;

import com.example.model.GameState;
import com.example.model.Pet;
import com.example.model.VitalStats;
import com.example.util.FileHandler;
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

import java.io.IOException;
import java.util.Random;

/**
 * Controller class responsible for managing the game scene/view.
 * This class handles the mole animation, sprite rendering, and navigation
 * to other scenes within the application.
 */
public class GameController {


    public ProgressBar energyBar;
    public ProgressBar healthBar;
    public ProgressBar hungerBar;
    public ProgressBar happinessBar;
    @FXML
    private ImageView moleSprite;

    private Timeline animation;
    private Timeline statsDecayTimeline;
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
            healthBar.progressProperty().bind(Bindings.divide(stats.healthProperty(), 100.0));
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
        startStatsDecay();
    }
    /**
     * Starts the timeline for the constant decay of stats.
     */
    private void startStatsDecay() {
        statsDecayTimeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            GameState gameState = GameState.getCurrentState();
            Pet pet = gameState.getPet();

            if (pet != null) {
                VitalStats stats = pet.getStats();

                // Decay the stats
                stats.decreaseEnergy(1);   // Decrease energy by 1 every second
                stats.decreaseHygiene(1);  // Decrease hygiene by 1 every second
                stats.decreaseHunger(1);   // Decrease hunger by 1 every second
                stats.decreaseHappiness(1); // Decrease happiness by 1 every second

                // Log the changes (for debugging)
                System.out.println("Stats Decayed: Energy=" + stats.getEnergy() +
                        ", Hygiene=" + stats.getHygiene() +
                        ", Hunger=" + stats.getHunger() +
                        ", Happiness=" + stats.getHappiness());
            }
        }));

        statsDecayTimeline.setCycleCount(Timeline.INDEFINITE); // Run indefinitely
        statsDecayTimeline.play(); // Start the timeline
    }

    /**
     * Stops the timeline for stats decay.
     */
    private void stopStatsDecay() {
        if (statsDecayTimeline != null) {
            statsDecayTimeline.stop();
        }
    }

    /**
     * Event handler for the back button.
     * Stops the mole animation and switches the scene back to the main menu.
     */
    @FXML
    private void goBack() {
        stopStatsDecay();
        if (animation != null) {
            animation.stop();
        }
        SceneController.getInstance().switchToMainMenu();
    }
    @FXML
    private void feedPet() {
        GameState gameState = GameState.getCurrentState();
        Pet pet = gameState.getPet();

        if (pet != null) {
            VitalStats stats = pet.getStats();
            stats.increaseHunger(20); // Increase hunger by 20
            stats.increaseHappiness(10); // Increase happiness by 10
            System.out.println(pet.getName() + " has been fed! Hunger and happiness increased.");
        } else {
            System.out.println("No pet to feed!");
        }

    }
    @FXML
    private void playPet(){
        GameState gameState = GameState.getCurrentState();
        Pet pet = gameState.getPet();

        if (pet != null) {
            VitalStats stats = pet.getStats();
            stats.decreaseEnergy(15); // Decrease energy by 15
            stats.increaseHappiness(20); // Increase happiness by 20
            stats.decreaseHunger(10); // Decrease hunger by 10
            System.out.println(pet.getName() + " is playing! Energy and hunger decreased, happiness increased.");
        } else {
            System.out.println("No pet to play with!");
        }
    }
    @FXML
    private void giveGift(){}
    @FXML
    private void exercisePet(){
        GameState gameState = GameState.getCurrentState();
        Pet pet = gameState.getPet();

        if (pet != null) {
            VitalStats stats = pet.getStats();
            stats.decreaseHunger(20); // Decrease hunger
            stats.decreaseHappiness(5); // Decrease happiness slightly
            stats.increaseEnergy(10); // Increase energy
            System.out.println(pet.getName() + " has exercised! Energy increased, hunger decreased.");
        } else {
            System.out.println("No pet to exercise!");
        }
    }
    @FXML
    private void takeVet(){
        GameState gameState = GameState.getCurrentState();
        Pet pet = gameState.getPet();

        if (pet != null) {
            VitalStats stats = pet.getStats();
            stats.increaseHealth(50); // Increase health
            stats.increaseEnergy(30); // Increase energy
            stats.decreaseHappiness(10); // Decrease happiness
            System.out.println(pet.getName() + " went to the vet! Health and hygiene increased, but happiness decreased.");
        } else {
            System.out.println("No pet to take to the vet!");
        }
    }
    @FXML
    private void openInventory(){
        System.out.println("Inventory has not been made yet");
        stopStatsDecay();
        if (animation != null) {
            animation.stop();
        }
        SceneController.getInstance().switchToInventory();
    }
    @FXML
    private void saveGame() {
        GameState gameState = GameState.getCurrentState();
        Pet pet = gameState.getPet();
        try {
            FileHandler fileHandler = new FileHandler();
            fileHandler.saveGame("slot" + pet.getSaveID(), gameState); // Save with a filename
            System.out.println("Game saved successfully!");
        } catch (IOException e) {
            System.err.println("Failed to save game: " + e.getMessage());
            e.printStackTrace();
        }
    }
    private void handleCriticalState(int index) {
        switch (index) {
            case 0: // Hunger
                System.out.println("Hunger is critically low! Consider feeding the pet.");
                // Add additional logic here (e.g., display a warning in the UI)
                break;
            case 1: // Happiness
                System.out.println("Happiness is critically low! Consider playing with the pet.");
                // Add additional logic here
                break;
            case 2: // Energy
                System.out.println("Energy is critically low! Pet go zzz.");
                // Add additional logic here
                break;
            case 3: // Health
                System.out.println("Health is too low game over.");
                // Add additional logic here
                break;
            default:
                System.out.println("Unknown critical state detected.");
                break;
        }
    }

}


