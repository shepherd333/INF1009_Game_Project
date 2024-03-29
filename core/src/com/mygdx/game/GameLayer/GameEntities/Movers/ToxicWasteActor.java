package com.mygdx.game.GameLayer.GameEntities.Movers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import GameEngine.EntityManagement.CollidableActor;

/**
 * Represents a toxic waste item in the game that players must avoid or interact with.
 * It is randomly positioned within defined bounds, avoiding the UI and other fixed elements.
 */
public class ToxicWasteActor extends CollidableActor {
    private Sprite sprite; // The visual representation of the toxic waste.

    public ToxicWasteActor() {
        // Load the texture and set the sprite size.
        sprite = new Sprite(new Texture(Gdx.files.internal("toxicWaste.png")));
        sprite.setSize(80, 85); // Desired size for the sprite.

        // Define the bounds for random positioning.
        float minX = 50; // Minimum X-coordinate, providing a margin.
        float maxX = Gdx.graphics.getWidth() - 50 - sprite.getWidth(); // Maximum X-coordinate, accounting for sprite width and margin.
        float minY = calculateMinY(); // Minimum Y-coordinate, accounting for UI/bottom elements.
        float maxY = calculateMaxY(); // Maximum Y-coordinate, accounting for UI/top elements.

        // Randomize position within bounds.
        float randomX = MathUtils.random(minX, maxX);
        float randomY = MathUtils.random(minY, maxY);
        setPosition(randomX, randomY);
    }

    /**
     * Gets the bounding rectangle for collision detection.
     *
     * @return A Rectangle that represents the bounds of the toxic waste.
     */
    public Rectangle getBounds() {
        return new Rectangle(getX(), getY(), sprite.getWidth(), sprite.getHeight());
    }

    /**
     * Calculates the minimum Y-coordinate for positioning the toxic waste,
     * ensuring it does not overlap with bottom UI elements or bins.
     *
     * @return The calculated minimum Y-coordinate.
     */
    private float calculateMinY() {
        // Assuming bins/items occupy up to 150 pixels from the bottom.
        return 150;
    }

    /**
     * Calculates the maximum Y-coordinate for positioning the toxic waste,
     * ensuring it does not overlap with top UI elements.
     *
     * @return The calculated maximum Y-coordinate.
     */
    private float calculateMaxY() {
        // Assuming bins/items and other UI elements occupy space at the top.
        return Gdx.graphics.getHeight() - 150 - sprite.getHeight(); // Adjust based on UI layout.
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.setPosition(getX(), getY());
        sprite.draw(batch);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    /**
     * Disposes of the sprite's texture. Should be called to free up resources when the actor is no longer needed.
     */
    public void dispose() {
        sprite.getTexture().dispose();
    }
}
