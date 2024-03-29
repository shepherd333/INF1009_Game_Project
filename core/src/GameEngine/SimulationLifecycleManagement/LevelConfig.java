package GameEngine.SimulationLifecycleManagement;
import GameEngine.Collisions.handlers.enums.ItemType;

/**
 * LevelConfig is responsible for storing the configuration for a game level.
 * This includes:
 * - The spawn speed factor, which affects the spawn speed of items on the conveyor belt.
 * - The movement speed factor, which affects the movement speed of items on the conveyor belt.
 * - The number of toxic waste items to spawn.
 * - The types of bins to spawn.
 * - The level number.
 * - The path to the background image for the level.
 *
 * The LevelConfig class is used to initialize these settings for each level in the game.
 */
public class LevelConfig {
    public float spawnSpeedFactor; // Factor affecting spawn speed of items on the conveyor belt
    public float movementSpeedFactor; // Factor affecting movement speed of items on the conveyor belt
    public int spawnToxicWaste; // Number of toxic waste items to spawn
    public ItemType[] spawnTypes; // Array of bin types to spawn
    public int levelNumber; // Level number to be set for each level
    public String backgroundImagePath; // New field for background image path

    // Constructor to initialize a LevelConfig object with provided parameters.
    public LevelConfig(float spawnSpeedFactor, float movementSpeedFactor, int spawnToxicWaste, ItemType[] spawnTypes, int levelNumber, String backgroundImagePath) {
        this.spawnSpeedFactor = spawnSpeedFactor; // Set the spawn speed
        this.movementSpeedFactor = movementSpeedFactor; // Set the movement speed
        this.spawnToxicWaste = spawnToxicWaste; // Set the number of toxic bin spawns
        this.spawnTypes = spawnTypes; // Initialize with desired bin types
        this.levelNumber = levelNumber; // Set the level number
        this.backgroundImagePath = backgroundImagePath; // Initialize the new field
    }
}