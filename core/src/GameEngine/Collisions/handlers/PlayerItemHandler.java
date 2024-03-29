// PlayerItemHandler.java
package GameEngine.Collisions.handlers;
import GameEngine.AIControl.ShakingManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.mygdx.game.GameLayer.GameEntities.Movers.PlayerActor;
import GameEngine.Collisions.handlers.enums.ItemType;

// Handles interactions between the bucket actor and items.
public class PlayerItemHandler {
    private final PlayerActor playerActor; // The bucket actor being handled

    // Constructor to initialize the handler with the bucket actor.
    public PlayerItemHandler(PlayerActor playerActor) {
        this.playerActor = playerActor;
    }

    // Logs debug messages.
    private void logDebug(String message) {
        Gdx.app.log("PlayerItemHandler", message);
    }

    // Handles picking up or dropping items by the bucket actor.
    public void handleItemPickupOrDrop() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            ItemType overlappingBinType = playerActor.getOverlappingBinType();
            ItemType heldItemType = playerActor.getHeldItemType();

            // Log information about the item interaction.
            logDebug("SPACE key pressed. Overlapping bin type: " + (overlappingBinType == null ? "None" : overlappingBinType) +
                    ", Held item type: " + (heldItemType == null ? "None" : heldItemType));

            // Check if the held item can be dropped into the overlapping bin.
            if (overlappingBinType != null && overlappingBinType == heldItemType) {
                logDebug("Attempting to drop item into correct bin.");
                playerActor.dropHeldItem();
            }
            // If the overlapping bin is incorrect, initiate shaking and error handling.
            else if (overlappingBinType != null && overlappingBinType != heldItemType) {
                logDebug("Attempting to drop item into incorrect bin. Starting shake.");
                playerActor.errorDropHeldItem();
                ShakingManager.startShaking(playerActor, 0.5F, 4); // Shake the bucket
            } else {
                logDebug("No overlapping bin detected or no item to drop.");
            }
        }
    }
}