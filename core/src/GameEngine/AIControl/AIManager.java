package GameEngine.AIControl;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;

//import GameEngine.SimulationLifecycleManagement.LifeManager;
//
//public class AIManager {
//    // Reference to the game's stage to access viewport and actors.
//    private Stage stage;
//
//    // Singleton instance of the LifeManager to manage the game's life state.
////    private LifeManager lifeManager = LifeManager.getInstance();
//
//    // Constructor that takes the game's stage as a parameter.
//    public AIManager(Stage stage) {
//        this.stage = stage;
//    }
//
//    // Method to move a raindrop entity based on its speed and reset its position when it goes off-screen.
//    public void moveRaindrop(PaperItemsActor paperitems, float deltaTime) {
//        // Check if the raindrop's speed is greater than 0 to ensure movement.
//        if (paperitems.getSpeed() > 0) {
//            // Move the raindrop downward, considering deltaTime for frame-rate independence.
//            paperitems.setY(paperitems.getY() - paperitems.getSpeed() * deltaTime);
//
//            // Check if the raindrop has reached the bottom of the screen.
//            if (paperitems.getY() + paperitems.getHeight() < 0) {
//                // Reset the raindrop's position to the top with a new random X coordinate.
//                float newX = MathUtils.random(0, stage.getViewport().getWorldWidth() - paperitems.getWidth());
//                float newY = stage.getViewport().getWorldHeight(); // Reset to just above the viewport.
//
//                // Update raindrop's position.
//                paperitems.setPosition(newX, newY);
//
//                // Log the new position for debugging purposes.
//                System.out.println("Paperitems reset to position: " + newX + ", " + newY);
//
////                // Decrement a life since the raindrop reached the bottom of the screen.
////                lifeManager.loseLife();
//            }
//        }
//    }
//
//    // Add other AI management methods as needed for different entity types or behaviors.
//}
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class AIManager {
    private Stage stage;
    private Actor player; // The player actor that enemies will follow.

    public AIManager(Stage stage, Actor player) {
        this.stage = stage;
        this.player = player;
    }

    // Method to make an actor follow the player.
    public void updateFollower(Actor follower, float deltaTime, float speed) {
        if (follower == null || player == null) return; // Add this check
        Vector2 followerPos = new Vector2(follower.getX(), follower.getY());
        Vector2 playerPos = new Vector2(player.getX(), player.getY());

        // Calculate the direction vector from follower to player
        Vector2 direction = playerPos.sub(followerPos).nor();

        // Move the follower towards the player based on the speed
        followerPos.add(direction.scl(speed * deltaTime));

        // Update the follower's position
        follower.setPosition(followerPos.x, followerPos.y);
    }
}
