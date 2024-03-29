package com.mygdx.game.GameLayer.GameEntities.Movers.Static;

import GameEngine.SimulationLifecycleManagement.AudioManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import GameEngine.EntityManagement.CollidableActor;
import com.mygdx.game.GameLayer.GameEntities.Movers.BucketActor;

/**
 * Represents a TrashMonster actor in the game.
 * This actor can collide with the BucketActor, causing it to lose life and respawn at a random edge.
 */
public class TrashMonsterActor extends CollidableActor {
    private Sprite sprite; // The visual representation of the TrashMonster

    public TrashMonsterActor() {
        // Initialize sprite with texture
        sprite = new Sprite(new Texture(Gdx.files.internal("trashmonster.png")));
        sprite.setSize(90, 115); // Set the desired size for the sprite

        // Set initial position of the TrashMonster
        float xPosition = 20 + 75 + 1100; // Example position, adjust as needed
        float yPosition = Gdx.graphics.getHeight() - sprite.getHeight() - 20; // Positioned 20 pixels from the top
        setPosition(xPosition, yPosition);
    }

    /**
     * Checks if this TrashMonster overlaps with a BucketActor.
     * @param bucket The BucketActor to check for overlap.
     * @return True if there is overlap, false otherwise.
     */
    public boolean overlaps(BucketActor bucket) {
        Rectangle trashMonsterBounds = new Rectangle(getX(), getY(), sprite.getWidth(), sprite.getHeight());
        Rectangle bucketBounds = new Rectangle(bucket.getX(), bucket.getY(), bucket.getWidth(), bucket.getHeight());
        return trashMonsterBounds.overlaps(bucketBounds);
    }

    /**
     * Respawns the TrashMonster at a random edge of the screen.
     */
    public void respawnAtRandomEdge() {
        float mapWidth = Gdx.graphics.getWidth();
        float mapHeight = Gdx.graphics.getHeight();

        // Randomly choose an edge of the screen
        int edge = MathUtils.random(0, 3); // 0: top, 1: bottom, 2: left, 3: right

        float newX = 0, newY = 0;
        switch (edge) {
            case 0: // Top edge
                newX = MathUtils.random(0, mapWidth - sprite.getWidth());
                newY = mapHeight - sprite.getHeight();
                break;
            case 1: // Bottom edge
                newX = MathUtils.random(0, mapWidth - sprite.getWidth());
                newY = 0;
                break;
            case 2: // Left edge
                newX = 0;
                newY = MathUtils.random(0, mapHeight - sprite.getHeight());
                break;
            case 3: // Right edge
                newX = mapWidth - sprite.getWidth();
                newY = MathUtils.random(0, mapHeight - sprite.getHeight());
                break;
        }

        setPosition(newX, newY); // Update the position to the new location
    }

    /**
     * Checks for collision with the BucketActor and handles the effects of such a collision.
     * @param bucket The BucketActor to check for collision.
     */
    public void checkMonsterBucketCollision(BucketActor bucket) {
        if (overlaps(bucket)) {
            bucket.decreaseLife(10); // Decrease the bucket's life
            AudioManager.getInstance().playSoundEffect("collision", 1.0f); // Play collision sound effect
            respawnAtRandomEdge(); // Respawn the TrashMonster at a random edge
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.setPosition(getX(), getY());
        sprite.draw(batch); // Draw the TrashMonster sprite
    }

    @Override
    public void act(float delta) {
        super.act(delta); // Placeholder for future behavior updates
    }

    /**
     * Disposes of the sprite's texture when the actor is no longer needed.
     */
    public void dispose() {
        sprite.getTexture().dispose(); // Prevent memory leaks by disposing of the texture
    }
}
