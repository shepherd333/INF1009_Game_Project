package GameEngine.EntityManagement;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.math.Rectangle;

/**
 * CollidableActor is a class that extends Actor and is used for actors that can collide with other actors in a game.
 * This includes:
 * - Defining a bounding box for collision detection.
 * - Updating the bounding box position and size to match the actor's.
 * - Providing a method to get the bounding box for collision detection.
 */
public class CollidableActor extends Actor {
    // Define a bounding box for collision detection
    private Rectangle bounds;
    private boolean collected;

    public CollidableActor() {
        super();
        bounds = new Rectangle();
        this.collected = false;
    }

    // Update the bounding box position and size to match the actor's
    @Override
    public void act(float delta) {
        super.act(delta);
        bounds.setPosition(getX(), getY());
        bounds.setSize(getWidth(), getHeight());
    }

    // Provide a method to get the bounding box for collision detection
    public Rectangle getBounds() {
        return bounds;
    }
}