package com.mygdx.game.EntityManagement;

// Import necessary LibGDX classes and other game-specific classes.
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.CollisionManagement.handlers.CollectCollisionHandler;
import com.mygdx.game.Lifecycle.LifeManager;

import java.util.ArrayList;
import java.util.List;

// Class responsible for managing all entities in the game.
public class EntityManager {
    // List to keep track of all entities.
    private List<Entity> entityList;

    // Constructor initializes the entity list.
    public EntityManager() {
        this.entityList = new ArrayList<>();
    }

    // Adds an entity to the entity list.
    public void addEntity(Entity entity) {
        this.entityList.add(entity);
    }

    // Returns the list of all entities.
    public List<Entity> getEntities() {
        return this.entityList;
    }

    // Updates each active entity. Entities that are not active are skipped.
    public void updateEntities() {
        for (Entity entity : entityList) {
            if (entity.isActive()) {
                entity.update();
            }
        }
    }

    // Moves each active entity that implements the iMovable interface.
    // This method assumes that not all entities may be movable, thus the interface check.
    public void moveEntities() {
        for (Entity entity : entityList) {
            if (entity.isActive() && entity instanceof iMovable) {
                ((iMovable)entity).move();
            }
        }
    }

    // Draws each active entity using either a SpriteBatch or ShapeRenderer, depending on the entity's needs.
    public void draw(SpriteBatch batch, ShapeRenderer shapeRenderer) {
        for (Entity entity : entityList) {
            if (entity.isActive()) {
                entity.draw(batch, shapeRenderer);
            }
        }
    }

    // Removes an entity from the entity list.
    public void removeEntity(Entity entity) {
        this.entityList.remove(entity);
    }
}
