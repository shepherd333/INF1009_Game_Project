package com.mygdx.game.EntityManagement;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class PlasticBinActor extends CollidableActor {
    private Sprite sprite;
    private int value;

    public PlasticBinActor(int value) {
        sprite = new Sprite(new Texture(Gdx.files.internal("PlasticBin.png")));
        sprite.setSize(120, 120); // Set your desired size

        // Position this actor
        float xPosition = 20 + (75 + 10) * 2; // Example for positioning
        float yPosition = Gdx.graphics.getHeight() - sprite.getHeight() - 20; // 20 pixels from the top
        setPosition(xPosition, yPosition);

        // Set the value of the Plastic Bin
        this.value = value;
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
