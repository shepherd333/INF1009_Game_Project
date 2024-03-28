// BucketItemHandler.java
package GameEngine.Collisions.handlers;

import GameEngine.AIControl.ShakingManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.mygdx.game.GameLayer.GameEntities.Movers.BucketActor;
import GameEngine.Collisions.handlers.enums.ItemType;

public class BucketItemHandler {
    private final BucketActor bucketActor;

    public BucketItemHandler(BucketActor bucketActor) {
        this.bucketActor = bucketActor;
    }

    private void logDebug(String message) {
        Gdx.app.log("BucketItemHandler", message);
    }

    public void handleItemPickupOrDrop() {
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
                ShakingManager.startShaking(bucketActor,0.5F, 4);
            } else {
                logDebug("No overlapping bin detected or no item to drop.");
            }
        }
    }
}