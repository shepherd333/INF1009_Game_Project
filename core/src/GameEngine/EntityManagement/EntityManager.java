package GameEngine.EntityManagement;
import GameEngine.SimulationLifecycleManagement.LevelConfig;
import com.mygdx.game.GameLayer.GameEntities.Movers.Static.ConveyorBeltActor;
import com.mygdx.game.GameLayer.GameEntities.Movers.ToxicWasteActor;
import com.mygdx.game.GameLayer.Scenes.GamePlay;

/**
 * EntityManager is a class that manages the creation and initialization of game entities.
 * This includes:
 * - Holding the configuration for the level.
 * - Holding a reference to the game play scene.
 * - Holding a reference to the conveyor belt actor.
 * - Providing a constructor to initialize the EntityManager with a game play scene and a level configuration.
 * - Providing a method to initialize game entities based on the level configuration.
 * - Providing a method to spawn toxic waste entities based on a given count.
 * - Providing a method to setup the conveyor belt entity.
 */
public class EntityManager {
    private LevelConfig levelConfig; // The configuration for the level
    private final GamePlay gamePlay; // Reference to the game play scene
    private ConveyorBeltActor conveyorBelt; // The conveyor belt actor

    // Constructor for EntityManager class.
    public EntityManager(GamePlay gamePlay, LevelConfig levelConfig) {
        this.gamePlay = gamePlay;
        this.levelConfig = levelConfig;
    }

    // Method to initialize game entities based on the level configuration.
    public void initializeGameEntities() {
        spawnToxicWaste(levelConfig.spawnToxicWaste); // Spawn toxic waste entities
        setupConveyorBelt(); // Setup conveyor belt entity
    }

    // Method to spawn toxic waste entities based on the given count.
    private void spawnToxicWaste(int spawnToxicWaste) {
        for (int i = 0; i < spawnToxicWaste; i++) {
            ToxicWasteActor toxicWaste = new ToxicWasteActor();
            gamePlay.getStage().addActor(toxicWaste); // Add toxic waste actor to the stage
        }
    }

    // Method to setup the conveyor belt entity.
    private void setupConveyorBelt() {
        conveyorBelt = new ConveyorBeltActor();
        gamePlay.getStage().addActor(conveyorBelt); // Add conveyor belt actor to the stage
    }
}
