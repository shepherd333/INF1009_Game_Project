package com.mygdx.game.EntityManagement;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Touchable;

public class RaindropEntity extends Actor {
    private Texture texture;
    private float speed;
    public float bucketX; // Added
    public float bucketWidth; // Added

    public RaindropEntity(Texture texture, float speed, float bucketX, float bucketWidth) {
        this.texture = texture;
        this.speed = speed;
        this.bucketX = bucketX; // Store the X position
        this.bucketWidth = bucketWidth; // Store the width
        this.setSize(texture.getWidth(), texture.getHeight());
        setTouchable(Touchable.enabled);
    }

    public void resetPosition(float bucketX, float bucketWidth) {
        // Use the stage's viewport to get the world width for positioning.
        float stageWidth = this.getStage().getViewport().getWorldWidth();
        float newX;

        // Ensure the new X position is not within the bucket area.
        do {
            newX = MathUtils.random(0, stageWidth - this.getWidth());
        } while (newX >= bucketX && newX <= bucketX + bucketWidth);

        this.setPosition(newX, this.getStage().getViewport().getWorldHeight()); // Position at top of the viewport
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        // Move the raindrop down by its speed adjusted for delta time.
        this.setY(this.getY() - speed * delta);

        // If the raindrop moves off the bottom of the screen, reset its position.
        if (this.getY() + this.getHeight() < 0) {
            Gdx.app.log("Raindrop", "A raindrop hit the bottom and will be removed. Current Y position: " + this.getY());
            // Here you could either reset the raindrop's position or remove it from the stage.
            this.remove(); // For example, to remove the raindrop
            // Or, to reset position, you might call resetPosition(bucketX, bucketWidth), with proper values.
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }

    public Rectangle getBounds() {
        Rectangle bounds = new Rectangle(getX(), getY(), getWidth(), getHeight());
        // Log the current bounds
        Gdx.app.log("RaindropEntity", "Bounds: " + bounds.toString());
        return bounds;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void dispose() {
        if (texture != null) {
            texture.dispose();
        }
    }
}
