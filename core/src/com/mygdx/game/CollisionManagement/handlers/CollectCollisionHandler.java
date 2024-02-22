package com.mygdx.game.CollisionManagement.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.EntityManagement.Entity;
import com.mygdx.game.Lifecycle.HighScoreManager;

// CollectCollisionHandler handles the logic for what happens when a collision is detected
// between two entities, specifically designed for scenarios where collecting or hitting an entity
// results in gaining points and resetting its position.
public class CollectCollisionHandler extends BaseCollisionHandler {
    // Access to the HighScoreManager to update the score upon collision.
    public HighScoreManager highScoreManager = HighScoreManager.getInstance();

    // Constructor that takes two entities involved in the collision.
    public CollectCollisionHandler(Entity entity1, Entity entity2) {
        super(entity1, entity2);
    }

    @Override
    public void handleCollision() {
        // Log collision detection and deactivate the first entity.
        System.out.println("Collision detected. Deactivating entity1.");

        // Increment the current score by 1 point.
        highScoreManager.addToCurrentScore(1);

        // Set the first entity as inactive.
        entity1.setActive(false);

        // Immediately call to reset the entity's position after a fixed delay.
        resetEntityToTop(entity1);
    }

    // Schedules the entity to be reset to the top of the screen after a delay.
    public void resetEntityToTop(Entity entity1) {
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                // Ensure the task is run on the main LibGDX thread.
                Gdx.app.postRunnable(() -> {
                    // Randomize the X position within the entire screen width correctly.
                    // It should consider the entity's width to ensure it doesn't spawn partially outside.
                    float newX = MathUtils.random(0, Gdx.graphics.getWidth() - entity1.getX());

                    // Set the new X position and reset Y to just above the visible screen.
                    entity1.setX(newX);
                    entity1.setY(Gdx.graphics.getHeight() + 150);

                    // Reactivate the entity, making it visible and interactable again.
                    entity1.setActive(true);
                });
            }
        }, 2f); // Delay before resetting the entity, specified in seconds.
    }
}
