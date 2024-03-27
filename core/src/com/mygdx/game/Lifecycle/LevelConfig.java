package com.mygdx.game.Lifecycle;

public class LevelConfig {
    public float spawnSpeedFactor;
    public float movementSpeedFactor;
    public final int spawnToxicWaste;

    // Constructor
    public LevelConfig(float spawnSpeedFactor, float movementSpeedFactor, int spawnToxicWaste) {
        this.spawnSpeedFactor = spawnSpeedFactor;
        this.movementSpeedFactor = movementSpeedFactor;
        this.spawnToxicWaste = spawnToxicWaste;
    }

    // Add any other level-specific settings or methods here
}
