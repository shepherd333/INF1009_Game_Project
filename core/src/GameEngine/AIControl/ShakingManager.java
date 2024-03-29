package GameEngine.AIControl;
import com.mygdx.game.GameLayer.GameEntities.Movers.PlayerActor;

public class ShakingManager {
    // Method to start shaking the bucket
    public static void startShaking(PlayerActor playerActor, float duration, float intensity) {
        // Set the bucket to shaking state
        playerActor.setShaking(true);

        // Set the duration and intensity of the shake
        playerActor.setShakeDuration(duration);
        playerActor.setShakeIntensity(intensity);

        // Reset the shake timer
        playerActor.setShakeTimer(0f);
    }
}