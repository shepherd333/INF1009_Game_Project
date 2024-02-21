package com.mygdx.game.EntityManagement;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.InputManagement.InputManager;

public class BucketEntity extends TextureObject {
    private InputManager inputManager;

    // Constructor
    public BucketEntity(Texture texture, float x, float y, double speed, SpriteBatch spriteBatch) {
        super(texture, x, y, speed, spriteBatch);
        // Any additional initialization specific to BucketEntity
    }

    @Override
    public void move() {
        // Player-controlled movement logic
        inputManager.handlePlayerInput(this);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public float getWidth() {
        return this.width;
    }

    // If you need to override any other methods from TextureObject or Entity, you can do so here
}