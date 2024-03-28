package GameEngine.Collisions;

import GameEngine.SimulationLifecycleManagement.AudioManager;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.GameLayer.GameEntities.Movers.BucketActor;
import com.mygdx.game.GameLayer.GameEntities.Movers.ToxicWasteActor;
public class BucketToxicHandler {
    public static void checkToxicWasteCollision(BucketActor bucketActor, Stage stage, AudioManager audioManager) {
        Array<Actor> actors = stage.getActors();
        for (Actor actor : actors) {
            if (actor instanceof ToxicWasteActor) {
                ToxicWasteActor toxicWaste = (ToxicWasteActor) actor;
                if (bucketActor.getBounds().overlaps(toxicWaste.getBounds())) {
                    bucketActor.decreaseLife(0.1F);
                    audioManager.playSoundEffect("collision", 1.0f);
                    break;
                }
            }
        }
    }
}
