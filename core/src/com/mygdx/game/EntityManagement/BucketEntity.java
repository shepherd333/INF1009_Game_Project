package com.mygdx.game.EntityManagement;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.InputManagement.InputManager;

// BucketEntity extends TextureObject, representing a player-controlled bucket in the game.
public class BucketEntity extends TextureObject {
    private InputManager inputManager; // Reference to InputManager for handling player inputs.
    private Viewport viewport;

    // Constructor
    public BucketEntity(Texture texture, float x, float y, double speed, SpriteBatch spriteBatch,Viewport viewport) {
        super(texture, x, y, speed, spriteBatch); // Calls the superclass constructor to initialize common fields.
        // Any additional initialization specific to BucketEntity can be done here.
        // Note: inputManager needs to be initialized or passed to the BucketEntity to be functional.
        this.viewport = viewport; // Initialize the viewport reference
    }

    @Override
    public void move() {
        // Player-controlled movement logic. This method should define how the bucket moves each frame based on player input.
        // To function, inputManager must be properly initialized and set to this BucketEntity.
        inputManager.handlePlayerInput(this);
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        // Boundary checks
        if (this.x < 0) {
            this.x = 0;
        } else if (this.x + this.width > worldWidth) {
            this.x = worldWidth - this.width;
        }

        if (this.y < 0) {
            this.y = 0;
        } else if (this.y + this.height > worldHeight) {
            this.y = worldHeight - this.height;
        }
    }

    @Override
    public Rectangle getBounds() {
        // Provides a bounding box for the bucket, useful for collision detection.
        // This returns a new Rectangle object based on the bucket's current position and size.
        return new Rectangle(x, y, width, height);
    }


    public float getWidth() {
        // Returns the width of the bucket. This could be used for collision detection or positioning logic.
        return this.width;
    }

    public float getHeight() {
        return this.height; // Assuming 'height' is a member variable representing the height of the bucket.
    }

    // If you need to override any other methods from TextureObject or Entity, you can do so here.
    // For example, you might override the update method if the bucket has specific logic that needs to run each frame.
}