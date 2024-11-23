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
            stats.increaseHygiene(50); // Increase hygiene
            stats.increaseEnergy(30); // Increase health
            stats.decreaseHappiness(10); // Decrease happiness
            System.out.println(pet.getName() + " went to the vet! Health and hygiene increased, but happiness decreased.");
        } else {
            System.out.println("No pet to take to the vet!");
        }
    }
    @FXML
    private void openInventory(){
        System.out.println("Inventory has not been made yet");
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
}


