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

    // Adjust methods

    public void increaseHygiene(int value) {
        hygiene.set(Math.min(100, hygiene.get() + value));
    }

    public void decreaseHygiene(int value) {
        hygiene.set(Math.max(0, hygiene.get() - value));
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



    // Clamp method to ensure values stay within range
    private int clampValue(int value) {
        return Math.max(0, Math.min(100, value));
    }
}
