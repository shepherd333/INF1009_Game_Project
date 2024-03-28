// BucketDirectionHandler.java
package GameEngine.PlayerControl;

import com.badlogic.gdx.Input;
import com.mygdx.game.GameLayer.GameEntities.Movers.BucketActor;

/**
 * This class is responsible for handling the direction change of a BucketActor.
 * It uses an InputHandlerInterface to determine which direction the BucketActor should face,
 * and then updates the BucketActor's direction accordingly.
 */
public class BucketDirectionHandler {
    private final BucketActor bucketActor;
    private final InputHandlerInterface inputHandler;

    public BucketDirectionHandler(BucketActor bucketActor, InputHandlerInterface inputHandler) {
        this.bucketActor = bucketActor;
        this.inputHandler = inputHandler;
    }

    public void handleDirectionChange() {
        if (inputHandler.isKeyPressed(Input.Keys.LEFT)) {
            bucketActor.changeDirection(BucketActor.Direction.LEFT);
        }
        if (inputHandler.isKeyPressed(Input.Keys.RIGHT)) {
            bucketActor.changeDirection(BucketActor.Direction.RIGHT);
        }
        if (inputHandler.isKeyPressed(Input.Keys.UP)) {
            bucketActor.changeDirection(BucketActor.Direction.UP);
        }
        if (inputHandler.isKeyPressed(Input.Keys.DOWN)) {
            bucketActor.changeDirection(BucketActor.Direction.DOWN);
        }
    }
}