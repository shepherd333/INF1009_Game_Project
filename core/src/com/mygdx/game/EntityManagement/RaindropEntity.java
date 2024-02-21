package com.mygdx.game.EntityManagement;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class RaindropEntity extends TextureObject {

    // Constructor
    public RaindropEntity(Texture texture, float x, float y, double speed, SpriteBatch spriteBatch, float bucketX, float bucketWidth) {
        super(texture, x, y, speed, spriteBatch);
        // Ensure the raindrop does not overlap with the bucket initially
        while (this.x >= bucketX && this.x <= bucketX + bucketWidth) {
            this.x = MathUtils.random(0, Gdx.graphics.getWidth() - width);
        }
        System.out.println("Raindrop created at position: " + this.x + ", " + this.y);
    }

    @Override
    public void move() {
        // AI-controlled movement logic
        // For drops, simply moving down could be the AI behavior
        if (this.speed > 0) {
            // Move the texture object downward based on its speed
            y -= speed;

            // Check if the texture object (drop) has reached the bottom of the screen
            if (y <= 0) {
                // Reset the position to the top with a new random X coordinate
                x = MathUtils.random(0, Gdx.graphics.getWidth() - width); // Ensure the new X is within screen bounds
                y = Gdx.graphics.getHeight();
                setActive(true); // Set the drop to active again
                System.out.println("Raindrop reset to position: " + x + ", " + y);
            }
        }
    }

    @Override
    public void draw(SpriteBatch batch, ShapeRenderer shapeRenderer) {
        super.draw(batch, shapeRenderer);
        System.out.println("Drawing raindrop at position: " + x + ", " + y);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    // If you need to override any other methods from TextureObject or Entity, you can do so here
}