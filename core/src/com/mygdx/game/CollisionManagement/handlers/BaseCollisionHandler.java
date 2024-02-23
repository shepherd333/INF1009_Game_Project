package com.mygdx.game.CollisionManagement.handlers;

import com.mygdx.game.EntityManagement.Entity;

// BaseCollisionHandler serves as an abstract base class for collision handling between entities.
// It implements the ICollisionHandler interface, ensuring all collision handlers provide
// a specific implementation for handling collisions.
public abstract class BaseCollisionHandler implements ICollisionHandler {
    // Protected members entity1 and entity2 represent the entities involved in a collision.
    // Being protected allows subclass access for specific collision logic.
    protected Entity entity1;
    protected Entity entity2;

    // Constructor initializes a new instance of the collision handler with two entities.
    // These entities are the subjects between which collisions are to be detected and handled.
    public BaseCollisionHandler(Entity entity1, Entity entity2){
        this.entity1 = entity1;
        this.entity2 = entity2;
    }

    // Abstract method handleCollision must be implemented by subclasses.
    // This method will contain the specific logic to be executed when a collision between
    // entity1 and entity2 is detected, such as applying damage, bouncing off, collecting items, etc.
    @Override
    public abstract void handleCollision();
}
