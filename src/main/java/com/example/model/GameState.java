package com.example.model;

import java.time.LocalDateTime;

public class GameState {
    private static GameState currentState;
    
    private LocalDateTime savedAt;
    private Pet pet;
    private VitalStats stats;
    // add other game state properties here
    
    public GameState() {
        this.savedAt = LocalDateTime.now();
    }
    
    // Getters and setters
    
    public static GameState getCurrentState() {
        if (currentState == null) {
            currentState = new GameState();
        }
        return currentState;
    }
    
    public static void loadState(GameState state) {
        currentState = state;
        // update game with the loaded state
    }
}