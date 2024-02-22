package com.mygdx.game.CollisionManagement;

import com.mygdx.game.EntityManagement.Entity;
import com.mygdx.game.CollisionManagement.handlers.CollisionHandler;

import java.util.List;

// CollisionManager is responsible for managing and processing collisions between entities in the game.
public class CollisionManager {
    // List of all entities in the game that might collide with each other.
    private List<Entity> entities;

    // Constructor initializes the CollisionManager with a list of entities.
    // This list should contain all entities that need to be checked for collisions.
    public CollisionManager(List<Entity> entities) {
        this.entities = entities;
    }

    // Checks for collisions between all pairs of entities in the list.
    // Each pair of entities is only checked once to avoid redundant checks.
    public void handleCollisions() {
        // Iterate over all entities using a nested loop to check each pair for collisions.
        for (int i = 0; i < entities.size(); i++) {
            for (int j = i + 1; j < entities.size(); j++) {
                // Create a new CollisionHandler for each pair of entities.
                CollisionHandler collisionHandler = new CollisionHandler(entities.get(i), entities.get(j));

                // Call the handleCollision method of the CollisionHandler.
                // This method will check if the two entities are colliding and handle the collision appropriately
                // based on the specific types of entities involved.
                collisionHandler.handleCollision();
            }
        }
    }
}
