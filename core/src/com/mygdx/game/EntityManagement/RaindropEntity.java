package com.mygdx.game.EntityManagement;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.AIManagement.AIManager;

// RaindropEntity extends TextureObject to represent a raindrop in the game.
public class RaindropEntity extends TextureObject {
    private AIManager aiManager; // Reference to AIManager to control the raindrop's movement.
    private Viewport viewport;
    // Constructor
    public RaindropEntity(Texture texture, float x, float y, double speed, SpriteBatch spriteBatch, float bucketX, float bucketWidth) {
        super(texture, x, y, speed, spriteBatch); // Calls the constructor of the superclass, TextureObject.

        // Ensure the raindrop does not spawn overlapping with the bucket by adjusting its initial position.
        while (this.x >= bucketX && this.x <= bucketX + bucketWidth) {
            this.x = MathUtils.random(0, Gdx.graphics.getWidth() - width); // Randomize x position if it's within the bucket area.
        }
        // Debug log for the initial creation of the raindrop, commented out for performance.
        //System.out.println("Raindrop created at position: " + this.x + ", " + this.y);
    }

    @Override
    public void move() {
        // Delegates the movement logic to the AIManager.
        // This method should define how the raindrop moves each frame.
        aiManager.moveRaindrop(this);
    }

    @Override
    public void draw(SpriteBatch batch, ShapeRenderer shapeRenderer) {
        // Uses the draw method from TextureObject and optionally adds additional drawing logic.
        // Currently, it only prints the position of the raindrop for debugging purposes.
        super.draw(batch, shapeRenderer); // Call the superclass method to actually draw the raindrop.
        System.out.println("Drawing raindrop at position: " + x + ", " + y);
    }

    @Override
    public Rectangle getBounds() {
        // Provides a bounding box for the raindrop, useful for collision detection.
        // This overrides the getBounds method from TextureObject if needed, or it can simply use the implementation from TextureObject.
        return new Rectangle(x, y, width, height);
    }

    // If there's a need to override any other methods from TextureObject or Entity, those overrides would go here.
    // For example, handling collisions or reacting to game events specific to the raindrop.
}
