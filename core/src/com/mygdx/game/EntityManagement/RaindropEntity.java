package com.mygdx.game.EntityManagement;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.AIManagement.AIManager;

public class RaindropEntity extends TextureObject {
    private AIManager aiManager;

    // Constructor
    public RaindropEntity(Texture texture, float x, float y, double speed, SpriteBatch spriteBatch, float bucketX, float bucketWidth) {
        super(texture, x, y, speed, spriteBatch);
        // Ensure the raindrop does not overlap with the bucket initially
        while (this.x >= bucketX && this.x <= bucketX + bucketWidth) {
            this.x = MathUtils.random(0, Gdx.graphics.getWidth() - width);
        }
        //System.out.println("Raindrop created at position: " + this.x + ", " + this.y);
    }

    @Override
    public void move() {
        aiManager.moveRaindrop(this);
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