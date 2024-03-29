package com.mygdx.game.GameLayer.GameEntities.Movers;

// Import statements to use various functionalities from different packages
import GameEngine.AIControl.ShakingHandler;
import GameEngine.Collisions.handlers.BucketToxicHandler;
import GameEngine.EntityManagement.EntityManager;
import GameEngine.PlayerControl.GdxInputHandler;
import GameEngine.PlayerControl.InputHandlerInterface;
import GameEngine.SimulationLifecycleManagement.AudioManager;
import GameEngine.SimulationLifecycleManagement.ScoreManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import GameEngine.EntityManagement.CollidableActor;
import GameEngine.PlayerControl.BucketMovementHandler;
import GameEngine.SimulationLifecycleManagement.LifeManager;
import GameEngine.Collisions.handlers.enums.Direction;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.mygdx.game.GameLayer.GameEntities.Movers.Static.BinActor;
import com.mygdx.game.GameLayer.Scenes.GamePlay;
import GameEngine.Collisions.handlers.enums.ItemType;

import java.util.EnumMap;

// Class BucketActor is an actor that can move around and interact with other entities on the screen.
public class BucketActor extends CollidableActor {
    // Fields for the actor's properties
    private Sprite currentSprite;
    private float speed;
    private boolean itemPickedUp; // Indicates if the bucket has picked up an item
    private Sprite heldItemSprite; // Visual representation of the held item
    private LifeManager lifeManager; // Manages the actor's health and life state
    private ItemType heldItemType; // Type of the item currently held by the bucket
    private GamePlay gamePlay; // Reference to the game play context
    private EntityManager entityManager; // Manager for entity operations like add or remove
    private ItemActor heldItem; // Actual reference to the currently held item
    private boolean isShaking = false; // Indicates if the bucket is currently shaking (due to some effect)
    private float shakeDuration = 0f; // Duration of the shaking effect
    private float shakeIntensity = 5f; // How intense the shake effect should be
    private float shakeTimer = 0f; // Timer to track shaking duration
    private BucketMovementHandler movementHandler; // Handles movement logic for the bucket
    private EnumMap<Direction, Texture> directionTextures; // Textures for each movement direction

    // Constructor to initialize a BucketActor instance
    public BucketActor(float x, float y, float speed, float maxHealth, GamePlay gamePlay) {
        this.gamePlay = gamePlay;
        this.lifeManager = new LifeManager(maxHealth, 100, 10, Color.GREEN, gamePlay);
        this.speed = speed;
        this.setPosition(x, y); // Set the actor's starting position
        loadTextures(); // Load necessary textures for different directions
        initializeSprite(); // Initialize the actor's sprite with a texture
        initializeInputHandler(); // Setup input handling for movement
    }

    // Called every frame, updates the actor's state and handles movement
    @Override
    public void act(float delta) {
        super.act(delta);
        movementHandler.handleMovement(delta); // Handle movement based on input
        ensureInBounds(); // Ensure the actor stays within game boundaries
        BucketToxicHandler.checkToxicWasteCollision(this, getStage(), AudioManager.getInstance()); // Check for collisions with toxic waste
        ShakingHandler.updateShaking(this, delta); // Update shaking effect if applicable
    }

    // Draws the actor and its held item sprite onto the screen
    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(currentSprite, getX(), getY(), getWidth(), getHeight()); // Draw the main sprite
        if (heldItemSprite != null) {
            heldItemSprite.setPosition(this.getX(), this.getY() + this.getHeight()); // Position the held item sprite
            heldItemSprite.draw(batch); // Draw the held item sprite
        }
        lifeManager.draw(batch, getX(), getY(), getWidth(), getHeight()); // Draw health/life info
    }

    // Load textures for each direction the bucket can face
    private void loadTextures() {
        directionTextures = new EnumMap<>(Direction.class);
        directionTextures.put(Direction.LEFT, new Texture(Gdx.files.internal("WalleLeft.png")));
        directionTextures.put(Direction.RIGHT, new Texture(Gdx.files.internal("WalleRight.png")));
        directionTextures.put(Direction.UP, new Texture(Gdx.files.internal("WalleBack.png")));
        directionTextures.put(Direction.DOWN, new Texture(Gdx.files.internal("WalleDown.png")));
    }

    // Initialize the sprite with the default texture
    private void initializeSprite() {
        Texture defaultTexture = directionTextures.get(Direction.DOWN); // Assuming DOWN is the default direction
        if (defaultTexture != null) {
            currentSprite = new Sprite(defaultTexture); // Create sprite with the default texture
            currentSprite.setSize(90, 115); // Set desired size for the sprite
            this.setSize(currentSprite.getWidth(), currentSprite.getHeight()); // Match actor's size to sprite
        } else {
            // Log error if default texture is not found
            Gdx.app.log("BucketActor", "Default texture (DOWN) not found. Check if directionTextures is initialized properly.");
        }
    }

    // Setup input handling to control the actor's movement
    private void initializeInputHandler() {
        setTouchable(Touchable.enabled); // Make the actor touchable for input events
        InputHandlerInterface inputHandler = new GdxInputHandler();
        movementHandler = new BucketMovementHandler(this, speed, inputHandler); // Initialize the movement handler
    }

    // Ensure the actor remains within the bounds of the game screen
    private void ensureInBounds() {
        // Clamp the actor's X and Y position to make sure it doesn't go out of the screen
        float clampedX = MathUtils.clamp(getX(), 0, getStage().getViewport().getWorldWidth() - getWidth());
        float clampedY = MathUtils.clamp(getY(), 0, getStage().getViewport().getWorldHeight() - getHeight());
        setPosition(clampedX, clampedY); // Apply the clamped positions
    }

    // Changes the direction the actor is facing by updating the sprite texture
    public void changeDirection(Direction direction) {
        Texture newDirectionTexture = directionTextures.get(direction); // Get the texture for the new direction
        if (newDirectionTexture != null) {
            currentSprite.setTexture(newDirectionTexture); // Set the sprite's texture to the new one
            this.setSize(currentSprite.getWidth(), currentSprite.getHeight()); // Update the actor's size based on the new sprite
        }
    }

    // Returns the bounding box of the actor, used for collision detection
    public Rectangle getBounds() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }

    // Handles logic when an item is picked up by the actor
    public void holdItem(ItemActor item) {
        this.heldItem = item; // Reference the item being held
        this.heldItemType = item.getItemType(); // Set the type of the item being held
        AudioManager.getInstance().playSoundEffect("itemPickup", 1.0f); // Play item pickup sound effect
        setHeldItemType(item.getItemType()); // Update the held item type
        setHeldItemSprite(item.getTextureRegion()); // Update the sprite for the held item
        setItemPickedUp(true); // Mark that an item is being held
    }

    // Handles logic when the held item is dropped
    public void dropHeldItem() {
        if (heldItem != null) {
            ItemType overlappingBinType = getOverlappingBinType(); // Determine the type of bin the item is dropped into
            if (overlappingBinType != null && overlappingBinType == heldItem.getItemType()) {
                AudioManager.getInstance().playSoundEffect("correctBin", 1.0f); // Play sound for correct item drop
                ScoreManager.getInstance().addToCurrentScore(100); // Increment score for correct drop
            } else {
                // If item drop is incorrect or no bin overlap, log or handle accordingly
            }
            heldItem.remove(); // Remove the item from the scene
            clearHeldItem(); // Clear the references to the held item
        }
    }

    // Handles incorrect item drop situations
    public void errorDropHeldItem(){
        if (heldItem != null){
            ItemType overlappingBinType = getOverlappingBinType(); // Check the type of bin currently overlapping
            if (overlappingBinType != null && overlappingBinType != heldItem.getItemType()){
                AudioManager.getInstance().playSoundEffect("errorSound", 1.0f); // Play error sound effect
                ScoreManager.getInstance().subtractFromCurrentScore(50); // Subtract score for incorrect drop
            }
        }
    }

    // Sets the sprite for the item currently being held
    public void setHeldItemSprite(TextureRegion textureRegion) {
        this.heldItemSprite = new Sprite(textureRegion); // Initialize the sprite with the texture region
        this.heldItemSprite.setSize(50, 50); // Set the size of the sprite
        this.heldItemSprite.setOrigin(25, 25); // Set the origin for potential rotations
    }

    // Clears the references to the held item
    public void clearHeldItem() {
        this.heldItemType = null;
        this.heldItemSprite = null;
        this.heldItem = null;
        setItemPickedUp(false); // Update the flag to indicate no item is being held
    }

    // Determines the type of bin that is currently overlapping with the actor
    public ItemType getOverlappingBinType() {
        float proximityMargin = 200; // Define how close the actor needs to be to consider an overlap
        for (Actor actor : getStage().getActors()) {
            if (actor instanceof BinActor) {
                BinActor bin = (BinActor) actor;
                Rectangle expandedBinBounds = new Rectangle(
                        bin.getBounds().x - proximityMargin / 2,
                        bin.getBounds().y - proximityMargin / 2,
                        bin.getBounds().width + proximityMargin,
                        bin.getBounds().height + proximityMargin);

                if (this.getBounds().overlaps(expandedBinBounds)) {
                    return bin.getAcceptsType(); // Return the type of the overlapping bin
                }
            }
        }
        return null; // Return null if no overlapping bin is found
    }

    // Other overridden and utility methods like setWidth, setHeight, dispose, decreaseLife, getters, and setters
    // These handle various aspects like managing actor size, cleanup, managing health, and accessing/modifying properties

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