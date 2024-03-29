package GameEngine.Collisions.handlers;
import GameEngine.SimulationLifecycleManagement.AudioManager;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.GameLayer.GameEntities.Movers.PlayerActor;
import com.mygdx.game.GameLayer.GameEntities.Movers.ToxicWasteActor;
/**
 * PlayerToxicHandler is a class that handles collisions between the player actor and toxic waste actors in a game.
 * This includes:
 * - Providing a static method to check for collisions between the player actor and toxic waste actors.
 * - In this method, it iterates over all actors in the stage.
 * - If an actor is an instance of ToxicWasteActor and its bounds overlap with the player actor's bounds, it decreases the player actor's life.
 * - It also checks if enough time has passed since the last sound effect was played. If so, it plays a collision sound effect and updates the lastSoundPlayTime.
 */
public class PlayerToxicHandler {

    // Checks for collisions between the bucket actor and toxic waste actors.
    public static void checkToxicWasteCollision(PlayerActor playerActor, Stage stage, AudioManager audioManager) {
        Array<Actor> actors = stage.getActors();
        float currentTime = TimeUtils.nanoTime(); // Use LibGDX TimeUtils to get the current time in nanoseconds

        for (Actor actor : actors) {
            if (actor instanceof ToxicWasteActor) {
                ToxicWasteActor toxicWaste = (ToxicWasteActor) actor;
                // Check if the bucket actor's bounds overlap with the toxic waste actor's bounds.
                if (playerActor.getBounds().overlaps(toxicWaste.getBounds())) {
                    // Decrease the bucket actor's life
                    playerActor.decreaseLife(0.3F); // Adjust the decrease rate as needed

                    // Check if enough time has passed since the last sound effect was played
                    if (currentTime - playerActor.lastSoundPlayTime > 1000000000) { // 1 second delay, adjust as needed
                        // Play the collision sound effect
                        audioManager.playSoundEffect("collision", 1.0f);
                        // Update the lastSoundPlayTime
                        playerActor.lastSoundPlayTime = currentTime;
                    }
                    break; // Exit the loop after handling the collision with the first toxic waste actor
                }
            }
        }
    }

}
