package com.sebasu.journey.components;

public class Scene {
    String location;
    String timeOfDay;
    String weather;
    String biome;
    String []details;

    public Scene(String location, String timeOfDay, String weather, String biome, String ...details) {
        this.location = location;
        this.timeOfDay = timeOfDay;
        this.weather = weather;
        this.biome = biome;
        this.details = details;
    }

}
