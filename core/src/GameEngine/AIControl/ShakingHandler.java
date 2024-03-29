package GameEngine.AIControl;
import com.badlogic.gdx.math.MathUtils;
import com.mygdx.game.GameLayer.GameEntities.Movers.BucketActor;

public class ShakingHandler {
    // Method to update shaking effect on the bucket
    public static void updateShaking(BucketActor bucketActor, float delta) {
        if (bucketActor.isShaking()) { // Check if the bucket is currently shaking
            float shakeTimer = bucketActor.getShakeTimer();
            float shakeDuration = bucketActor.getShakeDuration();
            float shakeIntensity = bucketActor.getShakeIntensity();

            shakeTimer += delta; // Increment shakeTimer based on delta time

            if (shakeTimer <= shakeDuration) { // Check if the shake duration has not been exceeded
                // Apply shaking effect by randomly offsetting the bucket's position within the intensity range
                float shakeOffsetX = MathUtils.random(-shakeIntensity, shakeIntensity);
                float shakeOffsetY = MathUtils.random(-shakeIntensity, shakeIntensity);
                bucketActor.setPosition(bucketActor.getX() + shakeOffsetX, bucketActor.getY() + shakeOffsetY);
            } else {
                // Stop shaking and reset timer when duration is reached
                bucketActor.setShaking(false);
                shakeTimer = 0; // Reset the shake timer
            }
            // Update the shakeTimer in the BucketActor
            bucketActor.setShakeTimer(shakeTimer);
        }
    }
}
