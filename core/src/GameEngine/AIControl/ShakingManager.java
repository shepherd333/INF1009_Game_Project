package GameEngine.AIControl;
import com.mygdx.game.GameLayer.GameEntities.Movers.BucketActor;

public class ShakingManager {
    // Method to start shaking the bucket
    public static void startShaking(BucketActor bucketActor, float duration, float intensity) {
        // Set the bucket to shaking state
        bucketActor.setShaking(true);

        // Set the duration and intensity of the shake
        bucketActor.setShakeDuration(duration);
        bucketActor.setShakeIntensity(intensity);

        // Reset the shake timer
        bucketActor.setShakeTimer(0f);
    }
}