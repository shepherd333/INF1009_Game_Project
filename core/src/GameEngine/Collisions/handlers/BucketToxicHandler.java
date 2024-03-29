package GameEngine.Collisions.handlers;
import GameEngine.SimulationLifecycleManagement.AudioManager;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.GameLayer.GameEntities.Movers.BucketActor;
import com.mygdx.game.GameLayer.GameEntities.Movers.ToxicWasteActor;
// Handles collisions between the bucket actor and toxic waste actors.
public class BucketToxicHandler {

    // Checks for collisions between the bucket actor and toxic waste actors.
    public static void checkToxicWasteCollision(BucketActor bucketActor, Stage stage, AudioManager audioManager) {
        Array<Actor> actors = stage.getActors();
        for (Actor actor : actors) {
            if (actor instanceof ToxicWasteActor) {
                ToxicWasteActor toxicWaste = (ToxicWasteActor) actor;
                // Check if the bucket actor's bounds overlap with the toxic waste actor's bounds.
                if (bucketActor.getBounds().overlaps(toxicWaste.getBounds())) {
                    // Decrease the bucket actor's life and play a collision sound effect.
                    bucketActor.decreaseLife(0.1F); // Adjust the decrease rate as needed
                    audioManager.playSoundEffect("collision", 1.0f);
                    break; // Exit the loop after handling the collision with the first toxic waste actor
                }
            }
        }
    }
}
