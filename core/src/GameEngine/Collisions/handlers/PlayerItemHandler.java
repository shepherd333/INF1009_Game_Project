// PlayerItemHandler.java
package GameEngine.Collisions.handlers;
import GameEngine.AIControl.ShakingManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.mygdx.game.GameLayer.GameEntities.Movers.PlayerActor;
import GameEngine.Collisions.handlers.enums.ItemType;

/**
 * PlayerItemHandler is a class that handles interactions between the player actor and items in a game.
 * This includes:
 * - Holding a reference to the player actor being handled.
 * - Providing a constructor to initialize the handler with the player actor.
 * - Providing a method to log debug messages.
 * - Providing a method to handle picking up or dropping items by the player actor.
 * - In this method, it checks if the SPACE key is just pressed. If so, it gets the overlapping bin type and the held item type.
 * - If the held item can be dropped into the overlapping bin, it drops the held item.
 * - If the overlapping bin is incorrect, it initiates shaking and error handling.
 * - If there is no overlapping bin detected or no item to drop, it logs a debug message.
 */
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