package GameEngine.AIControl;
import com.mygdx.game.GameLayer.GameEntities.Movers.PlayerActor;
/**
 * ShakingManager is a class that manages the shaking behavior of a PlayerActor in a game.
 * This includes:
 * - Providing a static method to start shaking the PlayerActor. In this method, it sets the PlayerActor to shaking state, sets the duration and intensity of the shake, and resets the shake timer.
 */
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