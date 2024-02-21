package com.mygdx.game.CollisionManagement.handlers;

import com.mygdx.game.EntityManagement.Entity;
import com.mygdx.game.EntityManagement.BucketEntity;
import com.mygdx.game.EntityManagement.RaindropEntity;

public class CollisionHandler {
    private Entity entity1;
    private Entity entity2;

    public CollisionHandler(Entity entity1, Entity entity2) {
        this.entity1 = entity1;
        this.entity2 = entity2;
    }

    public void handleCollision() {
        if (entity1.getBounds().overlaps(entity2.getBounds())) {
            // Delegate the handling of the collision to the appropriate handler
            ICollisionHandler handler = createCollisionHandler(entity1, entity2);
            if (handler != null) {
                handler.handleCollision();
            }
        }
    }

    private ICollisionHandler createCollisionHandler(Entity entity1, Entity entity2) {
        // Create the appropriate collision handler based on the types of the entities
        if (entity1 instanceof RaindropEntity && entity2 instanceof BucketEntity) {
            return new CollectCollisionHandler(entity1, entity2);
        } else if (entity1 instanceof BucketEntity && entity2 instanceof RaindropEntity) {
            return new CollectCollisionHandler(entity2, entity1);
        } else {
            // Return null or some default handler for other types of entities
            return null;
        }
    }
}