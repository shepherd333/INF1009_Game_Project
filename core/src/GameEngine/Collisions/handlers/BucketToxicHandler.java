package GameEngine.Collisions.handlers;
import GameEngine.SimulationLifecycleManagement.AudioManager;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.GameLayer.GameEntities.Movers.BucketActor;
import com.mygdx.game.GameLayer.GameEntities.Movers.ToxicWasteActor;
// Handles collisions between the bucket actor and toxic waste actors.
public class BucketToxicHandler {

    // Checks for collisions between the bucket actor and toxic waste actors.
    public static void checkToxicWasteCollision(BucketActor bucketActor, Stage stage, AudioManager audioManager) {
        Array<Actor> actors = stage.getActors();
        float currentTime = TimeUtils.nanoTime(); // Use LibGDX TimeUtils to get the current time in nanoseconds

        for (Actor actor : actors) {
            if (actor instanceof ToxicWasteActor) {
                ToxicWasteActor toxicWaste = (ToxicWasteActor) actor;
                // Check if the bucket actor's bounds overlap with the toxic waste actor's bounds.
                if (bucketActor.getBounds().overlaps(toxicWaste.getBounds())) {
                    // Decrease the bucket actor's life
                    bucketActor.decreaseLife(0.2F); // Adjust the decrease rate as needed

                    // Check if enough time has passed since the last sound effect was played
                    if (currentTime - bucketActor.lastSoundPlayTime > 1000000000) { // 1 second delay, adjust as needed
                        // Play the collision sound effect
                        audioManager.playSoundEffect("collision", 1.0f);
                        // Update the lastSoundPlayTime
                        bucketActor.lastSoundPlayTime = currentTime;
                    }
                    break; // Exit the loop after handling the collision with the first toxic waste actor
                }
            }
        }
    }

}
