package GameEngine.AIControl;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.GameLayer.GameEntities.Movers.BucketActor;

public class ShakingManager {
    public static void startShaking(BucketActor bucketActor, float duration, float intensity) {
        bucketActor.setShaking(true);
        bucketActor.setShakeDuration(duration);
        bucketActor.setShakeIntensity(intensity);
        bucketActor.setShakeTimer(0f); // Reset the shake timer

        // Log the duration and intensity being set
//        System.out.println("Shake Duration set to: " + duration);
//        System.out.println("Shake Intensity set to: " + intensity);
    }
}