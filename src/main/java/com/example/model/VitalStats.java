package com.example.model;

// Model class representing the vital statistics/attributes of a pet
public class VitalStats {
    // Core vital statistics, ranging from 0 to 100
    private int hunger;      // 0 = starving, 100 = full
    private int happiness;   // 0 = depressed, 100 = ecstatic
    private int energy;      // 0 = exhausted, 100 = energetic
    private int hygiene;     // 0 = filthy, 100 = pristine

    // Default constructor initializes stats to default values
    public VitalStats() {
        // Start with moderate/good values for all stats
        this.hunger = 80;    // Pet starts well-fed
        this.happiness = 70; // Pet starts fairly happy
        this.energy = 100;   // Pet starts fully energized
        this.hygiene = 100;  // Pet starts completely clean
    }

    // Getters and setters for each vital statistic
    // Each setter ensures values stay within valid range (0-100)

    public int getHunger() {
        return hunger;
    }

    public void setHunger(int hunger) {
        this.hunger = clampValue(hunger);
    }

    public int getHappiness() {
        return happiness;
    }

    public void setHappiness(int happiness) {
        this.happiness = clampValue(happiness);
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = clampValue(energy);
    }

    public int getHygiene() {
        return hygiene;
    }

    public void setHygiene(int hygiene) {
        this.hygiene = clampValue(hygiene);
    }

    // Helper method to ensure values stay within valid range
    // @param value The value to clamp
    // @return The value clamped between 0 and 100
    private int clampValue(int value) {
        return Math.max(0, Math.min(100, value));
    }
}
