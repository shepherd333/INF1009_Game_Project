package GameEngine.Collisions.handlers;

import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * BaseCollisionHandler is an abstract class that implements ICollisionHandler and serves as a base for collision handlers in a game.
 * This includes:
 * - Holding references to the two actors involved in the collision.
 * - Providing a constructor to initialize the collision handler with the involved actors.
 * - Providing an abstract method to handle the collision, which must be overridden by subclasses.
 */
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