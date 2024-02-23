package com.mygdx.game.AIManagement;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.EntityManagement.RaindropEntity;
import com.mygdx.game.Lifecycle.LifeManager;

// AIManager is responsible for controlling non-player entities in the game, like raindrops.
public class AIManager {
    // The viewport can be used to ensure movements and positions are calculated within the game's visible area.
    // However, it's not used in the current implementation.
    private Viewport viewport;

    // Singleton instance of the LifeManager to manage game life state.
    public static LifeManager lifeManager = LifeManager.getInstance();

    // Static method to move a raindrop entity based on its speed and reset its position when it goes off-screen.
    public static void moveRaindrop(RaindropEntity raindrop) {
        // Check if the raindrop's speed is greater than 0 to ensure movement.
        if (raindrop.getSpeed() > 0) {
            // Move the raindrop downward by decreasing its Y position according to its speed.
            // The cast to float ensures the operation's result is correctly interpreted as a float.
            raindrop.setY(raindrop.getY() - (float)raindrop.getSpeed());

            // Check if the raindrop has reached the bottom of the screen.
            if (raindrop.getY() <= 0) {
                // Reset the raindrop's position to the top with a new random X coordinate.
                // The calculation here intends to randomize the X position within the screen bounds,
                // but it incorrectly subtracts the raindrop's current X from the screen width.
                // It should directly randomize within the full width range instead.
                raindrop.setX(MathUtils.random(0, Gdx.graphics.getWidth() - raindrop.getBounds().getWidth()));

                // Set the raindrop's Y position just above the screen to re-enter the play area.
                raindrop.setY(Gdx.graphics.getHeight() + 150);

                // Activate the raindrop again for it to be updated and rendered.
                raindrop.setActive(true);

                // Log the new position for debugging purposes.
                System.out.println("Raindrop reset to position: " + raindrop.getX() + ", " + raindrop.getY());

                // Decrement a life since the raindrop reached the bottom of the screen.
                lifeManager.loseLife();
            }
        }
    }
}
