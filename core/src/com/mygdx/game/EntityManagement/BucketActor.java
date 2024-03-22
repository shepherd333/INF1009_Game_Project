package com.mygdx.game.EntityManagement;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class BucketActor extends CollidableActor {
    private Texture textureLeft;
    private Texture textureRight;
    private Texture textureUp;
    private Texture textureDown;
    private Sprite currentSprite;
    private float speed;

    private Texture texture;
    private String possessionValue;

    // Constructor
    public BucketActor(float x, float y, float speed) {
        this.speed = speed;
        this.setPosition(x, y);
        textureLeft = new Texture(Gdx.files.internal("WalleLeft.png"));
        textureRight = new Texture(Gdx.files.internal("WalleRight.png"));
        textureUp = new Texture(Gdx.files.internal("WalleBack.png"));
        textureDown = new Texture(Gdx.files.internal("WalleDown.png"));
        // Set the initial texture, for example, facing up
        currentSprite = new Sprite(textureDown);
        currentSprite.setSize(125,125);
        this.setSize(currentSprite.getWidth(), currentSprite.getHeight());
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
    public void changeDirection(Direction direction) {
        switch (direction) {
            case LEFT:
                currentSprite.setTexture(textureLeft);
                break;
            case RIGHT:
                currentSprite.setTexture(textureRight);
                break;
            case UP:
                currentSprite.setTexture(textureUp);
                break;
            case DOWN:
                currentSprite.setTexture(textureDown);
                break;
        }
        // Update the size of the actor to match the new sprite's size
        this.setSize(currentSprite.getWidth(), currentSprite.getHeight());
    }
    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(currentSprite, getX(), getY(), getWidth(), getHeight());
    }


    // Provides a bounding box for the bucket, useful for collision detection.
    public Rectangle getBounds() {
        Rectangle bounds = new Rectangle(getX(), getY(), getWidth(), getHeight());
        Gdx.app.log("BucketActor", "Bounds: " + bounds.toString());
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
        textureLeft.dispose();
        textureRight.dispose();
        textureUp.dispose();
        textureDown.dispose();
    }

    public float getSpeed() {
        return speed;
    }

    public void setPossessionValue(String value) {
        this.possessionValue = value;
    }

    public String getPossessionValue() {
        return possessionValue;
    }
    public enum Direction {
        LEFT, RIGHT, UP, DOWN
    }

}