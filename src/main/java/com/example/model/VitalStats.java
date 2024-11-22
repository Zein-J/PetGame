package com.example.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class VitalStats {
    private final IntegerProperty hunger = new SimpleIntegerProperty(80);      // Default: well-fed
    private final IntegerProperty happiness = new SimpleIntegerProperty(70);  // Default: fairly happy
    private final IntegerProperty energy = new SimpleIntegerProperty(100);    // Default: fully energized
    private final IntegerProperty hygiene = new SimpleIntegerProperty(100);   // Default: pristine

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

    public IntegerProperty hygieneProperty() {
        return hygiene;
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

    public int getHygiene() {
        return hygiene.get();
    }

    public void setHygiene(int value) {
        hygiene.set(clampValue(value));
    }

    // Clamp method to ensure values stay within range
    private int clampValue(int value) {
        return Math.max(0, Math.min(100, value));
    }
}
