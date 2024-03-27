package com.mygdx.game.EntityManagement;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class TrashMonsterActor extends CollidableActor{
    private Sprite sprite;

    public TrashMonsterActor() {
        sprite = new Sprite(new Texture(Gdx.files.internal("trashmonster.png")));
        sprite.setSize(100, 100); // Set your desired size

        // Position this actor
        float xPosition = 20 + 75 + 1100;
        float yPosition = Gdx.graphics.getHeight() - sprite.getHeight() - 20; // 20 pixels from the top
        setPosition(xPosition, yPosition);
    }

    public boolean overlaps(BucketActor bucket) {
        Rectangle trashMonsterBounds = new Rectangle(getX(), getY(), sprite.getWidth(), sprite.getHeight());
        Rectangle bucketBounds = new Rectangle(bucket.getX(), bucket.getY(), bucket.getWidth(), bucket.getHeight());
        return trashMonsterBounds.overlaps(bucketBounds);
    }

    public void respawnAtRandomEdge() {
        float mapWidth = Gdx.graphics.getWidth();
        float mapHeight = Gdx.graphics.getHeight();
        float newX = 0, newY = 0;

        // Choose a random edge: 0 = top, 1 = bottom, 2 = left, 3 = right
        int edge = MathUtils.random(0, 3);

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

        // Set the new position
        setPosition(newX, newY);
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

    public void dispose() {
        sprite.getTexture().dispose();
    }

}
