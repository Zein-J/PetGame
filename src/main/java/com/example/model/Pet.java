package com.example.model;

// Model class representing a virtual pet in the game
public class Pet {
    // The name of the pet
    private String name;

    // Default no-args constructor required for Jackson JSON serialization/deserialization
    public Pet() {
    }

    // Constructor to create a new pet with a specified name
    // @param name The name to give to the pet
    public Pet(String name) {
        this.name = name;
    }

    // Gets the pet's name
    // @return The current name of the pet
    public String getName() {
        return name;
    }

    // Sets or changes the pet's name
    // @param name The new name for the pet
    public void setName(String name) {
        this.name = name;
    }
}
