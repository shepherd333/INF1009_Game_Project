package com.mygdx.game.EntityManagement;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class BucketActor extends CollidableActor {
    private Texture textureLeft;
    private Texture textureRight;
    private Texture textureUp;
    private Texture textureDown;
    private Sprite currentSprite;
    private float speed;

    private Texture texture;
    private String possessionValue;
    private boolean itemPickedUp; // Flag to check if an item has been picked up
    private float maxHealth = 100; // Maximum health
    private float currentHealth = maxHealth; // Current health
    private float lifeBarWidth = 50; // Width of the life bar
    private float lifeBarHeight = 5; // Height of the life bar
    private Color lifeBarColor = Color.GREEN; // Color of the life bar
    private static ShapeRenderer shapeRenderer = new ShapeRenderer();

    // Constructor
    public BucketActor(float x, float y, float speed) {
        this.speed = speed;
        this.setPosition(x, y);
        textureLeft = new Texture(Gdx.files.internal("WalleLeft.png"));
        textureRight = new Texture(Gdx.files.internal("WalleRight.png"));
        textureUp = new Texture(Gdx.files.internal("WalleBack.png"));
        textureDown = new Texture(Gdx.files.internal("WalleDown.png"));
        // Set the initial texture, for example, facing up
        currentSprite = new Sprite(textureDown);
        currentSprite.setSize(125,125);
        this.setSize(currentSprite.getWidth(), currentSprite.getHeight());
        setTouchable(Touchable.enabled);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        // Update logic to prevent picking up items if already picked up
        if (!itemPickedUp) {
            // Get current bucket position
            float newX = getX();
            float newY = getY();

            // Update position based on input
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                newX -= speed * delta;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                newX += speed * delta;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                newY += speed * delta;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                newY -= speed * delta;
            }

            // Clamp position within screen bounds
            newX = MathUtils.clamp(newX, 0, getStage().getViewport().getWorldWidth() - getWidth());
            newY = MathUtils.clamp(newY, 0, getStage().getViewport().getWorldHeight() - getHeight());

            // Update bucket position
            setPosition(newX, newY);
        } else {
            // If item is picked up, clamp the bucket's position within screen bounds
            float clampedX = MathUtils.clamp(getX(), 0, getStage().getViewport().getWorldWidth() - getWidth());
            float clampedY = MathUtils.clamp(getY(), 0, getStage().getViewport().getWorldHeight() - getHeight());
            setPosition(clampedX, clampedY);
        }
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

    @Override
    public void draw(Batch batch, float parentAlpha) {
//        batch.draw(currentSprite, getX(), getY(), getWidth(), getHeight());
        batch.draw(currentSprite, getX(), getY(), getWidth(), getHeight());

        // End the batch before starting to draw with the ShapeRenderer
        batch.end();

        // Draw the life bar with ShapeRenderer
        Gdx.gl.glEnable(GL20.GL_BLEND);
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(lifeBarColor);

        // Calculate the width of the life bar based on current health
        float healthPercentage = currentHealth / maxHealth;
        float drawWidth = lifeBarWidth * healthPercentage;
        float lifeBarX = getX() + (getWidth() - lifeBarWidth) / 2; // Center the life bar
        float lifeBarY = getY() + getHeight() + 5; // 5 pixels above the entity
        shapeRenderer.rect(lifeBarX, lifeBarY, drawWidth, lifeBarHeight);

        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

        // Start the batch again for subsequent drawings
        batch.begin();
    }

    public void takeDamage(float amount) {
        currentHealth -= amount;
        currentHealth = Math.max(currentHealth, 0); // Ensure health does not drop below 0
        updateLifeBarColor(); // Optionally, update the life bar's color based on current health
    }

    public void heal(float amount) {
        currentHealth += amount;
        currentHealth = Math.min(currentHealth, maxHealth); // Ensure health does not exceed maxHealth
    }

    private void updateLifeBarColor() {
        // Update the life bar color based on current health percentage
        float healthPercentage = currentHealth / maxHealth;
        if(healthPercentage > 0.5) {
            lifeBarColor = Color.GREEN;
        } else if(healthPercentage > 0.25) {
            lifeBarColor = Color.YELLOW;
        } else {
            lifeBarColor = Color.RED;
        }
    }

    // Provides a bounding box for the bucket, useful for collision detection.
    public Rectangle getBounds() {
        Rectangle bounds = new Rectangle(getX(), getY(), getWidth(), getHeight());
//        Gdx.app.log("BucketActor", "Bounds: " + bounds.toString());
        return bounds;
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

    public float getSpeed() {
        return speed;
    }

    public void setPossessionValue(String value) {
        this.possessionValue = value;
    }

    public String getPossessionValue() {
        return possessionValue;
    }

    public boolean isItemPickedUp() {
        return itemPickedUp;
    }

    public void setItemPickedUp(boolean itemPickedUp) {
        this.itemPickedUp = itemPickedUp;
    }

    public enum Direction {
        LEFT, RIGHT, UP, DOWN
    }
}
