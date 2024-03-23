package com.mygdx.game.EntityManagement;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class MetalBinActor extends CollidableActor {
    private Sprite sprite;

    public MetalBinActor() {
        sprite = new Sprite(new Texture(Gdx.files.internal("MetalBin.png")));
        sprite.setSize(75, 75); // Set your desired size

        // Position this actor
        float xPosition = 20 + (75 + 10) * 3; // Example for positioning
        float yPosition = Gdx.graphics.getHeight() - sprite.getHeight() - 20; // 20 pixels from the top
        setPosition(xPosition, yPosition);
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

