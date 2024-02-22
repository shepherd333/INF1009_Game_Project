package com.mygdx.game.CollisionManagement.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.EntityManagement.Entity;
import com.mygdx.game.Lifecycle.HighScoreManager;

public class CollectCollisionHandler extends BaseCollisionHandler {
    public HighScoreManager highScoreManager = HighScoreManager.getInstance();
    public CollectCollisionHandler(Entity entity1, Entity entity2) {
        super(entity1, entity2);
    }

    @Override
    public void handleCollision() {
        // Handle the collision by deactivating entity1
        System.out.println("Collision detected. Deactivating entity1.");
        highScoreManager.addToCurrentScore(1);
        entity1.setActive(false);
        // Schedule the reset task after a delay on the main LibGDX thread
        resetEntityToTop(entity1);
    }

    public void resetEntityToTop(Entity entity1) {
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                Gdx.app.postRunnable(() -> {
                    // Correctly randomize the X position within the entire screen width
                    float newX = MathUtils.random(0, Gdx.graphics.getWidth() - entity1.getX());
                    entity1.setX(newX);
                    entity1.setY(Gdx.graphics.getHeight() + 150); // Reset Y to the top
                    entity1.setActive(true); // Reactivate the entity here, ensuring it's the last step
                });
            }
        }, 2f); // Assuming a 2-second delay
    }

}