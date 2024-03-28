package GameEngine.PlayerControl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.mygdx.game.GameLayer.GameEntities.Movers.BucketActor;
import com.mygdx.game.GameLayer.GameEntities.Movers.enums.Direction;
import com.mygdx.game.GameLayer.GameEntities.Movers.enums.ItemType;

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
            logDebug("Moved to x: " + newX + ", y: " + newY);
        }

        // Handling the SPACE key press for item pickup/drop
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            ItemType overlappingBinType = bucketActor.getOverlappingBinType();
            ItemType heldItemType = bucketActor.getHeldItemType();

            logDebug("SPACE key pressed. Overlapping bin type: " + (overlappingBinType == null ? "None" : overlappingBinType) +
                    ", Held item type: " + (heldItemType == null ? "None" : heldItemType));

            if (overlappingBinType != null && overlappingBinType == heldItemType) {
                logDebug("Attempting to drop item into correct bin.");
                bucketActor.dropHeldItem();
            }
            else if (overlappingBinType != null && overlappingBinType != heldItemType) {
                logDebug("Attempting to drop item into incorrect bin. Starting shake.");
                bucketActor.errorDropHeldItem();
                bucketActor.startShaking(0.5F, 1);
            } else {
                logDebug("No overlapping bin detected or no item to drop.");
            }
        }
    }
}


