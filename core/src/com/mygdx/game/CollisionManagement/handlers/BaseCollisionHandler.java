package com.mygdx.game.CollisionManagement.handlers;

import com.mygdx.game.EntityManagement.Entity;
public abstract class BaseCollisionHandler implements ICollisionHandler {
    protected Entity entity1;
    protected Entity entity2;

    public BaseCollisionHandler(Entity entity1, Entity entity2){
        this.entity1 = entity1;
        this.entity2 = entity2;
    }
    @Override
    public abstract void handleCollision();
}
