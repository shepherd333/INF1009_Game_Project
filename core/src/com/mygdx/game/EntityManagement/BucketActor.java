package com.mygdx.game.EntityManagement;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.Lifecycle.LifeSystem.LifeManager;
import com.mygdx.game.enums.ItemType;

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
    private int itemType; // Type of item picked up
    private Sprite heldItemSprite; // Sprite to display the item picked up
    private LifeManager lifeManager;
    private ItemType heldItemType;


    // Constructor
    public BucketActor(float x, float y, float speed, float maxHealth ) {
        this.lifeManager = new LifeManager(maxHealth, 100, 10, Color.GREEN);
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
        if (!itemPickedUp) {
            // Get current bucket position
            float newX = getX();
            float newY = getY();
            // Update position based on input
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                newX -= speed * delta;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                newX += speed * delta;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                newY += speed * delta;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                newY -= speed * delta;
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
    }
    public void setHeldItemSprite(Texture texture) {
        this.heldItemSprite = new Sprite(texture);
        this.heldItemSprite.setSize(50, 50); // Set the size of the sprite
    }
    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(currentSprite, getX(), getY(), getWidth(), getHeight());
        if (heldItemSprite != null) {
            heldItemSprite.setPosition(getX(), getY() + getHeight()); // Position the sprite above the bucket
            heldItemSprite.draw(batch);
        }
        lifeManager.draw(batch, getX(), getY(), getWidth(), getHeight());
    }

    public void setHealth(float health) {
        lifeManager.updateHealth(health);
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
    // Provides a bounding box for the bucket, useful for collision detection.
    public Rectangle getBounds() {
        Rectangle bounds = new Rectangle(getX(), getY(), getWidth(), getHeight());
        Gdx.app.log("BucketActor", "Bounds: " + bounds.toString());
//        Gdx.app.log("BucketActor", "Bounds: " + bounds.toString());
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
    public void setItemType(int itemType) {
        this.itemType = itemType;
    }
    public int getItemType() {
        return itemType;
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

    public void setHeldItemType(ItemType itemType) {
        this.heldItemType = itemType;
    }

    public ItemType getHeldItemType() {
        return heldItemType;
    }
    public enum Direction {
        LEFT, RIGHT, UP, DOWN
    }
}