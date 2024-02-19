package com.mygdx.game.EntityManagement;

import com.badlogic.gdx.math.Rectangle;

public class Entity {
    private Rectangle bounds;
    private boolean active;

    public Entity(float x, float y, float width, float height) {
        this.bounds = new Rectangle(x, y, width, height);
        this.active = true;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}