package com.mygdx.game.EntityManagement;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public abstract class TextureObject extends Entity implements iMovable {

    protected Texture texture;
    protected float x, y, width, height;
    protected double speed;

    // Constructor without AI control parameter
    public TextureObject(Texture texture, float x, float y, double speed, SpriteBatch spriteBatch) {
        super(x, y, speed); // Ensure this matches Entity's constructor
        this.texture = texture;
        this.speed = speed;
        this.width = texture.getWidth(); // Initialize with original size
        this.height = texture.getHeight();
    }

    @Override
    public void update() {
        System.out.println("texture");
    }

    @Override
    public void draw(SpriteBatch batch, ShapeRenderer shapeRenderer) {
        if (batch != null) {
            batch.draw(texture, x, y, width, height);
        }
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    @Override
    public abstract void move(); // Make this method abstract

    // Implement getters and setters required by the Entity interface
    @Override
    public float getX() { return (float) x; }

    @Override
    public void setX(float x) { this.x = x; }

    @Override
    public float getY() { return (float) y; }

    @Override
    public void setY(float y) { this.y = y; }

    @Override
    public double getSpeed() { return (double) speed; }

    @Override
    public void setSpeed(double speed) { this.speed = speed; }

    // Additional methods specific to TextureObject
    public void dispose() {
        if (texture != null) texture.dispose();
    }

    public void setWidth(float width) { this.width = width; }
    public void setHeight(float height) { this.height = height; }

    // Assuming TextureObject doesn't use ShapeRenderer, no implementation needed for ShapeRenderer-specific drawing
}