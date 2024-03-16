package com.mygdx.game.EntityManagement;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Touchable;

public class BucketEntity extends Actor {
    private Texture texture;
    private float speed; // Consider using Vector2 for speed if you need direction.

    // Constructor
    public BucketEntity(Texture texture, float x, float y, float speed) {
        this.texture = texture;
        this.speed = speed;
        this.setPosition(x, y);
        this.setSize(texture.getWidth(), texture.getHeight());
        setTouchable(Touchable.enabled);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        // Example movement logic: move the bucket based on speed. Adjust as necessary.
        // This is just a placeholder; actual movement logic will depend on your game's mechanics.
        // For example, you might update the bucket's position based on user input.

        // Boundary checks
        // Ensure the bucket doesn't move outside the left or right screen bounds
        if (getX() < 0) {
            setPosition(0, getY()); // Reset to left edge if out of bounds
        } else if (getX() + getWidth() > getStage().getViewport().getWorldWidth()) {
            setPosition(getStage().getViewport().getWorldWidth() - getWidth(), getY()); // Reset to right edge
        }

        // Similarly, for top and bottom boundaries, if needed:
        if (getY() < 0) {
            setPosition(getX(), 0); // Reset to bottom edge if out of bounds
        } else if (getY() + getHeight() > getStage().getViewport().getWorldHeight()) {
            setPosition(getX(), getStage().getViewport().getWorldHeight() - getHeight()); // Reset to top edge
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }


    // Provides a bounding box for the bucket, useful for collision detection.
    public Rectangle getBounds() {
        Rectangle bounds = new Rectangle(getX(), getY(), getWidth(), getHeight());
        Gdx.app.log("BucketEntity", "Bounds: " + bounds.toString());
        return bounds;
    }


    @Override
    public void setWidth(float width) {
        super.setWidth(width);
    }

    @Override
    public void setHeight(float height) {
        super.setHeight(height);
    }

    public void dispose() {
        // Dispose of the texture when the object is no longer needed to free up resources.
        if (texture != null) texture.dispose();
    }

    public float getSpeed() {
        return speed;
    }
}
