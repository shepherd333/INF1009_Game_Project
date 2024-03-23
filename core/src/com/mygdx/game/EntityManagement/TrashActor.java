package com.mygdx.game.EntityManagement;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.mygdx.game.Scenes.GamePlay;

public class TrashActor extends CollidableActor {
    private Texture texture;
    private float speed;
    public float bucketX; // Added
    public float bucketWidth; // Added
    private GamePlay gamePlay;
    private String uniqueValue;

    public TrashActor(Texture texture, float speed, float bucketX, float bucketWidth, GamePlay gamePlay) {
        this.texture = texture;
        this.speed = speed;
        this.bucketX = bucketX; // Store the X position
        this.bucketWidth = bucketWidth; // Store the width
        this.setSize(75, 75);
        this.gamePlay = gamePlay;
        setTouchable(Touchable.enabled);
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

        // Move the raindrop horizontally by subtracting its speed adjusted for delta time.
        this.setX(this.getX() - speed * delta);

        // If the raindrop moves off the left side of the screen, reset its position.
        if (this.getX() + this.getWidth() < 0) {
            Gdx.app.log("Trash", "A trash moved off the left side and will be removed. Current X position: " + this.getX());
            // Here you could either reset the raindrop's position or remove it from the stage.
            this.remove(); // For example, to remove the raindrop
            // Or, to reset position, you might call resetPosition(bucketX, bucketWidth), with proper values.
        }
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }

    public Rectangle getBounds() {
        Rectangle bounds = new Rectangle(getX(), getY(), getWidth(), getHeight());
        // Log the current bounds
        Gdx.app.log("TrashActor", "Bounds: " + bounds.toString());
        return bounds;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void dispose() {
        if (texture != null) {
            texture.dispose();
        }
    }
    public String getUniqueValue() {
        return uniqueValue;
    }
}