package GameEngine.AIControl;
import com.badlogic.gdx.math.MathUtils;
import com.mygdx.game.GameLayer.GameEntities.Movers.PlayerActor;

public class ShakingHandler {
    // Method to update shaking effect on the bucket
    public static void updateShaking(PlayerActor playerActor, float delta) {
        if (playerActor.isShaking()) { // Check if the bucket is currently shaking
            float shakeTimer = playerActor.getShakeTimer();
            float shakeDuration = playerActor.getShakeDuration();
            float shakeIntensity = playerActor.getShakeIntensity();

            shakeTimer += delta; // Increment shakeTimer based on delta time

            if (shakeTimer <= shakeDuration) { // Check if the shake duration has not been exceeded
                // Apply shaking effect by randomly offsetting the bucket's position within the intensity range
                float shakeOffsetX = MathUtils.random(-shakeIntensity, shakeIntensity);
                float shakeOffsetY = MathUtils.random(-shakeIntensity, shakeIntensity);
                playerActor.setPosition(playerActor.getX() + shakeOffsetX, playerActor.getY() + shakeOffsetY);
            } else {
                // Stop shaking and reset timer when duration is reached
                playerActor.setShaking(false);
                shakeTimer = 0; // Reset the shake timer
            }
            // Update the shakeTimer in the PlayerActor
            playerActor.setShakeTimer(shakeTimer);
        }
    }
}
