package com.mygdx.game.EntityManagement;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.CollisionManagement.handlers.CollectCollisionHandler;
import com.mygdx.game.Lifecycle.HighScoreManager;
import com.mygdx.game.Lifecycle.LifeManager;

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

    public List<Entity> getEntities() {
        return this.entityList;
    }

    public void updateEntities() {
        for (Entity entity : entityList) {
            if (entity.isActive()) {
                entity.update();
            }
        }
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

