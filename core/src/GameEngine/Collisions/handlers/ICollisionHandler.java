package GameEngine.Collisions.handlers;

/**
 * ICollisionHandler is an interface that defines a contract for classes that handle collisions between actors in a game.
 * This includes:
 * - Providing a method to handle the collision between actors.
 */
public interface ICollisionHandler {
    // Method to handle the collision between actors.
    void handleCollision();
}
