package com.mygdx.game.GameLayer.GameEntities.Movers;

import GameEngine.SimulationLifecycleManagement.ScoreManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import GameEngine.EntityManagement.CollidableActor;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.GameLayer.Scenes.GamePlay;
import GameEngine.Collisions.handlers.enums.ItemType;

import java.util.HashMap;
import java.util.Map;

/**
 * ItemActor is a class that represents a falling item in a game, which can be of different types (e.g., glass, metal, paper, plastic, trash).
 * Each item type has its own texture and behavior.
 * This includes:
 * - Holding references to a TextureAtlas, TextureRegion, GamePlay, and ItemType.
 * - Providing a constructor to initialize the ItemActor with an ItemType, speed, bucketX, bucketWidth, and GamePlay. In this constructor, it sets the ItemType, speed, bucketX, bucketWidth, and GamePlay, loads the texture atlas for the item type if it hasn't already been loaded, assigns the texture region for this item, makes the actor touchable to detect interactions, sets the size of the actor based on the texture, initializes the unique value placeholder, and adjusts the size based on the item type.
 * - Providing a method to reset the position of the item to the top of the screen for re-use.
 * - Overriding a method to act on the item. In this method, it calls the act method of the superclass, moves the item across the screen, and removes the actor if it goes off-screen.
 * - Overriding a method to draw the item. In this method, it draws the texture region on the batch if it is not null.
 * - Overriding a method to remove the item. In this method, it calls the remove method of the superclass and performs additional cleanup if necessary.
 * - Providing a method to get the bounds of the item.
 * - Providing a method to dispose of resources when the item is no longer needed.
 * - Providing a method to adjust the size of the actor based on the item type.
 * - Providing getters and setters for various properties.
 * - Providing a method to remove this item from the game play.
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
    private float scaleFactor = 2F ; // This scale factor will multiply the size of the item

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

        // Assign the texture region for this item, randomly selected from available ones for the itemType
        TextureAtlas itemAtlas = atlasMap.get(itemType);
        Array<TextureAtlas.AtlasRegion> regions = itemAtlas.findRegions(regionNames[itemType.ordinal()]);

        if (regions.size > 0) {
            int index = MathUtils.random(0, regions.size - 1);
            textureRegion = regions.get(index);
        } else {
            Gdx.app.error("ItemActor", "No TextureRegions found for item type: " + itemType);
        }

        // Make the actor touchable to detect interactions
        setTouchable(Touchable.enabled);

        // Set the size of the actor based on the texture
        this.setSize(textureRegion.getRegionWidth() * 3, textureRegion.getRegionHeight() * 3);

        // Unique value placeholder initialization
        this.uniqueValue = "1";

        // Adjust the size based on the item type
        setSizeForItem(itemType);
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
            ScoreManager.getInstance().subtractFromCurrentScore(10);
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
//    private void setSizeForItemType(ItemType itemType) {
//        // Adjust the size based on specific item types
//        switch (itemType) {
//            case GLASS:
//                this.setSize(90, 90);
//                break;
//            case METAL:
//                this.setSize(90, 90);
//                break;
//            case PAPER:
//                this.setSize(90, 90);
//                break;
//            case PLASTIC:
//                this.setSize(90, 90);
//                break;
//            case TRASH:
//                this.setSize(90, 90);
//                break;
//            default:
//                this.setSize(90, 90); // Default size
//                break;
//        }
//    }



    private void setSizeForItem(ItemType itemType) {
        // If the textureRegion is not null, set the size based on its width and height scaled by scaleFactor
        if (textureRegion != null) {
            this.setSize(textureRegion.getRegionWidth() * scaleFactor, textureRegion.getRegionHeight() * scaleFactor);
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
