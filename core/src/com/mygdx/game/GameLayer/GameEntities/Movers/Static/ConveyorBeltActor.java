package com.mygdx.game.GameLayer.GameEntities.Movers.Static;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

/**
 * Represents a conveyor belt actor using an animation created from a series of texture regions.
 * The animation is intended to loop, creating the visual effect of a continuously moving conveyor belt.
 */
public class ConveyorBeltActor extends Actor {
    private Animation<TextureRegion> conveyorAnimation; // Animation for the conveyor belt
    private float stateTime; // Tracks the elapsed time to determine the current frame of the animation
    private TextureAtlas conveyorAtlas; // Holds the texture regions for the conveyor belt animation

    public ConveyorBeltActor() {
        // Load the texture atlas and extract frames for the conveyor animation
        conveyorAtlas = new TextureAtlas(Gdx.files.internal("conveyor.atlas"));
        Array<TextureAtlas.AtlasRegion> frames = conveyorAtlas.findRegions("conveyor");
        conveyorAnimation = new Animation<>(0.1f, frames, Animation.PlayMode.LOOP);
        stateTime = 0f;

        // Set the size of the actor based on the first frame of the animation
        TextureRegion initialFrame = frames.first();
        setSize(initialFrame.getRegionWidth(), initialFrame.getRegionHeight() * 2);
        setPosition(0, 0); // Position the actor at the bottom left corner by default
    }

    @Override
    public void act(float delta) {
        // Update the state time with the delta to progress the animation
        stateTime += delta;
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        // Get the current frame of the animation based on the state time
        TextureRegion currentFrame = conveyorAnimation.getKeyFrame(stateTime, true);
        float currentWidth = currentFrame.getRegionWidth();
        float screenWidth = Gdx.graphics.getWidth();
        int tileCount = (int) Math.ceil(screenWidth / currentWidth); // Calculate how many tiles are needed to span the screen width

        // Tile the animation frame across the width of the screen
        for (int i = 0; i < tileCount; i++) {
            batch.draw(currentFrame, getX() + i * currentWidth, getY(), currentWidth, getHeight());
        }
    }

    /**
     * Disposes of resources used by the actor to prevent memory leaks.
     * Should be called when the actor is no longer needed.
     */
    public void dispose() {
        conveyorAtlas.dispose();
    }
}
