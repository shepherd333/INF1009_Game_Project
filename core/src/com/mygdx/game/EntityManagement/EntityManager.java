package com.mygdx.game.EntityManagement;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import java.util.ArrayList;
import java.util.List;

public class EntityManager {
    private List<Entity> entityList;

    public EntityManager() {
        this.entityList = new ArrayList<>();
    }

    public void addEntity(Entity entity) {
        this.entityList.add(entity);
    }

    public void updateEntities() {
        List<Entity> entitiesToRemove = new ArrayList<>();
        for (Entity entity : entityList) {
            if (entity.isActive()) {
                entity.update();
                if (entity instanceof RaindropEntity) {
                    RaindropEntity drop = (RaindropEntity) entity;
                    for (Entity otherEntity : entityList) {
                        if (otherEntity instanceof BucketEntity) {
                            BucketEntity bucket = (BucketEntity) otherEntity;
                            if (drop.getBounds().overlaps(bucket.getBounds())) {
                                // Handle collision
                                System.out.println("Collision detected! Deactivating drop.");
                                drop.setActive(false); // Make the drop disappear
                                entitiesToRemove.add(drop);
                            } else {
                                // Print debug information
                                System.out.println("No collision. Drop bounds: " + drop.getBounds() + ", Bucket bounds: " + bucket.getBounds());
                            }
                        }
                    }
                }
            }
        }
        entityList.removeAll(entitiesToRemove);
    }

    public void moveEntities() {
        for (Entity entity : entityList) {
            if (entity.isActive() && entity instanceof iMovable) {
                ((iMovable)entity).move();
            }
        }
    }

    public void draw(SpriteBatch batch, ShapeRenderer shapeRenderer) {
        for (Entity entity : entityList) {
            if (entity.isActive()) {
                entity.draw(batch, shapeRenderer);
            }
        }
    }

    public void removeEntity(Entity entity) {
        this.entityList.remove(entity);
    }
}