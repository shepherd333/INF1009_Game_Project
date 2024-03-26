package com.mygdx.game.AIManagement;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.EntityManagement.PaperItemsActor;
//import com.mygdx.game.Lifecycle.LifeSystem.LifeManager;

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
    public void moveRaindrop(PaperItemsActor paperitems, float deltaTime) {
        // Check if the raindrop's speed is greater than 0 to ensure movement.
        if (paperitems.getSpeed() > 0) {
            // Move the raindrop downward, considering deltaTime for frame-rate independence.
            paperitems.setY(paperitems.getY() - paperitems.getSpeed() * deltaTime);

            // Check if the raindrop has reached the bottom of the screen.
            if (paperitems.getY() + paperitems.getHeight() < 0) {
                // Reset the raindrop's position to the top with a new random X coordinate.
                float newX = MathUtils.random(0, stage.getViewport().getWorldWidth() - paperitems.getWidth());
                float newY = stage.getViewport().getWorldHeight(); // Reset to just above the viewport.

                // Update raindrop's position.
                paperitems.setPosition(newX, newY);

                // Log the new position for debugging purposes.
                System.out.println("Paperitems reset to position: " + newX + ", " + newY);

//                // Decrement a life since the raindrop reached the bottom of the screen.
//                lifeManager.loseLife();
            }
        }
    }

    // Add other AI management methods as needed for different entity types or behaviors.
}
