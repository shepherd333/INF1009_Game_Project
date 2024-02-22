package com.mygdx.game.AIManagement;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.EntityManagement.RaindropEntity;
import com.mygdx.game.Lifecycle.LifeManager;

public class AIManager {
    protected Viewport viewport;
    public static LifeManager lifeManager = LifeManager.getInstance();
    public static void moveRaindrop(RaindropEntity raindrop) {
        // AI-controlled movement logic for the raindrop
        if (raindrop.getSpeed() > 0) {
            // Move the raindrop downward based on its speed
            raindrop.setY(raindrop.getY() - (float)raindrop.getSpeed()); // Cast to float if necessary

            // Check if the raindrop has reached the bottom of the screen
            if (raindrop.getY() <= 0) {
                // Reset the raindrop's position to the top with a new random X coordinate
                raindrop.setX(MathUtils.random(0, Gdx.graphics.getWidth() - raindrop.getX())); // Ensure the new X is within screen bounds
                raindrop.setY(Gdx.graphics.getHeight()+ 150);
                raindrop.setActive(true); // Set the raindrop to active again
                System.out.println("Raindrop reset to position: " + raindrop.getX() + ", " + raindrop.getY());
                lifeManager.loseLife();
            }
        }
    }
}
