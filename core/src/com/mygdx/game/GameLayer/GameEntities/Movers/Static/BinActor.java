package com.mygdx.game.GameLayer.GameEntities.Movers.Static;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import GameEngine.EntityManagement.CollidableActor;
import GameEngine.Collisions.handlers.enums.ItemType;

/**
 * Represents a Bin Actor in the game, which can accept specific item types.
 * The bin's visual representation and position are determined by the item type it accepts
 * and its position index, respectively.
 */
public class BinActor extends CollidableActor {
    private Sprite sprite; // The visual representation of the bin
    private ItemType acceptsType; // The type of item this bin can accept
    private int positionIndex; // Used to determine the bin's x position

    /**
     * Constructs a new Bin Actor.
     *
     * @param itemType The type of item this bin accepts.
     * @param positionIndex The index determining the bin's position on the screen.
     */
    public BinActor(ItemType itemType, int positionIndex) {
        this.acceptsType = itemType;
        this.positionIndex = positionIndex;
        // Assuming a naming convention where the texture's name corresponds to the item type
        String texturePath = itemType.toString() + "Bin.png";
        sprite = new Sprite(new Texture(Gdx.files.internal(texturePath)));
        sprite.setSize(200, 200); // Set the size of the bin

        // Calculate and set the position of the bin based on its index
        float xPosition = 20 + (75 + 150) * positionIndex; // Determines x based on index
        float yPosition = Gdx.graphics.getHeight() - sprite.getHeight() - 50; // 50 pixels from the top
        setPosition(xPosition, yPosition);
    }

    /**
     * Draws the bin on the screen.
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.setPosition(getX(), getY());
        sprite.draw(batch);
    }

    /**
     * Updates the actor's behavior. Placeholder for future logic.
     */
    @Override
    public void act(float delta) {
        super.act(delta);
    }

    /**
     * Disposes of the bin's texture. Should be called to free up resources when the bin is no longer needed.
     */
    public void dispose() {
        sprite.getTexture().dispose();
    }

    /**
     * Gets the type of item the bin accepts.
     *
     * @return The ItemType the bin accepts.
     */
    public ItemType getAcceptsType() {
        return this.acceptsType;
    }

    /**
     * Checks if the bin accepts a given item type.
     *
     * @param itemType The item type to check.
     * @return true if the bin accepts the item type, false otherwise.
     */
    public boolean acceptsItemType(ItemType itemType) {
        return this.acceptsType == itemType;
    }
}
