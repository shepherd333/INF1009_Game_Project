package com.mygdx.game.CollisionManagement.handlers;

import com.mygdx.game.EntityManagement.Entity;

public class CollectCollisionHandler extends BaseCollisionHandler {
    public CollectCollisionHandler(Entity entity1, Entity entity2) {
        super(entity1, entity2);
    }

    @Override
    public void handleCollision() {
        // Handle the collision by deactivating entity1
        System.out.println("Collision detected. Deactivating entity1.");
        entity1.setActive(false);
    }
}