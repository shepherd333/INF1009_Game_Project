// PlayerDirectionHandler.java
package GameEngine.PlayerControl;

import com.badlogic.gdx.Input;
import com.mygdx.game.GameLayer.GameEntities.Movers.PlayerActor;
import GameEngine.Collisions.handlers.enums.Direction;

/**
 * This class is responsible for handling the direction change of a PlayerActor.
 * It uses an InputHandlerInterface to determine which direction the PlayerActor should face,
 * and then updates the PlayerActor's direction accordingly.
 */
public class PlayerDirectionHandler {
    private final PlayerActor playerActor;
    private final InputHandlerInterface inputHandler;

    public PlayerDirectionHandler(PlayerActor playerActor, InputHandlerInterface inputHandler) {
        this.playerActor = playerActor;
        this.inputHandler = inputHandler;
    }

    public void handleDirectionChange() {
        if (inputHandler.isKeyPressed(Input.Keys.LEFT)) {
            playerActor.changeDirection(Direction.LEFT);
        }
        if (inputHandler.isKeyPressed(Input.Keys.RIGHT)) {
            playerActor.changeDirection(Direction.RIGHT);
        }
        if (inputHandler.isKeyPressed(Input.Keys.UP)) {
            playerActor.changeDirection(Direction.UP);
        }
        if (inputHandler.isKeyPressed(Input.Keys.DOWN)) {
            playerActor.changeDirection(Direction.DOWN);
        }
    }
}