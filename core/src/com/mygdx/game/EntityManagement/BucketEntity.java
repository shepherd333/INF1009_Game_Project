package com.mygdx.game.EntityManagement;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class BucketEntity extends TextureObject {

    // Constructor
    public BucketEntity(Texture texture, float x, float y, double speed, SpriteBatch spriteBatch) {
        super(texture, x, y, speed, spriteBatch);
        // Any additional initialization specific to BucketEntity
    }

    @Override
    public void move() {
        // Player-controlled movement logic
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            x -= speed * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            x += speed * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            y += speed * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            y -= speed * Gdx.graphics.getDeltaTime();
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public float getWidth() {
        return this.width;
    }

    // If you need to override any other methods from TextureObject or Entity, you can do so here
}