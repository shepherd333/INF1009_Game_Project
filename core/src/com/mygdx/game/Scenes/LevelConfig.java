package com.mygdx.game.Scenes;

public class LevelConfig {
    public float spawnSpeedFactor;
    public float movementSpeedFactor;
    public int spawnToxicWaste;

    // Constructor
    public LevelConfig(float spawnSpeedFactor, float movementSpeedFactor, int spawnToxicWaste) {
        this.spawnSpeedFactor = spawnSpeedFactor;
        this.movementSpeedFactor = movementSpeedFactor;
        this.spawnToxicWaste = spawnToxicWaste;

    }

    // Add any other level-specific settings or methods here
}
