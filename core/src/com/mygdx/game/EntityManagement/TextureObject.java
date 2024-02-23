package com.mygdx.game.EntityManagement;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

// TextureObject is an abstract class that extends Entity and implements iMovable,
// meant to represent entities in the game that have a texture and can move.
public abstract class TextureObject extends Entity implements iMovable {

    // Texture associated with this object.
    protected Texture texture;
    // Position and size of the texture object.
    protected float x, y, width, height;
    // Speed at which the object moves.
    protected double speed;

    // Constructor to create a TextureObject with a given texture, position, and speed.
    public TextureObject(Texture texture, float x, float y, double speed, SpriteBatch spriteBatch) {
        super(x, y, speed); // Call to the superclass (Entity) constructor.
        this.texture = texture; // Assign the texture.
        this.speed = speed; // Set the speed.
        // Set the width and height based on the texture's size.
        this.width = texture.getWidth();
        this.height = texture.getHeight();
    }

    @Override
    public void update() {
        // Placeholder for update logic. Actual update logic to be implemented in subclasses.
        System.out.println("texture");
    }

    @Override
    public void draw(SpriteBatch batch, ShapeRenderer shapeRenderer) {
        // Draw the texture at its position with its size using the SpriteBatch.
        // ShapeRenderer is not used here, but it's available if needed.
        if (batch != null) {
            batch.draw(texture, x, y, width, height);
        }
    }

    @Override
    public Rectangle getBounds() {
        // Returns a Rectangle that represents this object's bounding box, useful for collision detection.
        return new Rectangle(x, y, width, height);
    }

    @Override
    public abstract void move(); // Abstract method to be implemented by subclasses to define movement behavior.

    // Getters and setters for the object's position, speed, and size.

    @Override
    public float getX() { return x; }

    @Override
    public void setX(float x) { this.x = x; }

    @Override
    public float getY() { return y; }

    @Override
    public void setY(float y) { this.y = y; }

    @Override
    public double getSpeed() { return speed; }

    @Override
    public void setSpeed(double speed) { this.speed = speed; }

    public void dispose() {
        // Dispose of the texture when the object is no longer needed to free up resources.
        if (texture != null) texture.dispose();
    }

    public void setWidth(float width) { this.width = width; }
    public void setHeight(float height) { this.height = height; }

    // Note: Since TextureObject doesn't use ShapeRenderer for drawing, no implementation is needed for ShapeRenderer-specific drawing.
}
