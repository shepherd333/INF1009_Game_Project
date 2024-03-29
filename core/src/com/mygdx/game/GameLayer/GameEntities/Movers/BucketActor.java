package com.mygdx.game.GameLayer.GameEntities.Movers;
import GameEngine.AIControl.ShakingHandler;
import GameEngine.Collisions.handlers.BucketToxicHandler;
import GameEngine.EntityManagement.EntityManager;
import GameEngine.PlayerControl.GdxInputHandler;
import GameEngine.PlayerControl.InputHandlerInterface;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.graphics.g2d.Sprite;
import GameEngine.EntityManagement.CollidableActor;
import GameEngine.PlayerControl.BucketMovementHandler;
import GameEngine.SimulationLifecycleManagement.AudioManager;
import GameEngine.SimulationLifecycleManagement.LifeManager;
import GameEngine.SimulationLifecycleManagement.ScoreManager;
import GameEngine.Collisions.handlers.enums.Direction;
import com.mygdx.game.GameLayer.GameEntities.Movers.Static.BinActor;
import com.mygdx.game.GameLayer.Scenes.GamePlay;
import GameEngine.Collisions.handlers.enums.ItemType;

import java.util.EnumMap;


public class BucketActor extends CollidableActor {
    private Sprite currentSprite;
    private float speed;
    private boolean itemPickedUp; // Flag to check if an item has been picked up
    private Sprite heldItemSprite; // Sprite to display the item picked up
    private LifeManager lifeManager;
    private ItemType heldItemType;
    private GamePlay gamePlay;
    private EntityManager entityManager;
    private ItemActor heldItem; // Reference to the currently held item
    private boolean isShaking = false;
    private float shakeDuration = 0f;
    private float shakeIntensity = 5f;
    private float shakeTimer = 0f;
    private BucketMovementHandler movementHandler;
    private EnumMap<Direction, Texture> directionTextures;

    // Constructor
    public BucketActor(float x, float y, float speed, float maxHealth, GamePlay gamePlay) {
        this.gamePlay = gamePlay;
        this.lifeManager = new LifeManager(maxHealth, 100, 10, Color.GREEN, gamePlay);
        this.speed = speed;
        this.setPosition(x, y);
        loadTextures();
        initializeSprite();
        initializeInputHandler();
    }
    @Override
    public void act(float delta) {
        super.act(delta);
        movementHandler.handleMovement(delta);
        ensureInBounds();
        BucketToxicHandler.checkToxicWasteCollision(this, getStage(), AudioManager.getInstance());
        ShakingHandler.updateShaking(this, delta);
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

    //Initializers
    private void loadTextures() {
        directionTextures = new EnumMap<>(Direction.class);
        directionTextures.put(Direction.LEFT, new Texture(Gdx.files.internal("WalleLeft.png")));
        directionTextures.put(Direction.RIGHT, new Texture(Gdx.files.internal("WalleRight.png")));
        directionTextures.put(Direction.UP, new Texture(Gdx.files.internal("WalleBack.png")));
        directionTextures.put(Direction.DOWN, new Texture(Gdx.files.internal("WalleDown.png")));
    }

    private void initializeSprite() {
        // Assuming DOWN is the default direction. Adjust if necessary.
        Texture defaultTexture = directionTextures.get(Direction.DOWN);
        if (defaultTexture != null) {
            currentSprite = new Sprite(defaultTexture);
        } else {
            // Handle the case where the texture is not found. This could be logging an error or using a fallback texture.
            Gdx.app.log("BucketActor", "Default texture (DOWN) not found. Check if directionTextures is initialized properly.");
            return; // Early return to avoid NullPointerException in case defaultTexture is null
        }

        // Set the desired size for the sprite.
        currentSprite.setSize(90, 115);

        // Optionally, update the actor's size to match the sprite if needed.
        // This is useful if your actor's dimensions are meant to match the sprite exactly.
        this.setSize(currentSprite.getWidth(), currentSprite.getHeight());
    }


    private void initializeInputHandler() {
        setTouchable(Touchable.enabled);
        InputHandlerInterface inputHandler = new GdxInputHandler();
        movementHandler = new BucketMovementHandler(this, speed, inputHandler);
    }

    private void ensureInBounds() {
        float clampedX = MathUtils.clamp(getX(), 0, getStage().getViewport().getWorldWidth() - getWidth());
        float clampedY = MathUtils.clamp(getY(), 0, getStage().getViewport().getWorldHeight() - getHeight());
        setPosition(clampedX, clampedY);
    }

    public void changeDirection(Direction direction) {
        Texture newDirectionTexture = directionTextures.get(direction);
        if (newDirectionTexture != null) {
            currentSprite.setTexture(newDirectionTexture);
            this.setSize(currentSprite.getWidth(), currentSprite.getHeight());
            // You may also need to adjust the sprite's size or other properties here.
        }
    }

    // Provides a bounding box for the bucket, useful for collision detection.
    public Rectangle getBounds() {
        Rectangle bounds = new Rectangle(getX(), getY(), getWidth(), getHeight());
//        Gdx.app.log("BucketActor", "Bounds: " + bounds.toString());
        return bounds;
    }

    //Item Methods
    // Call this method when the item is picked up
    public void holdItem(ItemActor item) {
        this.heldItem = item;
        this.heldItemType = item.getItemType();
        AudioManager.getInstance().playSoundEffect("itemPickup", 1.0f);
        setHeldItemType(item.getItemType());
        setHeldItemSprite(item.getTextureRegion());
        setItemPickedUp(true);
    }

    // Call this method when the item is dropped into a bin
    public void dropHeldItem() {
        if (heldItem != null) {
            // Debugging: Log when attempting to drop an item
            Gdx.app.log("BucketActor", "Attempting to drop held item...");

            // Get the type of the bin that is currently overlapping with the bucket
            ItemType overlappingBinType = getOverlappingBinType();

            // Debugging: Log the types involved in the operation
            Gdx.app.log("BucketActor", "Held item type: " + heldItem.getItemType());
            Gdx.app.log("BucketActor", "Overlapping bin type: " + (overlappingBinType == null ? "None" : overlappingBinType));

            // If there is a bin overlapping and the item's type matches the bin's type
            if (overlappingBinType != null && overlappingBinType == heldItem.getItemType()) {
                // Debugging: Log successful item drop
                Gdx.app.log("BucketActor", "Dropping item into correct bin.");

                // Correct bin
                AudioManager.getInstance().playSoundEffect("correctBin", 1.0f);
                ScoreManager.getInstance().addToCurrentScore(100); // Add 100 points for correct placement
                Gdx.app.log("GamePlay", "Correct bin! Score added!!!");
            } else {
                // Debugging: Log unsuccessful item drop or no bin overlap
                Gdx.app.log("BucketActor", "Failed to drop item into correct bin or no bin overlap.");
            }

            // Now we can remove the item from the scene
            heldItem.remove();

            // Debugging: Log clearing the held item
            Gdx.app.log("BucketActor", "Held item removed from scene and clearing references.");

            // Clear the held item reference
            clearHeldItem();
        } else {
            // Debugging: Log when there's no item to drop
            Gdx.app.log("BucketActor", "No item held to drop.");
        }
    }


    public void errorDropHeldItem(){
        if (heldItem != null){
            ItemType overlappingBinType = getOverlappingBinType();
            if (overlappingBinType != null && overlappingBinType != heldItem.getItemType()){
                // Incorrect bin or no bin
                AudioManager.getInstance().playSoundEffect("errorSound", 1.0f);
                ScoreManager.getInstance().subtractFromCurrentScore(50); // Subtract 50 points for incorrect placement
                Gdx.app.log("GamePlay", "Incorrect bin or no bin overlapping! Score subtracted.");
            }
        }
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
        this.heldItemType = null;
        this.heldItemSprite = null;
        this.heldItem = null; // Clear the reference
        setItemPickedUp(false);
    }

    public ItemType getOverlappingBinType() {
        float proximityMargin = 200; // Define a margin for how close the bucket needs to be

        for (Actor actor : getStage().getActors()) {
            if (actor instanceof BinActor) {
                BinActor bin = (BinActor) actor;
                // Create a larger rectangle around the bin based on the proximity margin
                Rectangle expandedBinBounds = new Rectangle(
                        bin.getBounds().x - proximityMargin / 2,
                        bin.getBounds().y - proximityMargin / 2,
                        bin.getBounds().width + proximityMargin,
                        bin.getBounds().height + proximityMargin);

                if (this.getBounds().overlaps(expandedBinBounds)) {
                    return bin.getAcceptsType();
                }
            }
        }
        return null;
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
        for (Texture texture : directionTextures.values()) {
            texture.dispose();
        }
    }
    public void decreaseLife(float amount) {
        lifeManager.decreaseHealth(amount); // Assuming LifeManager has a method to decrease life
        if (lifeManager.getLife() <= 0) {
        }
    }
    //GETTERS AND SETTERS
    public boolean isItemPickedUp() {
        return itemPickedUp;
    }
    public void setItemPickedUp(boolean itemPickedUp) {
        this.itemPickedUp = itemPickedUp;
    }

    public void setHeldItemType(ItemType itemType) {
        this.heldItemType = itemType;
        Gdx.app.log("BucketActor", "Held item type set to: " + itemType);
    }

    public ItemType getHeldItemType() {
        return heldItemType;
    }
    public void setShaking(boolean shaking) {
        this.isShaking = shaking;
    }

    public void setShakeDuration(float duration) {
        this.shakeDuration = duration;
    }

    public void setShakeIntensity(float intensity) {
        this.shakeIntensity = intensity;
    }

    public void setShakeTimer(float timer) {
        this.shakeTimer = timer;
    }

    public boolean isShaking() {
        return isShaking;
    }

    public float getShakeDuration() {
        return shakeDuration;
    }

    public float getShakeIntensity() {
        return shakeIntensity;
    }

    public float getShakeTimer() {
        return shakeTimer;
    }
}