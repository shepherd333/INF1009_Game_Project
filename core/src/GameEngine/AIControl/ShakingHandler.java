package GameEngine.AIControl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.mygdx.game.GameLayer.GameEntities.Movers.BucketActor;

public class ShakingHandler {
    public static void updateShaking(BucketActor bucketActor, float delta) {
        if (bucketActor.isShaking()) {
            float shakeTimer = bucketActor.getShakeTimer();
            float shakeDuration = bucketActor.getShakeDuration();
            float shakeIntensity = bucketActor.getShakeIntensity();

//            Gdx.app.log("ShakingHandler", "Shake Timer: " + shakeTimer);
//            Gdx.app.log("ShakingHandler", "Shake Duration: " + shakeDuration);
//            Gdx.app.log("ShakingHandler", "Shake Intensity: " + shakeIntensity);

            shakeTimer += delta; // Increment shakeTimer only when shaking is active

            if (shakeTimer <= shakeDuration) { // Adjusted condition for shake duration check
                // Apply shaking by randomly offsetting the actor's position within the shake intensity range
                float shakeOffsetX = MathUtils.random(-shakeIntensity, shakeIntensity);
                float shakeOffsetY = MathUtils.random(-shakeIntensity, shakeIntensity);
                bucketActor.setPosition(bucketActor.getX() + shakeOffsetX, bucketActor.getY() + shakeOffsetY);
            } else {
                bucketActor.setShaking(false); // Stop shaking when duration is reached
                shakeTimer = 0; // Reset the shake timer
            }

            // Update the shakeTimer in the BucketActor
            bucketActor.setShakeTimer(shakeTimer);
        }
    }
}
