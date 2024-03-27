package com.mygdx.game.EntityManagement.Foundation;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.math.Rectangle;

public class CollidableActor extends Actor {
    // Define a bounding box for collision detection
    private Rectangle bounds;
    private boolean collected;

    public CollidableActor() {
        super();
        bounds = new Rectangle();
        this.collected = false;
    }

    // Update the bounding box position and size to match the actor's
    @Override
    public void act(float delta) {
        super.act(delta);
        bounds.setPosition(getX(), getY());
        bounds.setSize(getWidth(), getHeight());
    }

    // Provide a method to get the bounding box for collision detection
    public Rectangle getBounds() {
        return bounds;
    }

    public boolean isCollected() {
        return collected;
    }

    public void setCollected(boolean collected) {
        this.collected = collected;
    }
}