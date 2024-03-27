package com.mygdx.game.EntityManagement;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
//    private Texture texture;
//    private String possessionValue;
    private boolean itemPickedUp; // Flag to check if an item has been picked up
    private int itemType; // Type of item picked up
    private Sprite heldItemSprite; // Sprite to display the item picked up
    private LifeManager lifeManager;
    private ItemType heldItemType;
    private TextureRegion heldItemTextureRegion;


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
        handleInput(delta); // Handle user input separately
        ensureInBounds(); // Ensure the actor remains within the screen bounds
    }

    private void handleInput(float delta) {
        if (!itemPickedUp) {
            float newX = getX(), newY = getY();
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) { newX -= speed * delta; changeDirection(Direction.LEFT); }
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) { newX += speed * delta; changeDirection(Direction.RIGHT); }
            if (Gdx.input.isKeyPressed(Input.Keys.UP)) { newY += speed * delta; changeDirection(Direction.UP); }
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) { newY -= speed * delta; changeDirection(Direction.DOWN); }
            setPosition(newX, newY);
        }
        if (itemPickedUp){
            if (Gdx.input.isKeyPressed(Input.Keys.D)){
                clearHeldItem();
            }
        }
    }

    private void ensureInBounds() {
        float clampedX = MathUtils.clamp(getX(), 0, getStage().getViewport().getWorldWidth() - getWidth());
        float clampedY = MathUtils.clamp(getY(), 0, getStage().getViewport().getWorldHeight() - getHeight());
        setPosition(clampedX, clampedY);
    }

    public void setHeldItemSprite(TextureRegion textureRegion) {
        // Initialize the sprite with the new texture region
        this.heldItemSprite = new Sprite(textureRegion);

        // Set the size of the sprite
        this.heldItemSprite.setSize(50, 50); // You can use any size you want

        // Optionally, set the origin of the sprite if you need to rotate it around its center
        this.heldItemSprite.setOrigin(25, 25); // Set origin to center for a 50x50 sprite
    }


    public void clearHeldItem() {
        this.heldItemType = null; // Assuming heldItemType is used to track the current item
        this.setItemPickedUp(false);
        // Reset the texture or visual representation of the bucket
        this.heldItemTextureRegion = null;
    }
    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(currentSprite, getX(), getY(), getWidth(), getHeight());
        if (heldItemSprite != null) {
            heldItemSprite.setPosition(this.getX(), this.getY() + this.getHeight()); // Position it relative to the bucket
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
    public boolean isItemPickedUp() {
        return itemPickedUp;
    }
    public void setItemPickedUp(boolean itemPickedUp) {
        this.itemPickedUp = itemPickedUp;
    }

    public void setHeldItemTextureRegion(TextureRegion textureRegion) {
        this.heldItemTextureRegion = textureRegion;
        // Update any sprite or drawable that uses this texture region
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