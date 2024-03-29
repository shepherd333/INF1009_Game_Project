package com.mygdx.game.GameLayer.GameEntities.Movers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import GameEngine.EntityManagement.CollidableActor;
import com.mygdx.game.GameLayer.Scenes.GamePlay;
import GameEngine.Collisions.handlers.enums.ItemType;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a falling item in the game, which can be of different types (e.g., glass, metal, paper, plastic, trash).
 * Each item type has its own texture and behavior.
 */
public class ItemActor extends CollidableActor {
    private static final Map<ItemType, TextureAtlas> atlasMap = new HashMap<>();
    private static final Map<ItemType, Integer> atlasRefCount = new HashMap<>();
    private TextureRegion textureRegion;
    private float speed; // The falling speed of the item
    public float bucketX, bucketWidth; // Position and width of the bucket to calculate interactions
    private GamePlay gamePlay; // Reference to the game play scene for interaction
    private ItemType itemType; // Type of the item
    private String uniqueValue; // Placeholder for unique properties or identification

    // Path to texture atlases and region names for different item types
    private static final String[] atlasPaths = {
            "glassitems.atlas", "metalitems.atlas", "paperitems.atlas", "plasticitems.atlas", "trashitems.atlas"
    };
    private static final String[] regionNames = {
            "glassItem", "metalItem", "paperItem", "plasticItem", "trashItem"
    };

    public ItemActor(ItemType itemType, float speed, float bucketX, float bucketWidth, GamePlay gamePlay) {
        this.itemType = itemType;
        this.speed = speed;
        this.bucketX = bucketX;
        this.bucketWidth = bucketWidth;
        this.gamePlay = gamePlay;

        // Load the texture atlas for the item type if it hasn't already been loaded
        synchronized (ItemActor.class) {
            if (!atlasMap.containsKey(itemType)) {
                TextureAtlas itemAtlas = new TextureAtlas(Gdx.files.internal(atlasPaths[itemType.ordinal()]));
                atlasMap.put(itemType, itemAtlas);
                atlasRefCount.put(itemType, 1);
            } else {
                int currentRefCount = atlasRefCount.get(itemType);
                atlasRefCount.put(itemType, currentRefCount + 1);
            }
        }

        // Assign the texture region for this item
        TextureAtlas itemAtlas = atlasMap.get(itemType);
        textureRegion = itemAtlas.findRegion(regionNames[itemType.ordinal()]);
        if (textureRegion == null) {
            Gdx.app.error("ItemActor", "TextureRegion is null for item type: " + itemType);
        }

        // Make the actor touchable to detect interactions
        setTouchable(Touchable.enabled);

        // Set the size of the actor based on the texture
        this.setSize(textureRegion.getRegionWidth() * 3, textureRegion.getRegionHeight() * 3);

        // Unique value placeholder initialization
        this.uniqueValue = "1";

        // Adjust the size based on the item type
        setSizeForItemType(itemType);
    }

    /**
     * Resets the position of the item to the top of the screen for re-use.
     */
    public void resetPosition(float bucketX, float bucketWidth) {
        float stageWidth = this.getStage().getViewport().getWorldWidth();
        float fixedDistance = 20; // Desired distance from the bottom or top

        // Set the new position
        this.setPosition(stageWidth, fixedDistance);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        // Move the item across the screen
        setX(getX() - speed * delta);

        // Remove the actor if it goes off-screen
        if (getX() + getWidth() < 0) {
            remove();
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (textureRegion != null) {
            batch.draw(textureRegion, getX(), getY(), getWidth(), getHeight());
        } else {
            System.out.println("TextureRegion is null for item type: " + itemType);
        }
    }

    @Override
    public boolean remove() {
        boolean removed = super.remove();
        if (removed) {
            // Additional cleanup can be performed here if necessary
        }
        return removed;
    }

    public Rectangle getBounds() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }

    public void dispose() {
        synchronized (ItemActor.class) {
            int currentRefCount = atlasRefCount.get(itemType) - 1;
            if (currentRefCount == 0) {
                atlasMap.get(itemType).dispose();
                atlasMap.remove(itemType);
                atlasRefCount.remove(itemType);
            } else {
                atlasRefCount.put(itemType, currentRefCount);
            }
        }
    }

    /**
     * Adjusts the size of the actor based on the item type.
     */
    private void setSizeForItemType(ItemType itemType) {
        // Adjust the size based on specific item types
        switch (itemType) {
            case GLASS:
                this.setSize(60, 60);
                break;
            case METAL:
                this.setSize(50, 50);
                break;
            case PAPER:
                this.setSize(70, 100);
                break;
            case PLASTIC:
                this.setSize(55, 55);
                break;
            case TRASH:
                this.setSize(65, 65);
                break;
            default:
                this.setSize(50, 50); // Default size
                break;
        }
    }

    // Getters and setters for various properties
    public float getSpeed() { return speed; }
    public void setSpeed(float speed) { this.speed = speed; }
    public ItemType getItemType() { return itemType; }
    public TextureRegion getTextureRegion() { return textureRegion; }
    public String getUniqueValue() { return uniqueValue; }

    /**
     * Removes this item from the game play.
     */
    public void removeItem() {
        if (gamePlay != null) {
            gamePlay.removeItemFromList(this);
        }
        if (getStage() != null) {
            remove();
        }
    }
}
