package GameEngine.SimulationLifecycleManagement;

import com.mygdx.game.GameLayer.enums.ItemType;

public class LevelConfig {
    public float spawnSpeedFactor;
    public float movementSpeedFactor;
    public int spawnToxicWaste;
    public ItemType[] spawnTypes; // Array of bin types to spawn

    public LevelConfig(float spawnSpeedFactor, float movementSpeedFactor, int spawnToxicWaste, ItemType[] spawnTypes) {
        this.spawnSpeedFactor = spawnSpeedFactor;
        this.movementSpeedFactor = movementSpeedFactor;
        this.spawnToxicWaste = spawnToxicWaste;
        this.spawnTypes = spawnTypes; // Initialize with desired bin types

    }
}
