// BucketMovementHandler.java
package GameEngine.PlayerControl;

import com.badlogic.gdx.Input;
import com.mygdx.game.GameLayer.GameEntities.Movers.BucketActor;

/**
 * This class is responsible for handling the movement of a BucketActor.
 * It uses an InputHandlerInterface to determine which direction the BucketActor should move in,
 * and then updates the BucketActor's position accordingly.
 */
public class BucketMovementHandler {
    private final BucketActor bucketActor;
    private final float speed;
    private final InputHandlerInterface inputHandler;

    public BucketMovementHandler(BucketActor bucketActor, float speed, InputHandlerInterface inputHandler) {
        this.bucketActor = bucketActor;
        this.speed = speed;
        this.inputHandler = inputHandler;
    }

    public void handleMovement(float deltaTime) {
        float newX = bucketActor.getX(), newY = bucketActor.getY();

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

        bucketActor.setPosition(newX, newY);
    }
}