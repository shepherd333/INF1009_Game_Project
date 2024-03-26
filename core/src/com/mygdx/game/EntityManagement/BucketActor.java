package com.mygdx.game.EntityManagement;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
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
    private boolean itemPickedUp; // Flag to check if an item has been picked up
    private boolean holdingItem = false;
    private CollidableActor heldItem;

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
        // Update logic to prevent picking up items if already picked up
        if (!holdingItem) {
            // Get current bucket position
            float newX = getX();
            float newY = getY();

            // Update position based on input
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                newX -= speed * delta;
                changeDirection(Direction.LEFT);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                newX += speed * delta;
                changeDirection(Direction.RIGHT);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                newY += speed * delta;
                changeDirection(Direction.UP);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                newY -= speed * delta;
                changeDirection(Direction.DOWN);
            }

            // Clamp position within screen bounds
            newX = MathUtils.clamp(newX, 0, getStage().getViewport().getWorldWidth() - getWidth());
            newY = MathUtils.clamp(newY, 0, getStage().getViewport().getWorldHeight() - getHeight());

            // Update bucket position
            setPosition(newX, newY);
        } else {
            // If item is picked up, clamp the bucket's position within screen bounds
            float clampedX = MathUtils.clamp(getX(), 0, getStage().getViewport().getWorldWidth() - getWidth());
            float clampedY = MathUtils.clamp(getY(), 0, getStage().getViewport().getWorldHeight() - getHeight());
            setPosition(clampedX, clampedY);
        }

        // Handle dropping item when spacebar is pressed
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && holdingItem) {
            dropItem();
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

    public boolean isItemPickedUp() {
        return itemPickedUp;
    }

    public void setItemPickedUp(boolean itemPickedUp) {
        this.itemPickedUp = itemPickedUp;
    }

    public boolean isHoldingItem() {
        return holdingItem;
    }

    public void setHoldingItem(boolean holdingItem) {
        this.holdingItem = holdingItem;
    }

    public CollidableActor getHeldItem() {
        return heldItem;
    }
    public void setHeldItem(CollidableActor heldItem) {
        this.heldItem = heldItem;
    }

    // Method to drop the held item
    private void dropItem() {
        if (heldItem != null) {
            heldItem.setPosition(getX(), getY()); // Set the position of the dropped item to the bucket's position
            getStage().addActor(heldItem); // Add the item to the stage
            setHoldingItem(false); // Update holdingItem flag
            setHeldItem(null); // Clear the held item reference
        }
    }
    public enum Direction {
        LEFT, RIGHT, UP, DOWN
    }
}
