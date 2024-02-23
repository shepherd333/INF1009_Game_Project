package com.mygdx.game.EntityManagement;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

// Entity is an abstract base class for all game entities.
// It provides the common structure and functionality needed for game objects.
public abstract class Entity {
    // Positional attributes of the entity.
    protected float x, y;
    // Movement speed of the entity.
    protected double speed;
    // Active state of the entity; inactive entities may not need to be updated or drawn.
    private boolean active = true;

    // Constructor to initialize an entity with its position and speed.
    public Entity(float x, float y, double speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;
    }

    // Abstract method that must be implemented by subclasses to update the entity's state.
    // This might include moving the entity, checking for collisions, etc.
    public abstract void update();

    // Abstract method for drawing the entity on the screen.
    // This allows for flexibility in how different entities are visually represented.
    public abstract void draw(SpriteBatch batch, ShapeRenderer shapeRenderer);

    // Abstract method to get the bounding box of the entity.
    // This is crucial for collision detection among entities.
    public abstract Rectangle getBounds();

    // Getter for the entity's X position.
    public float getX() { return x; }

    // Setter for the entity's X position.
    public void setX(float x) { this.x = x; }

    // Getter for the entity's Y position.
    public float getY() { return y; }

    // Setter for the entity's Y position.
    public void setY(float y) { this.y = y; }

    // Getter for the entity's speed.
    public double getSpeed() { return speed; }

    // Getter for the entity's width.
    public float getWidth() {
        return getBounds().width;
    }

    // Setter for the entity's speed.
    public void setSpeed(double speed) { this.speed = speed; }

    // Getter for the entity's active state.
    // The active state determines whether the entity should be updated and drawn.
    public boolean isActive() { return active; }

    // Setter for the entity's active state.
    // Deactivating an entity can effectively remove it from the game without deleting it.
    public void setActive(boolean active) {
        this.active = active;
        // Print statement for debugging purposes, indicating the entity's new active state.
        System.out.println("Set active called. New state: " + this.active);
    }
}
