// PlayerController.java
package GameEngine.PlayerControl;

import com.mygdx.game.GameLayer.GameEntities.Movers.BucketActor;

/**
 * This class is responsible for controlling the player's actions.
 * It uses a BucketMovementHandler to handle the player's movement and a BucketDirectionHandler to handle the player's direction change.
 * It uses an InputHandlerInterface to get the player's input.
 * Directly links to GamePlay scene
 */
public class PlayerController {
    private final BucketMovementHandler movementHandler;
    private final BucketDirectionHandler directionHandler;

    public PlayerController(BucketActor bucketActor, float speed) {
        InputHandlerInterface inputHandler = new GdxInputHandler();
        this.movementHandler = new BucketMovementHandler(bucketActor, speed, inputHandler);
        this.directionHandler = new BucketDirectionHandler(bucketActor, inputHandler);
    }

    public void handleInput(float deltaTime) {
        movementHandler.handleMovement(deltaTime);
        directionHandler.handleDirectionChange();
    }
}