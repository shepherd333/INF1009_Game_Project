package com.mygdx.game.EntityManagement;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.CollisionManagement.handlers.CollectCollisionHandler;
import com.mygdx.game.Lifecycle.HighScoreManager;

import java.util.ArrayList;
import java.util.List;

public class EntityManager {
    private List<Entity> entityList;
    private RaindropEntity raindrop;
    public CollectCollisionHandler CCH;
    public HighScoreManager highScoreManager = HighScoreManager.getInstance();
    private int points;

    public EntityManager() {
        this.entityList = new ArrayList<>();
    }

    public void addEntity(Entity entity) {
        this.entityList.add(entity);
    }





    public void resetEntityToTop(Entity entity) {
        // Corrected to use the passed entity and not the raindrop field

        float newX = MathUtils.random(0, Gdx.graphics.getWidth() - entity.getX());
        entity.setActive(true);
        entity.setX(newX);
        entity.setY(Gdx.graphics.getHeight());
        //System.out.println("Entity reset to position: " + entity.getX() + ", " + entity.getY());
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
                                System.out.println("Collision detected. Scheduling reset for entity.");
                                highScoreManager.addToCurrentScore(2000);
                                drop.setActive(false);

                                // Schedule the reset task after a delay on the main LibGDX thread
                                Timer.schedule(new Timer.Task() {
                                    @Override
                                    public void run() {
                                        // Post the task to be run on the main LibGDX thread
                                        Gdx.app.postRunnable(new Runnable() {
                                            @Override
                                            public void run() {

                                                resetEntityToTop(drop);
                                            }
                                        });
                                    }
                                }, 1f); // Delay in seconds
                            } else {
                                // Print debug information
                                //System.out.println("No collision. Drop bounds: " + drop.getBounds() + ", Bucket bounds: " + bucket.getBounds());
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