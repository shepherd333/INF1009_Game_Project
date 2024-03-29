// BucketItemHandler.java
package GameEngine.Collisions.handlers;
import GameEngine.AIControl.ShakingManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.mygdx.game.GameLayer.GameEntities.Movers.BucketActor;
import GameEngine.Collisions.handlers.enums.ItemType;

// Handles interactions between the bucket actor and items.
public class BucketItemHandler {
    private final BucketActor bucketActor; // The bucket actor being handled

    // Constructor to initialize the handler with the bucket actor.
    public BucketItemHandler(BucketActor bucketActor) {
        this.bucketActor = bucketActor;
    }

    // Logs debug messages.
    private void logDebug(String message) {
        Gdx.app.log("BucketItemHandler", message);
    }

    // Handles picking up or dropping items by the bucket actor.
    public void handleItemPickupOrDrop() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            ItemType overlappingBinType = bucketActor.getOverlappingBinType();
            ItemType heldItemType = bucketActor.getHeldItemType();

            // Log information about the item interaction.
            logDebug("SPACE key pressed. Overlapping bin type: " + (overlappingBinType == null ? "None" : overlappingBinType) +
                    ", Held item type: " + (heldItemType == null ? "None" : heldItemType));

            // Check if the held item can be dropped into the overlapping bin.
            if (overlappingBinType != null && overlappingBinType == heldItemType) {
                logDebug("Attempting to drop item into correct bin.");
                bucketActor.dropHeldItem();
            }
            // If the overlapping bin is incorrect, initiate shaking and error handling.
            else if (overlappingBinType != null && overlappingBinType != heldItemType) {
                logDebug("Attempting to drop item into incorrect bin. Starting shake.");
                bucketActor.errorDropHeldItem();
                ShakingManager.startShaking(bucketActor, 0.5F, 4); // Shake the bucket
            } else {
                logDebug("No overlapping bin detected or no item to drop.");
            }
        }
    }
}