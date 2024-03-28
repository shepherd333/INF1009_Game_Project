package com.mygdx.game.EntityManagement.Static;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.EntityManagement.Foundation.CollidableActor;

public class ToxicWasteActor extends CollidableActor {
    private Sprite sprite;

    public ToxicWasteActor() {
        sprite = new Sprite(new Texture(Gdx.files.internal("toxicWaste.png")));
        sprite.setSize(80, 85); // Set your desired size

        float minX = 50; // Minimum X-coordinate
        float maxX = Gdx.graphics.getWidth() - 50 - sprite.getWidth(); // Maximum X-coordinate, assuming ToxicWasteActor has a width of 120
        float minY = calculateMinY(); // Calculate based on bins/items
        float maxY = calculateMaxY();// Maximum Y-coordinate, assuming ToxicWasteActor has a height of 120

        float randomX = MathUtils.random(minX, maxX);
        float randomY = MathUtils.random(minY, maxY);
        setPosition(randomX,randomY);
    }

    public Rectangle getBounds() {
        return new Rectangle(getX(), getY(), sprite.getWidth(), sprite.getHeight());
    }

    private float calculateMinY() {
        // Assuming bins/items occupy up to 150 pixels from the bottom
        return 150;
    }

    private float calculateMaxY() {
        // Assuming bins/items and other UI elements occupy space at the top
        return Gdx.graphics.getHeight() - 150 - sprite.getHeight(); // Adjust as necessary
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
