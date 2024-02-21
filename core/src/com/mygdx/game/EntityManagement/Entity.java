package com.mygdx.game.EntityManagement;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public abstract class Entity {
    protected float x, y; // Common properties
    protected double speed;
    private boolean active = true; // New field

    public Entity(float x, float y, double speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;
    }

    // Abstract method for updating entity state
    public abstract void update();

    // Abstract method for drawing the entity
    public abstract void draw(SpriteBatch batch, ShapeRenderer shapeRenderer);

    // Abstract method for getting bounding rectangle
    public abstract Rectangle getBounds();

    // Getters and setters
    public float getX() { return x; }
    public void setX(float x) { this.x = x; }

    public float getY() { return y; }
    public void setY(float y) { this.y = y; }

    public double getSpeed() { return speed; }
    public void setSpeed(double speed) { this.speed = speed; }

    // getter and setter for the 'active' field in CollectCollisionHandler
    public boolean isActive() { return active; }
    public void setActive(boolean active) {
        this.active = active;
        System.out.println("Set active called. New state: " + this.active);
    }
}