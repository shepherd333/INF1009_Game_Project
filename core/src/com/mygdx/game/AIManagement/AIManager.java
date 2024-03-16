package com.mygdx.game.AIManagement;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.EntityManagement.RaindropEntity;
//import com.mygdx.game.Lifecycle.LifeManager;

public class AIManager {
    // Reference to the game's stage to access viewport and actors.
    private Stage stage;

    // Singleton instance of the LifeManager to manage the game's life state.
//    private LifeManager lifeManager = LifeManager.getInstance();

    // Constructor that takes the game's stage as a parameter.
    public AIManager(Stage stage) {
        this.stage = stage;
    }

    // Method to move a raindrop entity based on its speed and reset its position when it goes off-screen.
    public void moveRaindrop(RaindropEntity raindrop, float deltaTime) {
        // Check if the raindrop's speed is greater than 0 to ensure movement.
        if (raindrop.getSpeed() > 0) {
            // Move the raindrop downward, considering deltaTime for frame-rate independence.
            raindrop.setY(raindrop.getY() - raindrop.getSpeed() * deltaTime);

            // Check if the raindrop has reached the bottom of the screen.
            if (raindrop.getY() + raindrop.getHeight() < 0) {
                // Reset the raindrop's position to the top with a new random X coordinate.
                float newX = MathUtils.random(0, stage.getViewport().getWorldWidth() - raindrop.getWidth());
                float newY = stage.getViewport().getWorldHeight(); // Reset to just above the viewport.

                // Update raindrop's position.
                raindrop.setPosition(newX, newY);

                // Log the new position for debugging purposes.
                System.out.println("Raindrop reset to position: " + newX + ", " + newY);

//                // Decrement a life since the raindrop reached the bottom of the screen.
//                lifeManager.loseLife();
            }
        }
    }

    // Add other AI management methods as needed for different entity types or behaviors.
}
