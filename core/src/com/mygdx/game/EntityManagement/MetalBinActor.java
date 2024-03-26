package com.mygdx.game.EntityManagement;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class MetalBinActor extends CollidableActor {
    private Sprite sprite;
    private Rectangle bounds;

    public MetalBinActor() {
        sprite = new Sprite(new Texture(Gdx.files.internal("MetalBin.png")));
        sprite.setSize(120, 120); // Set your desired size

        // Position this actor
        float xPosition = 20 + (75 + 10) * 3; // Example for positioning
        float yPosition = Gdx.graphics.getHeight() - sprite.getHeight() - 20; // 20 pixels from the top
        setPosition(xPosition, yPosition);

        // Set up collision bounds
        bounds = new Rectangle(getX(), getY(), sprite.getWidth(), sprite.getHeight());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.setPosition(getX(), getY());
        sprite.draw(batch);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        // Update collision bounds position
        bounds.setPosition(getX(), getY());
    }

    @Override
    public Rectangle getBounds() {
        return bounds;
    }

    @Override
    public void drawDebugBounds(ShapeRenderer shapeRenderer) {
        // Render red collision box
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(bounds.x, bounds.y, bounds.width, bounds.height);
    }

    public void dispose() {
        sprite.getTexture().dispose();
    }
}
