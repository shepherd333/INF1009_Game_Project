package com.mygdx.game.EntityManagement.Items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.mygdx.game.EntityManagement.CollidableActor;
import com.mygdx.game.Scenes.GamePlay;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class PlasticItemsActor extends CollidableActor {
    private TextureRegion textureRegion;
    private float speed;
    public float bucketX, bucketWidth;
    private GamePlay gamePlay;
    private String uniqueValue;

    private static TextureAtlas plasticItemsAtlas = new TextureAtlas(Gdx.files.internal("plasticitems.atlas"));
    private static Array<TextureAtlas.AtlasRegion> plasticItemsRegions =  plasticItemsAtlas.findRegions("plasticItem");

    public PlasticItemsActor(float speed, float bucketX, float bucketWidth, GamePlay gamePlay) {
        this.speed = speed;
        this.bucketX = bucketX; // Store the X position
        this.bucketWidth = bucketWidth; // Store the width
        this.gamePlay = gamePlay;

        int index = (int) (Math.random() * plasticItemsRegions.size);
        this.textureRegion = plasticItemsRegions.get(index);

        setTouchable(Touchable.enabled);
        this.setSize(75, 75);
        this.uniqueValue = "1";
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
        batch.draw(textureRegion, getX(), getY(), getWidth(), getHeight());
    }

    public Rectangle getBounds() {
        Rectangle bounds = new Rectangle(getX(), getY(), getWidth(), getHeight());
        // Log the current bounds
        Gdx.app.log("PlasticItemsActor", "Bounds: " + bounds.toString());
        return bounds;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void dispose() {
        plasticItemsAtlas.dispose();
    }
    public String getUniqueValue() {
        return uniqueValue;
    }
}