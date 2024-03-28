package com.mygdx.game.GameLayer.GameEntities.Movers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import GameEngine.EntityManagement.CollidableActor;
import com.mygdx.game.GameLayer.Scenes.GamePlay;
import com.mygdx.game.GameLayer.GameEntities.Movers.enums.ItemType;

import java.util.HashMap;
import java.util.Map;

public class ItemActor extends CollidableActor {
    private static final Map<ItemType, TextureAtlas> atlasMap = new HashMap<>();
    private static final Map<ItemType, Integer> atlasRefCount = new HashMap<>();
    private TextureRegion textureRegion;
    private float speed;
    public float bucketX, bucketWidth;
    private GamePlay gamePlay;
    private ItemType itemType;
    private String uniqueValue;

    // Atlas and region management based on item type
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

        synchronized (ItemActor.class) {
            // Load TextureAtlas if not already loaded
            if (!atlasMap.containsKey(itemType)) {
                TextureAtlas itemAtlas = new TextureAtlas(Gdx.files.internal(atlasPaths[itemType.ordinal()]));
                atlasMap.put(itemType, itemAtlas);
                atlasRefCount.put(itemType, 1);
            } else {
                int currentRefCount = atlasRefCount.get(itemType);
                atlasRefCount.put(itemType, currentRefCount + 1);
            }
        }

        // Here we assign the textureRegion
        TextureAtlas itemAtlas = atlasMap.get(itemType);
        textureRegion = itemAtlas.findRegion(regionNames[itemType.ordinal()]);
        if (textureRegion == null) {
            Gdx.app.error("ItemActor", "TextureRegion is null for item type: " + itemType);
        }

        setTouchable(Touchable.enabled);
        this.setSize(textureRegion.getRegionWidth()*3, textureRegion.getRegionHeight()*3); // Adjust size based on the texture
        this.uniqueValue = "1";

        setSizeForItemType(itemType);
    }


    public void resetPosition(float bucketX, float bucketWidth) {
        // Use the stage's viewport to get the world width for positioning.
        float stageWidth = this.getStage().getViewport().getWorldWidth();

        // Calculate the fixed distance above the bottom of the screen
        float fixedDistance = 20; // Adjust this value to your desired distance

        // Calculate the Y position
        float yPosition = fixedDistance;

        // Set the initial X position to be just outside the right edge of the stage
        float initialX = stageWidth;

        // Set the new position
        this.setPosition(initialX, yPosition);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        setX(getX() - speed * delta);
        if (getX() + getWidth() < 0) {
            remove(); // Remove the actor if it goes off screen
        }

    }
    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (textureRegion != null) {
            batch.draw(textureRegion, getX(), getY(), getWidth(), getHeight());
        } else {
            // Log error or draw a placeholder
            System.out.println("TextureRegion is null for item type: " + itemType);
        }
    }

    @Override
    public boolean remove() {
        boolean removed = super.remove();  // This line actually removes the actor from the stage
        if (removed) {
            // Perform any additional cleanup here if necessary
        }
        return removed;
    }


    public Rectangle getBounds() {
        Rectangle bounds = new Rectangle(getX(), getY(), getWidth(), getHeight());
        // Log the current bounds
        //Gdx.app.log("GlassItemsActor", "Bounds: " + bounds.toString());
        return bounds;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void dispose() {
        // Decrement the reference count and dispose if no longer needed
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

    private void setSizeForItemType(ItemType itemType) {
        switch (itemType) {
            case GLASS:
                this.setSize(60, 60); // Example size for glass items
                break;
            case METAL:
                this.setSize(50, 50); // Example size for metal items
                break;
            case PAPER:
                this.setSize(70, 100); // Example size for paper items
                break;
            case PLASTIC:
                this.setSize(55, 55); // Example size for plastic items
                break;
            case TRASH:
                this.setSize(65, 65); // Example size for trash items
                break;
            default:
                // Set a default size if item type is not recognized
                this.setSize(50, 50);
                break;
        }
    }

    public String getUniqueValue() {
        return uniqueValue;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public TextureRegion getTextureRegion() {
        return this.textureRegion;
    }

    public void removeItem() {
        // Remove the item from the GamePlay items array
        if (gamePlay != null) {
            gamePlay.removeItemFromList(this);
        }

        // Remove the item from its own stage
        if (getStage() != null) {
            remove();
        }
    }
    // Additional methods and logic based on the item type can be added here
    // ...
}
