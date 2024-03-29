// PlayerMovementHandler.java
package GameEngine.PlayerControl;

import com.badlogic.gdx.Input;
import com.mygdx.game.GameLayer.GameEntities.Movers.PlayerActor;

/**
 * This class is responsible for handling the movement of a PlayerActor.
 * It uses an InputHandlerInterface to determine which direction the PlayerActor should move in,
 * and then updates the PlayerActor's position accordingly.
 */
public class PlayerMovementHandler {
    private final PlayerActor playerActor;
    private final float speed;
    private final InputHandlerInterface inputHandler;

    public PlayerMovementHandler(PlayerActor playerActor, float speed, InputHandlerInterface inputHandler) {
        this.playerActor = playerActor;
        this.speed = speed;
        this.inputHandler = inputHandler;
    }

    public void handleMovement(float deltaTime) {
        float newX = playerActor.getX(), newY = playerActor.getY();

        if (inputHandler.isKeyPressed(Input.Keys.LEFT)) {
            newX -= speed * deltaTime;
        }
        if (inputHandler.isKeyPressed(Input.Keys.RIGHT)) {
            newX += speed * deltaTime;
        }
        if (inputHandler.isKeyPressed(Input.Keys.UP)) {
            newY += speed * deltaTime;
        }
        if (inputHandler.isKeyPressed(Input.Keys.DOWN)) {
            newY -= speed * deltaTime;
        }

        playerActor.setPosition(newX, newY);
    }
}