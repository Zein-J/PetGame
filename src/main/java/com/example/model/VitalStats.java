package com.example.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class VitalStats {
    private final IntegerProperty hunger = new SimpleIntegerProperty(80);      // Default: well-fed
    private final IntegerProperty happiness = new SimpleIntegerProperty(70);  // Default: fairly happy
    private final IntegerProperty energy = new SimpleIntegerProperty(100);    // Default: fully energized
    private final IntegerProperty health = new SimpleIntegerProperty(100);    // Default: healthy

    // petState array to represent the state of each stat: 0 = OK, 1 = Critical
    private final int[] petState = {0, 0, 0, 0}; // Index 0 = Hunger, 1 = Happiness, 2 = Energy, 3 = Health
    // Define thresholds for stats
    private static final int CRITICAL_THRESHOLD = 20; // Critical threshold for warnings
    // Callback to notify the Pet object
    private Runnable thresholdCallback;
    public VitalStats() {
        // Add listeners to monitor thresholds
        hunger.addListener((observable, oldValue, newValue) -> updatePetState(0, newValue.intValue()));
        happiness.addListener((observable, oldValue, newValue) -> updatePetState(1, newValue.intValue()));
        energy.addListener((observable, oldValue, newValue) -> updatePetState(2, newValue.intValue()));
        health.addListener((observable, oldValue, newValue) -> updatePetState(3, newValue.intValue()));

    }

    // Getters for properties
    public IntegerProperty hungerProperty() {
        return hunger;
    }

    public IntegerProperty happinessProperty() {
        return happiness;
    }

    public IntegerProperty energyProperty() {
        return energy;
    }

    public IntegerProperty healthProperty() {
        return health;
    }

    // Getters and setters for values
    public int getHunger() {
        return hunger.get();
    }

    public void setHunger(int value) {
        hunger.set(clampValue(value));
    }

    public int getHappiness() {
        return happiness.get();
    }

    public void setHappiness(int value) {
        happiness.set(clampValue(value));
    }

    public int getEnergy() {
        return energy.get();
    }

    public void setEnergy(int value) {
        energy.set(clampValue(value));
    }

    public int getHealth() {
        return health.get();
    }

    public void setHealth(int value) {
        health.set(clampValue(value));
    }

    // Adjust methods
    public void increaseHealth(int value) {
        health.set(Math.min(100, health.get() + value));
    }

    public void decreaseHealth(int value) {
        health.set(Math.max(0, health.get() - value));
    }

    public void increaseHunger(int value) {
        hunger.set(Math.min(100, hunger.get() + value));
    }

    public void decreaseHunger(int value) {
        hunger.set(Math.max(0, hunger.get() - value));
    }

    public void increaseHappiness(int value) {
        happiness.set(Math.min(100, happiness.get() + value));
    }

    public void decreaseHappiness(int value) {
        happiness.set(Math.max(0, happiness.get() - value));
    }

    public void increaseEnergy(int value) {
        energy.set(Math.min(100, energy.get() + value));
    }

    public void decreaseEnergy(int value) {
        energy.set(Math.max(0, energy.get() - value));
    }
    public int[] getState(){return this.petState;}
    public int getVitalState(int index) {return this.petState[index];}

    // Update petState array based on the stat value
    private void updatePetState(int index, int newValue) {
        if (newValue < CRITICAL_THRESHOLD) {
            petState[index] = 1; // Critical state
            System.out.println(getStatName(index) + " is critically low! Current value: " + newValue);
        } else {
            petState[index] = 0; // Normal state
        }
    }

    // Helper to get the stat name from the index
    private String getStatName(int index) {
        switch (index) {
            case 0: return "Hunger";
            case 1: return "Happiness";
            case 2: return "Energy";
            case 3: return "Health";
            default: return "Unknown";
        }
    }

    // Clamp method to ensure values stay within range
    private int clampValue(int value) {
        return Math.max(0, Math.min(100, value));
    }
}
