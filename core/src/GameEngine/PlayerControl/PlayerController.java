// PlayerController.java
package GameEngine.PlayerControl;

import com.mygdx.game.GameLayer.GameEntities.Movers.PlayerActor;

/**
 * This class is responsible for controlling the player's actions.
 * It uses a PlayerMovementHandler to handle the player's movement and a PlayerDirectionHandler to handle the player's direction change.
 * It uses an InputHandlerInterface to get the player's input.
 * Directly links to GamePlay scene
 */
public class PlayerController {
    private final PlayerMovementHandler movementHandler;
    private final PlayerDirectionHandler directionHandler;

    public PlayerController(PlayerActor playerActor, float speed) {
        InputHandlerInterface inputHandler = new GdxInputHandler();
        this.movementHandler = new PlayerMovementHandler(playerActor, speed, inputHandler);
        this.directionHandler = new PlayerDirectionHandler(playerActor, inputHandler);
    }

    public void handleInput(float deltaTime) {
        movementHandler.handleMovement(deltaTime);
        directionHandler.handleDirectionChange();
    }
}