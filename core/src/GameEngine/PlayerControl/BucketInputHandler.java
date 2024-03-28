package GameEngine.PlayerControl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.mygdx.game.GameLayer.GameEntities.Movers.BucketActor;
import GameEngine.Collisions.handlers.enums.Direction;

public class BucketInputHandler {
    private final BucketActor bucketActor;
    private final float speed;

    public BucketInputHandler(BucketActor bucketActor, float speed) {
        this.bucketActor = bucketActor;
        this.speed = speed;
    }

    private void logDebug(String message) {
        Gdx.app.log("BucketInputHandler", message);
    }

    public void handle(float deltaTime) {
        float newX = bucketActor.getX(), newY = bucketActor.getY();
        boolean moved = false; // Track if movement occurs

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            newX -= speed * deltaTime;
            bucketActor.changeDirection(Direction.LEFT);
            moved = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            newX += speed * deltaTime;
            bucketActor.changeDirection(Direction.RIGHT);
            moved = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            newY += speed * deltaTime;
            bucketActor.changeDirection(Direction.UP);
            moved = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            newY -= speed * deltaTime;
            bucketActor.changeDirection(Direction.DOWN);
            moved = true;
        }

        if (moved) {
            bucketActor.setPosition(newX, newY);
            //logDebug("Moved to x: " + newX + ", y: " + newY);
        }
    }
}


