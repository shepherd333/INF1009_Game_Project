package GameEngine.EntityManagement;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.math.Rectangle;

// Entity now extends Actor, integrating with LibGDX's scene graph.
public abstract class Entity extends Actor {
    // Movement speed of the entity.
    protected double speed;

    // Constructor to initialize an entity with its position and speed.
    public Entity(float x, float y, double speed) {
        this.setPosition(x, y); // Use Actor's position management.
        this.speed = speed;
        this.setSize(0, 0); // Initialize with size 0,0; override in subclasses where necessary.
    }

    // Override Actor's act method to update the entity's state.
    @Override
    public void act(float delta) {
        super.act(delta);
        // Implement movement or other update logic here.
        // You can use speed and delta to calculate the movement.
    }

    // Abstract method for drawing the entity. Inherited from Actor.
    @Override
    public abstract void draw(Batch batch, float parentAlpha);

    // Method to get the bounding box of the entity for collision detection, etc.
    public Rectangle getBounds() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }

    // Speed management.
    public double getSpeed() { return speed; }
    public void setSpeed(double speed) { this.speed = speed; }

    // The visibility property of Actor can be used similarly to the 'active' property.
    // If an entity is not visible (or "active"), it could be skipped in game logic updates and rendering.
}
