package GameEngine.Collisions.handlers;

import com.badlogic.gdx.scenes.scene2d.Actor;

// Abstract base class for collision handlers.
public abstract class BaseCollisionHandler implements ICollisionHandler {
    protected Actor actor1;  // First actor involved in the collision
    protected Actor actor2;  // Second actor involved in the collision

    // Constructor to initialize the collision handler with the involved actors.
    public BaseCollisionHandler(Actor actor1, Actor actor2) {
        this.actor1 = actor1;
        this.actor2 = actor2;
    }

    // Abstract method to handle the collision.
    @Override
    public abstract void handleCollision();
}