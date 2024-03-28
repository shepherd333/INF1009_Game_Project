package com.mygdx.game.GameLayer.GameEntities.Movers;
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
import GameEngine.SimulationLifecycleManagement.LifeSystem.LifeManager;
import GameEngine.SimulationLifecycleManagement.ScoreSystem.ScoreManager;
import com.mygdx.game.GameLayer.GameEntities.Static.BinActor;
import com.mygdx.game.GameLayer.Scenes.GamePlay;
import GameEngine.SceneManagement.SceneManager;
import com.mygdx.game.GameLayer.GameEntities.Movers.enums.ItemType;

public class BucketActor extends CollidableActor {
    private Texture textureLeft;
    private Texture textureRight;
    private Texture textureUp;
    private Texture textureDown;
    private Sprite currentSprite;
    private float speed;
    private boolean itemPickedUp; // Flag to check if an item has been picked up
    private ItemType itemType; // Type of item picked up
    private Sprite heldItemSprite; // Sprite to display the item picked up
    private LifeManager lifeManager;
    private ItemType heldItemType;
    private GamePlay gamePlay;
    private ItemActor heldItem; // Reference to the currently held item
    private boolean isShaking = false;
    private float shakeDuration = 0f;
    private float shakeIntensity = 5f;
    private float shakeTimer = 0f;
    private SceneManager sceneManager;
    private BucketMovementHandler movementHandler;

    // Constructor
    public BucketActor(float x, float y, float speed, float maxHealth, GamePlay gamePlay) {
        this.gamePlay = gamePlay;
        this.sceneManager = sceneManager; // Initialize the SceneManager
        this.lifeManager = new LifeManager(maxHealth, 100, 10, Color.GREEN, gamePlay);
        this.speed = speed;
        this.setPosition(x, y);
        textureLeft = new Texture(Gdx.files.internal("WalleLeft.png"));
        textureRight = new Texture(Gdx.files.internal("WalleRight.png"));
        textureUp = new Texture(Gdx.files.internal("WalleBack.png"));
        textureDown = new Texture(Gdx.files.internal("WalleDown.png"));
        // Set the initial texture, for example, facing up
        currentSprite = new Sprite(textureDown);
        currentSprite.setSize(90,115);
        this.setSize(currentSprite.getWidth(), currentSprite.getHeight());
        setTouchable(Touchable.enabled);
        InputHandlerInterface inputHandler = new GdxInputHandler();
        movementHandler = new BucketMovementHandler(this, speed, inputHandler);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        movementHandler.handleMovement(delta);
        ensureInBounds();
        checkToxicWasteCollision();

        // Existing logic for handling shaking, etc.
        if (isShaking) {
            shakeTimer += delta;
            if (shakeTimer <= shakeDuration) {
                // Apply shaking by randomly offsetting the actor's position within the shake intensity range
                float shakeOffsetX = MathUtils.random(-shakeIntensity, shakeIntensity);
                float shakeOffsetY = MathUtils.random(-shakeIntensity, shakeIntensity);
                setPosition(getX() + shakeOffsetX, getY() + shakeOffsetY);
            } else {
                isShaking = false;
                shakeTimer = 0;
            }
        }
    }

    private void checkToxicWasteCollision() {
        for (Actor actor : getStage().getActors()) {
            if (actor instanceof ToxicWasteActor) {
                ToxicWasteActor toxicWaste = (ToxicWasteActor) actor;
                if (getBounds().overlaps(toxicWaste.getBounds())) {
                    decreaseLife(0.1F); // Decrease life by 1 or another value based on your game's balance
                    AudioManager.getInstance().playSoundEffect("collision", 1.0f);
                    break; // Optional: break if you only want to apply damage from one toxic waste per frame
                }
            }
        }
    }
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

    public void clearHeldItem() {
        this.heldItemType = null;
        this.heldItemSprite = null;
        this.heldItem = null; // Clear the reference
        setItemPickedUp(false);
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

    public void startShaking(float duration, float intensity) {
        this.isShaking = true;
        this.shakeDuration = duration;
        this.shakeIntensity = intensity;
        this.shakeTimer = 0f; // Reset the shake timer
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

    public void decreaseLife(float amount) {
        lifeManager.decreaseHealth(amount); // Assuming LifeManager has a method to decrease life
        if (lifeManager.getLife() <= 0) {
            // Handle the bucket's life reaching zero or below
            // For example, trigger a game over or respawn the bucket
        }
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

    public void setHeldItemType(ItemType itemType) {
        this.heldItemType = itemType;
        Gdx.app.log("BucketActor", "Held item type set to: " + itemType);
    }

    public ItemType getHeldItemType() {
        return heldItemType;
    }

    public enum Direction {
        LEFT, RIGHT, UP, DOWN
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
}