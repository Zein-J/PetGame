package com.example.model;

import java.time.LocalDateTime;

// Model class representing the complete state of the game at any given moment
// Uses the Singleton pattern to maintain a single current game state
public class GameState {
    // Static reference to the current game state (Singleton pattern)
    private static GameState currentState;

    // Timestamp of when this game state was last saved
    private LocalDateTime savedAt;
    
    // The player's pet instance
    private Pet pet;
    
    // The pet's current vital statistics
    private VitalStats stats;

    // Constructor initializes a new game state with current timestamp
    public GameState() {
        this.savedAt = LocalDateTime.now();
    }

    // Gets the current game state, creating a new one if none exists
    // @return The current GameState instance
    public static GameState getCurrentState() {
        if (currentState == null) {
            currentState = new GameState();
        }
        return currentState;
    }

    // Loads a saved game state as the current state
    // @param state The GameState to load as current
    public static void loadState(GameState state) {
        currentState = state;
    }

    // Standard getters and setters for game state properties
    
    public LocalDateTime getSavedAt() {
        return savedAt;
    }

    public void setSavedAt(LocalDateTime savedAt) {
        this.savedAt = savedAt;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public VitalStats getStats() {
        return stats;
    }

    public void setStats(VitalStats stats) {
        this.stats = stats;
    }
}