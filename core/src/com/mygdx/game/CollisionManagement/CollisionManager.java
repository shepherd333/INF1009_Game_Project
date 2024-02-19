package com.mygdx.game.CollisionManagement;

import com.mygdx.game.EntityManagement.Entity;
import com.mygdx.game.CollisionManagement.handlers.CollisionHandler;

import java.util.List;

public class CollisionManager {
    private List<Entity> entities;

    public CollisionManager(List<Entity> entities) {
        this.entities = entities;
    }

    public void handleCollisions() {
        for (int i = 0; i < entities.size(); i++) {
            for (int j = i + 1; j < entities.size(); j++) {
                CollisionHandler collisionHandler = new CollisionHandler(entities.get(i), entities.get(j));
                collisionHandler.handleCollision();
            }
        }
    }
}