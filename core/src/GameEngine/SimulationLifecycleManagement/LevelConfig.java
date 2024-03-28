package GameEngine.SimulationLifecycleManagement;

import GameEngine.Collisions.handlers.enums.ItemType;

public class LevelConfig {
    public float spawnSpeedFactor;
    public float movementSpeedFactor;
    public int spawnToxicWaste;
    public ItemType[] spawnTypes; // Array of bin types to spawn
    public int levelNumber; // Add level number


    public LevelConfig(float spawnSpeedFactor, float movementSpeedFactor, int spawnToxicWaste, ItemType[] spawnTypes, int i) {
        this.spawnSpeedFactor = spawnSpeedFactor;
        this.movementSpeedFactor = movementSpeedFactor;
        this.spawnToxicWaste = spawnToxicWaste;
        this.spawnTypes = spawnTypes; // Initialize with desired bin types
        this.levelNumber = i;

    }
}