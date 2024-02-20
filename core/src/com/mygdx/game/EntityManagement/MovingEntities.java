package com.mygdx.game.EntityManagement;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
public class MovingEntities extends Entity implements MovableEntities {

    private Texture texture;
    private float x;
    public float y;
    private float speed;
    private boolean isAIControlled;


    public MovingEntities(String n, float x, float y, float speed, boolean isAIControlled) {
        super(x, y, speed);
        this.texture = new Texture(Gdx.files.internal(n));
        this.isAIControlled = isAIControlled;
    }

    public void drawTexture(SpriteBatch batch) {
        if (texture != null) {

            batch.draw(texture, x, y, texture.getWidth(), texture.getHeight());
        }
    }

    public Texture getTexture() {
        return texture;
    }
    public void setTexture(Texture texture) {
        this.texture = texture;
    }
    public float getX() {
        return x;
    }
    public void setX(float x) {
        this.x = x;
    }
    public float getY() {
        return y;
    }
    public void setY(float y) {
        this.y = y;
    }
    public float getSpeed() {
        return speed;
    }
    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public boolean isAIControlled() {
        return isAIControlled;
    }

    @Override
    public void update() {
    }

    @Override
    public void movement() {

    }

    @Override
    public void move() {
        // Implement move method for iMovable interface
        if (isAIControlled) {
            // AI controlled movement logic
        } else {
            // Player controlled movement logic
        }
    }

    void dispose() {
        if (texture != null) {
            texture.dispose();
        }
    }

}