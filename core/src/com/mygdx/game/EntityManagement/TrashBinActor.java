package com.mygdx.game.EntityManagement;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class TrashBinActor extends CollidableActor {
    private Sprite sprite;
    private int value;

    public TrashBinActor(int value) {
        sprite = new Sprite(new Texture(Gdx.files.internal("TrashBin.png")));
        sprite.setSize(120, 120); // Set your desired size

        // Position this actor
        float xPosition = 20 + (75 + 10) * 4;
        float yPosition = Gdx.graphics.getHeight() - sprite.getHeight() - 20; // 20 pixels from the top
        setPosition(xPosition, yPosition);

        // Set the value of the Trash Bin
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

