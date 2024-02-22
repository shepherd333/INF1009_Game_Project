package com.mygdx.game.CollisionManagement.handlers;

import com.mygdx.game.EntityManagement.Entity;
import com.mygdx.game.EntityManagement.BucketEntity;
import com.mygdx.game.EntityManagement.RaindropEntity;

// CollisionHandler is responsible for detecting collisions between two entities and delegating
// the handling of these collisions to the appropriate specific collision handler.
public class CollisionHandler {
    // The two entities involved in the potential collision.
    private Entity entity1;
    private Entity entity2;

    // Constructor to initialize a new CollisionHandler instance with two entities.
    public CollisionHandler(Entity entity1, Entity entity2) {
        this.entity1 = entity1;
        this.entity2 = entity2;
    }

    // Checks for collision between entity1 and entity2 and handles it if it occurs.
    public void handleCollision() {
        // Check if the bounding rectangles of the two entities overlap, indicating a collision.
        if (entity1.getBounds().overlaps(entity2.getBounds())) {
            // If a collision is detected, delegate the handling to a specific collision handler.
            ICollisionHandler handler = createCollisionHandler(entity1, entity2);
            // If a valid handler is returned, call its handleCollision method to process the collision.
            if (handler != null) {
                handler.handleCollision();
            }
        }
    }

    // Determines the appropriate collision handler based on the types of entities involved in the collision.
    private ICollisionHandler createCollisionHandler(Entity entity1, Entity entity2) {
        // Specific case where a RaindropEntity collides with a BucketEntity.
        // The order of entity1 and entity2 matters for certain collision handlers.
        if (entity1 instanceof RaindropEntity && entity2 instanceof BucketEntity) {
            return new CollectCollisionHandler(entity1, entity2);
        } else if (entity1 instanceof BucketEntity && entity2 instanceof RaindropEntity) {
            // Handle the case where the entities are in the opposite order.
            return new CollectCollisionHandler(entity2, entity1);
        } else {
            // For other types of entities or collisions not specifically handled, return null.
            // You could also return a default handler that does nothing or logs unhandled collisions.
            return null;
        }
    }
}
